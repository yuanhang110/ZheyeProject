package com.yuanhang.service;

import com.yuanhang.dao.UserDAO;
import com.yuanhang.exception.ServiceException;
import com.yuanhang.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @author 肖远航
 * @Autowired注解 Spring容器中所有了类型匹配的bean都被注入进来，
 * 并且如果bean有@Order注解或者实现Order接口，按照Order的先后顺序注入;
 */
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    /***
     * 注册
     */
    public int reg(String username,String password){
        /***
         * 验证username是否为空
         */
        if(StringUtils.isEmpty(username)){
            throw new ServiceException("400","username is empty");
        }
        if(StringUtils.isEmpty(password)){
            throw new ServiceException("400","password is empty");
        }
        //当用户名和密码都不为空，判断用户是否已经注册过
        User user=userDAO.selectByName(username);
        if(user!=null){
            throw new ServiceException("400","账号已注册");
        }
        /***
         * 未注册就注册
         * UUID.randomUUID().toString()是javaJDK提供的一个自动生成主键的方法。
         * UUID(Universally Unique Identifier)
         * 全局唯一标识符,是指在一台机器上生成的数字，它保证对在同一时空中的所有机器都是唯一的，
         * 是由一个十六位的数字组成,表现出来的 形式。由以下几部分的组合：
         * 目的是通过密码加5位数的随机字符串再进行MD5加密
         */
        User user1=new User();
        user1.setName(username);
        String salt= UUID.randomUUID().toString().substring(0,5);
        user1.setSalt(salt);
        String newPassword=DigestUtils.md5Hex(password+salt);
        user1.setPassword(newPassword);
        return userDAO.addUser(user);
    }
}
