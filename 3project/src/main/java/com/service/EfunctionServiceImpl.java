package com.service;

import com.bean.Efunction;
import com.bean.EfunctionExample;
import com.bean.Result;
import com.bean.RoleFunExample;
import com.dao.EfunctionMapper;
import com.dao.RoleFunMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class EfunctionServiceImpl implements  EfunctionService {
    @Autowired
    EfunctionMapper efunctionMapper;
    @Autowired
    RoleFunMapper roleFunMapper;
    @Override
    public List<Efunction> checkEmployeeFunction(long eid){
       return efunctionMapper.checkEmployeeFunction(eid);
    }

    @Override
    public List<Efunction> findFunctionsByCondition(Efunction efunction) {
       return efunctionMapper.findFunctionsByCondition(efunction.getFname(),((efunction.getPage()-1)*efunction.getRows()),efunction.getRows());

    }

    @Override
    public int countFunctionsByCondition(Efunction efunction) {
        return efunctionMapper.countFunctionsByCondition(efunction.getFname());
    }

    @Override
    public Result addFunction(Efunction efunction) {
        //创建返回结果
        Result result = new Result();
        //添加校验fcode不能重复
        EfunctionExample efunctionExample = new EfunctionExample();
        EfunctionExample.Criteria criteria = efunctionExample.createCriteria();
        criteria.andFcodeEqualTo(efunction.getFcode());
        List<Efunction> efunctionList = efunctionMapper.selectByExample(efunctionExample);
        if(0==efunctionList.size()){

            int affectRow = efunctionMapper.insert(efunction);
            //查看是否添加成功，设置属性并返回
            if(1==affectRow){
                result.setMessage("添加成功");
                result.setSuccess(true);
                return result;
            }
        }
        result.setMessage("添加失败");
        result.setSuccess(false);
        return result;
    }

    @Override
    public Efunction getEfunctionById(int fid) {
        EfunctionExample efunctionExample = new EfunctionExample();
        EfunctionExample.Criteria criteria = efunctionExample.createCriteria();
        criteria.andFidEqualTo((long)fid);
        //根据fid从数据库中获取efunction数据
        List<Efunction> efunctionList = efunctionMapper.selectByExample(efunctionExample);
        if(0!=efunctionList.size()){
            return efunctionList.get(0);
        }
        return null;
    }

    @Override
    public List<Efunction> getFirstFunctions() {
        EfunctionExample efunctionExample = new EfunctionExample();
        EfunctionExample.Criteria criteria = efunctionExample.createCriteria();
        criteria.andParentidEqualTo(0L);
        return efunctionMapper.selectByExample(efunctionExample);
    }

    @Override
    public Result updateFunction(Efunction efunction) {
        //创建返回结果
        Result result =new Result();
        int affectRow = 0;
        EfunctionExample efunctionExample = new EfunctionExample();
        EfunctionExample.Criteria criteria = efunctionExample.createCriteria();
        //先通过Fid获取要修改的efunction数据
        criteria.andFidEqualTo(efunction.getFid());
        List<Efunction> efunctionListSelectByFid = efunctionMapper.selectByExample(efunctionExample);
        if(1==efunctionListSelectByFid.size())
        {
            Efunction oldEfunction = efunctionListSelectByFid.get(0);
            //fcode不能重复，比较两个fcode的值,相同代表不必修改，就直接更新
            if(oldEfunction.getFcode().equals(efunction.getFcode())){
                affectRow = efunctionMapper.updateByPrimaryKeySelective(efunction);
                if(1==affectRow){
                    result.setMessage("添加成功");
                    result.setSuccess(true);
                    return result;
                }
            }
            //重置efunctionExample
            efunctionExample.clear();
            EfunctionExample.Criteria criteria1 = efunctionExample.createCriteria();
            criteria1.andFcodeEqualTo(efunction.getFcode());
            //查询数据库中有没有重复的fcode
            List<Efunction> efunctionListSelectByFcode = efunctionMapper.selectByExample(efunctionExample);
            if(0==efunctionListSelectByFcode.size()){
                affectRow = efunctionMapper.updateByPrimaryKeySelective(efunction);
                if(1==affectRow){
                    result.setMessage("添加成功");
                    result.setSuccess(true);
                    return result;
                }
            }
        }
        result.setMessage("添加失败");
        result.setSuccess(false);
        return result;
    }

    @Override
    @Transactional
    public Result deleteFunctionByFid(long fid) {
        Result result = new Result();
        //删除fid对应的efunction，并在role_fun中删除带有fid的数据
        try {
            //删除role_fun中所有fid为fid的数据
            RoleFunExample roleFunExample = new RoleFunExample();
            RoleFunExample.Criteria criteria = roleFunExample.createCriteria();
            criteria.andFidEqualTo(fid);
            int affectRow = roleFunMapper.deleteByExample(roleFunExample);

            //根据parentId删除efunction中的数据,删除所有父节点为要删除节点的元素
            EfunctionExample efunctionExample = new EfunctionExample();
            EfunctionExample.Criteria criteria_efunction_parentId = efunctionExample.createCriteria();
            criteria_efunction_parentId.andParentidEqualTo(fid);
            int affectRow_efunction_parentId = efunctionMapper.deleteByExample(efunctionExample);
            //重置efunctionExample 删除 fid为 fid 的efunction
            efunctionExample.clear();
            EfunctionExample.Criteria criteria_efunction_fid = efunctionExample.createCriteria();
            criteria_efunction_fid.andFidEqualTo(fid);
            int affectRow_efunction_fid = efunctionMapper.deleteByExample(efunctionExample);
            if(affectRow_efunction_fid==0){
                throw new Exception("fail to delete efunction");
            }
            //如果执行到这里没有出现异常则执行成功
            result.setMessage("删除成功");
            result.setSuccess(true);
            return result;
        }catch (Exception e) {
            //发生异常，回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            System.out.println("发生异常，取消操作");
            result.setMessage("删除失败");
            result.setSuccess(false);
            return result;
        }
    }
}
