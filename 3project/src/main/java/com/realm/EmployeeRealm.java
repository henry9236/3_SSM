package com.realm;

import com.bean.Employee;
import com.bean.EmployeeExample;
import com.dao.EmployeeMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EmployeeRealm extends AuthorizingRealm {
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 获取用户权限信息，做授权校验
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前登录用户的登录信息，该身份信息在认证时已设置
        Employee employee = (Employee) principalCollection.getPrimaryPrincipal();
        long employeeRid = employeeMapper.findEmployeeRoleIdByEid((employee.getEid()));
        //将从数据库中查询到的权限信息封装到AuthorizationInfo中去
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole(String.valueOf(employeeRid));
        return simpleAuthorizationInfo;
    }

    /**
     * 获取用户信息，做认证登录校验
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取数据库要校验的用户信息
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        //获取页面传入的用户名
        String jobnmber = token.getUsername();
        EmployeeExample employeeExample = new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andJobnumberEqualTo(jobnmber);
        List<Employee> employeeList =  employeeMapper.selectByExample(employeeExample);
        if(0!=employeeList.size()){
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(employeeList.get(0),employeeList.get(0).getPassword(),jobnmber);

            ((SimpleAuthenticationInfo) authenticationInfo).setCredentialsSalt(ByteSource.Util.bytes(employeeList.get(0).getRemark()));
            return authenticationInfo;
        }
        return null;
    }
}
