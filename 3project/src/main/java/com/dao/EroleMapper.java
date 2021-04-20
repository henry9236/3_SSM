package com.dao;

import com.bean.Erole;
import com.bean.EroleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EroleMapper {
    long countByExample(EroleExample example);

    int deleteByExample(EroleExample example);

    int deleteByPrimaryKey(Long rid);

    int insert(Erole record);

    int insertSelective(Erole record);

    List<Erole> selectByExample(EroleExample example);

    Erole selectByPrimaryKey(Long rid);

    int updateByExampleSelective(@Param("record") Erole record, @Param("example") EroleExample example);

    int updateByExample(@Param("record") Erole record, @Param("example") EroleExample example);

    int updateByPrimaryKeySelective(Erole record);

    int updateByPrimaryKey(Erole record);
}