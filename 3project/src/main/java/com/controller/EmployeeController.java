package com.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bean.Employee;
import com.bean.Result;
import com.service.EmployeeService;
import com.service.EmployeeServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    /**
     * 登录响应，检查数据库中是否有该数据(账号密码是否正确)
     * @return
     */
    @RequestMapping("login")
    @ResponseBody
    public String checkLogin(HttpSession session){
        Result result = new Result();
        String exception = (String) session.getAttribute("shiroLoginFailure");
        if(null!=exception){
            if(UnknownAccountException.class.getName().equals(exception)){
                result.setMessage("登录失败");
                result.setSuccess(false);
            }else  if(IncorrectCredentialsException.class.getName().equals(exception)){
                result.setMessage("登录成功");
                result.setSuccess(true);
            }
        }
        return result.toString();
//    //shiro认证
//    //获取登录对象
//    Subject subject = SecurityUtils.getSubject();
//    //登录校验
//    UsernamePasswordToken token = new UsernamePasswordToken(name,password);
//    try {
//        //进行校验，如果校验失败就抛出异常
//        subject.login(token);
//        result.setMsg("登录成功");
//        result.setStatus(true);
//        //将登录对象存到 session域
//        Session session = subject.getSession();
//        session.setAttribute("employee",subject.getPrincipal());
//    }catch (Exception e){
//        result.setMsg("登录失败");
//        result.setStatus(false);
//    }
//       return result.toString();
//

    }
    @RequestMapping("getCurrentEmployee")
    @ResponseBody
    public String getCurrentEmployee (HttpSession httpSession){
        Employee employee = (Employee) httpSession.getAttribute("employee");
        return employee.getEname();
    }
    @RequestMapping("logout")
    public String logout(HttpSession httpSession){
        httpSession.removeAttribute("employee");
        return "redirect:/index.html";
    }
    @RequestMapping("findEmployeesByCondition")
    @ResponseBody
    public JSONObject findEmployeesByCondition(Employee employee){
        JSONObject result = new JSONObject();
        List<Employee> employeeList = employeeService.findEmployeeByCondition(employee);
        int total = employeeService.countEmployeesByName(employee.getEname());
        result.put("rows",employeeList);
        result.put("total",total);
        return result;
    }
    @RequestMapping("exportEmployees")
    public ResponseEntity<byte[]>  export() throws IOException {
        String filename = "employee.xls";
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row title = sheet.createRow(0);
        title.createCell(0).setCellValue("员工id");
        title.createCell(1).setCellValue("员工姓名");
        title.createCell(2).setCellValue("员工性别");
        title.createCell(3).setCellValue("员工年龄");
        title.createCell(4).setCellValue("联系电话");
        title.createCell(5).setCellValue("工号");
        title.createCell(6).setCellValue("入职日期");
        //获取数据库员工信息
        Employee employee = new Employee();
        employee.setPage(1);
        employee.setRows(9999);
        List<Employee> employees =
                employeeService.findEmployeeByCondition(employee);
        for(Employee employee1:employees){
            Row nextRow = sheet.createRow(sheet.getLastRowNum()+1);
            nextRow.createCell(0).setCellValue(employee1.getEid());
            nextRow.createCell(1).setCellValue(employee1.getEname());
            nextRow.createCell(2).setCellValue(employee1.getEsexStr());
            nextRow.createCell(3).setCellValue(employee1.getEage());
            nextRow.createCell(4).setCellValue(employee1.getEtelephone());
            nextRow.createCell(5).setCellValue(employee1.getJobnumber());
            nextRow.createCell(6).setCellValue(employee1.getHireDateStr());
        }
        ByteArrayOutputStream byteArrayOutputStream = new
                ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        byte[] bytes=byteArrayOutputStream.toByteArray();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDispositionFormData("attachment",filename);
        return new ResponseEntity<>(bytes,httpHeaders, HttpStatus.OK);
    }
}
