package com.service;

import com.bean.Efunction;
import com.bean.Employee;

import java.util.List;

public interface EmployeeService {

    /**
     * 通过name和password在数据库中查找employee的信息(name可以是jobnumber或姓名)<--做两次查询
     * @param name
     * @param password
     * @return
     */
    Employee checkEmployee(String name, String password);

    /**
     * 根据条件获取员工列表数据，
     * @param employee
     * @return
     */
    List<Employee> findEmployeeByCondition(Employee employee);

    /**
     * 根据姓名计算个数
     * @param name
     * @return
     */
    int countEmployeesByName(String name);
}
