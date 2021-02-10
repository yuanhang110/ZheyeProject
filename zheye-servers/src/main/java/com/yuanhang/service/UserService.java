package com.yuanhang.service;

import com.yuanhang.dao.UserDAO;
import com.yuanhang.exception.ServiceException;
import com.yuanhang.model.MsgCodeEnum;
import com.yuanhang.model.User;
import com.yuanhang.utils.JwtUtils;
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
    public int reg(String username, String password) {
        /***
         * 验证username是否为空
         */
        if (StringUtils.isEmpty(username)) {
            throw new ServiceException(MsgCodeEnum.ACCOUNT_EMPTY.getCode(), MsgCodeEnum.ACCOUNT_ERROR.getMessage());
        }
        if (StringUtils.isEmpty(password)) {
            throw new ServiceException(MsgCodeEnum.PARAM_EMPTY.getCode(), MsgCodeEnum.PASSWORD_EMPTY.getMessage());
        }
        //当用户名和密码都不为空，判断用户是否已经注册过
        User user = userDAO.selectByName(username);
        if (user != null) {
            throw new ServiceException(MsgCodeEnum.ACCOUNT_REGISTERED.getCode(), MsgCodeEnum.ACCOUNT_REGISTERED.getMessage());
        }
        /***
         * 未注册就注册
         * UUID.randomUUID().toString()是javaJDK提供的一个自动生成主键的方法。
         * UUID(Universally Unique Identifier)
         * 全局唯一标识符,是指在一台机器上生成的数字，它保证对在同一时空中的所有机器都是唯一的，
         * 是由一个十六位的数字组成,表现出来的 形式。由以下几部分的组合：
         * 目的是通过密码加5位数的随机字符串再进行MD5加密
         */
        User user1 = new User();
        user1.setName(username);
        String salt = UUID.randomUUID().toString().substring(0, 5);
        user1.setSalt(salt);
        String newPassword = DigestUtils.md5Hex(password + salt);
        user1.setPassword(newPassword);
        user1.setHeadUrl("https://b-ssl.duitang.com/uploads/item/201703/20/20170320112905_5zhQ3.jpeg");
        return userDAO.addUser(user1);
    }

    public User login(String username, String password, Boolean rememberme) {
        if (StringUtils.isEmpty(username)) {
            throw new ServiceException(MsgCodeEnum.ACCOUNT_EMPTY.getCode(), MsgCodeEnum.ACCOUNT_ERROR.getMessage());
        }
        if (StringUtils.isEmpty(password)) {
            throw new ServiceException(MsgCodeEnum.PARAM_EMPTY.getCode(), MsgCodeEnum.PASSWORD_EMPTY.getMessage());
        }
        //当用户名和密码都不为空，判断用户是否已经存在
        User user = userDAO.selectByName(username);
        if (user == null) {
            throw new ServiceException(MsgCodeEnum.ACCOUNT_ERROR.getCode(), MsgCodeEnum.ACCOUNT_ERROR.getMessage());
        }
        //如果密码错误，密码加salt码md5值和数据库中存的不一样
        if (!DigestUtils.md5Hex(password + user.getSalt()).equals(user.getPassword())) {
            throw new ServiceException(MsgCodeEnum.PASSWORD_ERROR.getCode(), MsgCodeEnum.PASSWORD_ERROR.getMessage());
        }
        //7天免登陆,单位分钟
        int expire = rememberme ? 7 * 24 * 30 : 120;
        //token
        String token = JwtUtils.createJWTToken(user.getId(), expire);
        user.setToken(token);
        return user;
    }

}
