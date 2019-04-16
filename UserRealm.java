package com.itheima.controller.shiro;

import com.itheima.domain.User;
import com.itheima.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义Realm
 */
public class UserRealm extends AuthorizingRealm {
    /**
     * 执行授权逻辑
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println( "执行授权逻辑" );
        //给资源授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(  );

        //添加资源授权字符串
        info.addStringPermission( "user:add" );
        return null;
    }


    @Autowired
    private UserService userService;

    /**
     * 执行认证逻辑
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println( "执行认证逻辑" );
//        //假设数据库的用户名和密码
//        String name = "sea";
//        String password = "321";

        //编写shiro判断逻辑，判断用户名和密码
        //1.判断用户名
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.findByname( token.getUsername() );
        if (user == null) {
            //用户名不存在
            return null;//shiro底层会抛出UnknownAccountException
        }
        //2判断密码
        return new SimpleAuthenticationInfo( "", user.getPassword(), "" );
    }
}
