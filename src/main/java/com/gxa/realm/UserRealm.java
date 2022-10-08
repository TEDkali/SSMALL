package com.gxa.realm;

import com.gxa.entity.User;
import com.gxa.mapper.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * AUTHOR:林溪
 * CREATE_DATE:2022/10/1 10:27
 * VERSION:1.0
 */
public class UserRealm extends AuthorizingRealm {
    //调用赋予mapper属性
    @Autowired
    private UserMapper userMapper;
    //认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("AuthenticationInfo method");
        //将形参转化为token
        UsernamePasswordToken upt = (UsernamePasswordToken)token;
        //从token中获取username,并根据userName去数据查询是否有该数据
        User user = userMapper.queryByUserName(upt.getUsername());
        //三个参数,1.查询返回的user对象,2.数据库保存的user密码,3.
        SimpleAuthenticationInfo authorizationInfo = new SimpleAuthenticationInfo(user,user.getPwd(),this.getName());
        //用户密码是否符合规范,AuthenticationInfo通过抛出异常的方式
        return authorizationInfo;
    }
    //授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo sa = new SimpleAuthorizationInfo();
        System.out.println("AuthorizationInfo method");
//授权
        //首先获取当前登录用户
        User u=(User)principalCollection.getPrimaryPrincipal();

        System.out.println(u);
        //权限set集合,
        Set<String> perms = new HashSet<>();
        //获取特征值,判断该用户身份
        String username = u.getUserName();
        //对用户进行判断,并赋予对应的权限
        if(username.equals("123")){
            System.out.println("all");
            perms.add("emp:list");
            perms.add("emp:add");
            perms.add("emp:update");
            perms.add("emp:delete");
        }else if(username.equals("qwe")){
            System.out.println("three");
            perms.add("emp:list");
            perms.add("emp:add");
            perms.add("emp:update");
        }else if(username.equals("asd")){
            System.out.println("two");
            perms.add("emp:list");
            perms.add("emp:update");
        }else if(username.equals("zxc")){
            System.out.println("one");
            perms.add("emp:list");
        }
        //将权限添加到权限认证对象
        sa.addStringPermissions(perms);
        return sa;
    }


}
