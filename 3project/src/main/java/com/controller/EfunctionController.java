package com.controller;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.bean.Efunction;
import com.bean.Employee;
import com.bean.Result;
import com.service.EfunctionService;
import com.util.EfunctionToJsonString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("function")
public class EfunctionController {
    @Autowired
    EfunctionService efunctionService;

    /**
     * 根据员工eid获取员工拥有的执行方法(efunction)，然后转成JSONarray返回到页面
     * @param
     * @return
     */
    @RequestMapping("currentFunctions")
    @ResponseBody
    public JSONArray checkEmployeeFunction(HttpSession httpSession){

        Employee employee = (Employee) httpSession.getAttribute("employee");
        //取不到值就直接结束
        if (null==employee){
            return null;
        }
        //根据员工eid获取员工拥有的执行方法(efunction)
        List<Efunction> listE = efunctionService.checkEmployeeFunction(employee.getEid());
        //转成JSONarray 返回到页面 变成树形 文件
       return EfunctionToJsonString.eFunctionLoop(listE,0);
    }

    /**
     * 根据条件查询efunction并返回
     * @param efunction
     * @return
     */
    @RequestMapping("findFunctionsByCondition")
    @ResponseBody
    public JSONObject findFunctionsByCondition(Efunction efunction){
        //获取efunction数据
        List<Efunction> efunctionList = efunctionService.findFunctionsByCondition(efunction);
        //获取efunction个数
        int total = efunctionService.countFunctionsByCondition(efunction);
        //将数据封装到JSONObject返回给浏览器
        JSONObject result = new JSONObject();
        result.put("rows",efunctionList);
        result.put("total",total);
        return result;
    }
    /**
     * 获取所有一级方法
     * @return
     */
    @RequestMapping("getFirstFunctions")
    @ResponseBody
    public List<Efunction> getFirstFunctions(){
        return efunctionService.getFirstFunctions();
    }
    /**
     * 将efunction添加到数据库
     * @param efunction
     * @return
     */
    @RequestMapping("addFunction")
    @ResponseBody
    public String addFunction(Efunction efunction){
    return efunctionService.addFunction(efunction).toString();
    }

    /**
     * 将要更新的efunction存到session作用域中，并返回状态
     * @param fid
     * @param httpSession
     * @return
     */
    @RequestMapping("toUpdateView")
    @ResponseBody
    public String toUpdateView(int fid,HttpSession httpSession){
    Result result = new Result();
    Efunction efunction =  efunctionService.getEfunctionById(fid);
    if(null!=efunction){
            httpSession.setAttribute("EfunctionToUpdate",efunction);
            result.setSuccess(true);
            return result.toString();
        }
    result.setSuccess(false);
    return result.toString();
    }

    /**
     * 从session作用域中取出数据返回到页面
     * @param httpSession
     * @return
     */
    @RequestMapping("getUpdateFunction")
    @ResponseBody
    public Efunction getUpdateFunction(HttpSession httpSession){
       Efunction efunction = (Efunction) httpSession.getAttribute("EfunctionToUpdate");
       return efunction;
    }

    @RequestMapping("commitUpdateFunction")
    @ResponseBody
    public String commitUpdateFunction(Efunction efunction){
        return efunctionService.updateFunction(efunction).toString();
    }
    @RequestMapping("deleteFunctoin")
    @ResponseBody
    public Result deleteFunction(long fid){
    return efunctionService.deleteFunctionByFid(fid);
    }
    @RequestMapping("deleteFunctionByFids")
    @ResponseBody
    @Transactional
    public Result deleteFunctionByFids(String fids){
        String[] strings = fids.split(",");
        for (String fid : strings) {
            efunctionService.deleteFunctionByFid(Long.parseLong(fid));
        }
        return new Result(true);
    }
}
