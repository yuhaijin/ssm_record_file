package com.qf.realm;

import com.qf.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取的当前账户用户名
        String username = (String) principalCollection.getPrimaryPrincipal();
        //根据用户名查询数据库中的角色账户表，获取到该账户对应的角色列表
        Set<String> roles = new HashSet<String>();
        String roleByName = userService.getRoleByName(username);
        roles.add(roleByName);
        //根据角色列表分别从数据库的角色权限表中查询出该角色下对应的权限列表
        Set<String> permissions = new HashSet<String>();
        List<String> list = userService.getpromission(roleByName);
        for(String permission :list){
            permissions.add(permission);
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo =
                new SimpleAuthorizationInfo(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String)authenticationToken.getPrincipal();
        //数据库中根据用户名查询密码
        String password = userService.getPassword(username);
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username,password,"UserRealm");
        return simpleAuthenticationInfo;
    }
}
