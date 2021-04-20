package com.dao;

import com.bean.Efunction;
import com.bean.EfunctionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EfunctionMapper {
    long countByExample(EfunctionExample example);

    int deleteByExample(EfunctionExample example);

    int deleteByPrimaryKey(Long fid);

    int insert(Efunction record);

    int insertSelective(Efunction record);

    List<Efunction> selectByExample(EfunctionExample example);

    Efunction selectByPrimaryKey(Long fid);

    int updateByExampleSelective(@Param("record") Efunction record, @Param("example") EfunctionExample example);

    int updateByExample(@Param("record") Efunction record, @Param("example") EfunctionExample example);

    int updateByPrimaryKeySelective(Efunction record);

    int updateByPrimaryKey(Efunction record);
    /**
     * 根据员工id查询员工拥有的方法
     * @param eid
     * @return
     */
    List<Efunction> checkEmployeeFunction(long eid);

    /**
     * 根据模糊查询和分页查询返回方法数据
     * @param fname
     * @param start
     * @param rows
     * @return
     */
    List<Efunction> findFunctionsByCondition(@Param("fname") String fname, @Param("start") int start, @Param("rows") int rows);

    /**
     * 根据模糊查询结果计算个数
     * @param fname
     * @return
     */
    int countFunctionsByCondition(@Param("fname") String fname);
}