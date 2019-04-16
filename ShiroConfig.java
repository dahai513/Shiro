package com.itheima.controller.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro的配置类
 *
 * @author
 */

@Configuration
public class ShiroConfig {
    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securtyManager")DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager  );

        //添加shiro内置过滤器
        /**
         * Shiro内置过滤器，可以实现权限相关的拦截器
         * 常用的过滤器：
         * anon:无需认证（登录） 可以访问
         * authc：必须认证才可访问
         * user:如果适用rememberMe的功能可以直接访问
         * perms：该资源必须得到资源权限才可以访问
         * role：该资源必须得到角色权限才可以访问
         */

        Map<String,String> filterMap = new LinkedHashMap<String, String>(  );
//        filterMap.put( "/add","authc" );
//        filterMap.put( "/update","authc" );

//        filterMap.put( "/add","anon" );
        filterMap.put( "/freemark","anon" );
        //放行login请求
        filterMap.put( "/login","anon" );

        //授权过滤器
        //注意：当前授权拦截后，shiro会字段跳转到未授权的页面
        filterMap.put( "/add","perms[user:add]" );

        filterMap.put( "/*","authc" );


        //修改跳转页面
        shiroFilterFactoryBean.setLoginUrl( "/tologin" );

        //设置未授权提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl( "/unAuth" );
        shiroFilterFactoryBean.setFilterChainDefinitionMap( filterMap );



        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     */
    @Bean(name = "securtyManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm
        securityManager.setRealm( userRealm );

        return securityManager;
    }

    /**
     * 创建Realm
     */
    @Bean(name = "userRealm")
    public UserRealm getRealm() {
        return new UserRealm();
    }
}
