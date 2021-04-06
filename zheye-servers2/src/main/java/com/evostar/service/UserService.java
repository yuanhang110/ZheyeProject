package com.evostar.service;

import com.evostar.VO.UserVO;
import com.evostar.dao.UserDAO;
import com.evostar.exception.ServiceException;
import com.evostar.model.HostHolder;
import com.evostar.model.MsgCodeEnum;
import com.evostar.model.User;
import com.evostar.utils.JwtUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private FollowService followService;
    public int reg(String username, String password){
        if(StringUtils.isEmpty(username)){
            throw new ServiceException(MsgCodeEnum.ACCOUNT_EMPTY.getCode(),MsgCodeEnum.ACCOUNT_EMPTY.getMessage());
        }
        if(StringUtils.isEmpty(password)){
            throw new ServiceException(MsgCodeEnum.PASSWORD_EMPTY.getCode(),MsgCodeEnum.PASSWORD_EMPTY.getMessage());
        }
        //用户是否已经注册过
        User user = userDAO.selectByName(username);
        if(user != null){
            throw new ServiceException(MsgCodeEnum.ACCOUNT_REGISTERED.getCode(), MsgCodeEnum.ACCOUNT_REGISTERED.getMessage());
        }
        User user1 = new User();
        user1.setName(username);
        String salt = UUID.randomUUID().toString().substring(0, 5);
        user1.setSalt(salt);
        String newPassword = DigestUtils.md5Hex(password + salt);
        user1.setPassword(newPassword);
        user1.setHeadUrl("https://b-ssl.duitang.com/uploads/item/201703/20/20170320112905_5zhQ3.jpeg");
        return userDAO.addUser(user1);
    }


    public User login(String username, String password, Boolean rememberme){
        if(StringUtils.isEmpty(username)){
            throw new ServiceException(MsgCodeEnum.ACCOUNT_EMPTY.getCode(),MsgCodeEnum.ACCOUNT_EMPTY.getMessage());
        }
        if(StringUtils.isEmpty(password)){
            throw new ServiceException(MsgCodeEnum.PASSWORD_EMPTY.getCode(),MsgCodeEnum.PASSWORD_EMPTY.getMessage());
        }
        User user = userDAO.selectByName(username);
        if(user == null){
            throw new ServiceException(MsgCodeEnum.ACCOUNT_ERROR.getCode(), MsgCodeEnum.ACCOUNT_ERROR.getMessage());
        }
        if (!DigestUtils.md5Hex(password + user.getSalt()).equals(user.getPassword())){
            throw new ServiceException(MsgCodeEnum.PASSWORD_ERROR.getCode(), MsgCodeEnum.PASSWORD_ERROR.getMessage());
        }
        //7天免登录
        int expire = rememberme ? 7 * 24 * 60 : 120;
        //token
        String token = JwtUtils.createJWTToken(user.getId(), expire);
        user.setToken(token);
        return user;
    }

    public UserVO getUserVO(User user){
        UserVO userVO = new UserVO();
        userVO.setName(user.getName());
        userVO.setHeadUrl(user.getHeadUrl());
        userVO.setId(user.getId());
        int answerNum = answerService.getAnswerCountByUserId(user.getId());
        userVO.setAnswerNum(answerNum);
        if(hostHolder.getUser() != null){
            userVO.setIsFollow(followService.isFollowUser(user.getId(), hostHolder.getUser().getId()));
        }
        userVO.setFollowNum(followService.followUserNum(user.getId()));
        return userVO;
    }

    public User selectById(int id){
        return userDAO.selectById(id);
    }

    public List<User> getUserByIds(List<String> userList){
        return userDAO.getUserListByIds(userList);
    }
}
