package com.dao;

import com.bean.RoleFun;
import com.bean.RoleFunExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleFunMapper {
    long countByExample(RoleFunExample example);

    int deleteByExample(RoleFunExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RoleFun record);

    int insertSelective(RoleFun record);

    List<RoleFun> selectByExample(RoleFunExample example);

    RoleFun selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RoleFun record, @Param("example") RoleFunExample example);

    int updateByExample(@Param("record") RoleFun record, @Param("example") RoleFunExample example);

    int updateByPrimaryKeySelective(RoleFun record);

    int updateByPrimaryKey(RoleFun record);
}