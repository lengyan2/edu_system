package com.example.acl.service.impl;

import com.atguigu.serurity.entity.SecurityUser;
import com.atguigu.serurity.security.DefaultPasswordEncoder;
import com.example.acl.entity.User;
import com.example.acl.service.PermissionService;
import com.example.acl.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 * 自定义userDetailsService - 认证用户详情
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;


    DefaultPasswordEncoder defaultPasswordEncoder;
    /***
     * 根据账号获取用户信息
     * @param username:
     * @return: org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中取出用户信息
        User user = userService.selectByUsername(username);
        System.out.println(user);
        // 判断用户是否存在
        if (null == user){
            //throw new UsernameNotFoundException("用户名不存在！");
        }
        // 返回UserDetails实现类
        com.atguigu.serurity.entity.User curUser = new com.atguigu.serurity.entity.User();
        BeanUtils.copyProperties(user,curUser);
        System.out.println(curUser);
//        String password = defaultPasswordEncoder.encode(curUser.getPassword());
//        curUser.setPassword(password);
        List<String> authorities = permissionService.selectPermissionValueByUserId(user.getId());
        SecurityUser securityUser = new SecurityUser(curUser);
        securityUser.setCurrentUserInfo(curUser);
        securityUser.setPermissionValueList(authorities);
        return securityUser;
    }

}
