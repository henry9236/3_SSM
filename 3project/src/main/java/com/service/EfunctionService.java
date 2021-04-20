package com.service;

import com.bean.Efunction;
import com.bean.Result;

import java.util.List;

public interface EfunctionService {

    /**
     * 根据员工eid，在数据库中联合查找员工拥有的方法(efunction)
     * @param eid
     * @return
     */
    List<Efunction> checkEmployeeFunction(long eid);

    /**
     * 通过从浏览器获取的分页数据(自动填充到efunction继承的PageTool中)，来查询员工拥有的方法
     * @param efunction
     * @return
     */
    List<Efunction> findFunctionsByCondition(Efunction efunction);

    /**
     * 根据模糊查询结果计算个数
     * @param efunction 使用封装的fname属性做模糊查询
     * @return
     */
    int countFunctionsByCondition(Efunction efunction);

    /**
     * 添加efunction
     * @param efunction 要添加的数据
     * @return
     */
    Result addFunction(Efunction efunction);

    /**
     * 根据fid获取efunction
     * @param fid
     * @return
     */
    Efunction getEfunctionById(int fid);

    /**
     * 获取所有一级功能方法，parentid等于0的数据
     * @return
     */
    List<Efunction> getFirstFunctions();

    /**
     * 更新efunction的数据
     * @param efunction
     * @return
     */
    Result updateFunction(Efunction efunction);

    /**
     * 根据fid，删除efunction数据，并删除role_fun中的数据
     * @param fid
     * @return
     */
    Result deleteFunctionByFid(long fid);
}
