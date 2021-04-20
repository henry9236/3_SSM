package com.service;

import com.bean.Employee;
import com.bean.EmployeeExample;
import com.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
        @Autowired
        EmployeeMapper employeeMapper;
    @Override
    public Employee checkEmployee(String jobnumber, String password) {
            EmployeeExample employeeExample = new EmployeeExample();
            EmployeeExample.Criteria criteria = employeeExample.createCriteria();
            criteria.andJobnumberEqualTo(jobnumber);
            criteria.andPasswordEqualTo(password);
           List<Employee> employee = employeeMapper.selectByExample(employeeExample);
           if (0!=employee.size())
           {
               return employee.get(0);
           }
           return null;
        }

    @Override
    public List<Employee> findEmployeeByCondition(Employee employee) {
       List<Employee> employeeList = employeeMapper.findEmployeeByCondition(employee.getEname(),employee.getJobnumber(),((employee.getPage()-1)*employee.getRows()),employee.getRows());
        for (Employee employeeInList : employeeList) {
            employeeInList.setEsexStr(employeeInList.getEsex()==0?"女":"男");
            employeeInList.setHireDateStr(new SimpleDateFormat("yyyy-mm-dd").format(employeeInList.getHiredate()));
        }
        return employeeList;
    }

    @Override
    public int countEmployeesByName(String name) {
        return employeeMapper.countEmployeeByName(name);
    }

}
