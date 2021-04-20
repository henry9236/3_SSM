package com.dao;

import com.bean.Employee;
import com.bean.EmployeeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    long countByExample(EmployeeExample example);

    int deleteByExample(EmployeeExample example);

    int deleteByPrimaryKey(Long eid);

    int insert(Employee record);

    int insertSelective(Employee record);

    List<Employee> selectByExample(EmployeeExample example);

    Employee selectByPrimaryKey(Long eid);

    int updateByExampleSelective(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByExample(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    /**
     * 根据姓名进行查询并进行分页查询
     * @param ename
     * @param limitStart
     * @param rows
     * @return
     */
    List<Employee> findEmployeeByCondition(@Param("ename")String ename,@Param("jobnumber")String jobnumber,@Param("limitStart")int limitStart,@Param("rows") int rows);

    /**
     * 根据姓名进行查询并计算个数
     * @param ename
     * @return
     */
    int countEmployeeByName(@Param("ename")String ename);

    /**
     * 根据员工id获取员工的rid
     * @param eid
     * @return
     */
    int findEmployeeRoleIdByEid(long eid);
}