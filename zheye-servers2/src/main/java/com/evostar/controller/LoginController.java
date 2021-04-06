package com.evostar.controller;

import com.evostar.VO.LoginVO;
import com.evostar.exception.ServiceException;
import com.evostar.model.ErrorCode;
import com.evostar.model.MsgCodeEnum;
import com.evostar.model.User;
import com.evostar.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@RestController
@Api(tags = "登录")
public class LoginController {
    @Autowired
    private UserService userService;
    @ApiOperation("注册")
    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "注册账号", required = true, defaultValue = "zhangsan", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, defaultValue = "123456", dataType = "String")
    })
    public HashMap<String, String> reg(@RequestBody Map<String, String> map){
        String username = map.get("username");
        String password = map.get("password");
        if (userService.reg(username, password) > 0){
            HashMap<String, String> result =new HashMap<>();
            result.put("msg", "SUCCESS");
            return result;
        }else {
            throw new ServiceException(MsgCodeEnum.REGISTERED_FAILED);
        }
    }

    @ApiOperation("登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号", required = true, defaultValue = "zhangsan", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, defaultValue = "123456", dataType = "String"),
            @ApiImplicitParam(name = "rememberme", value = "七天免登录", required = false, defaultValue = "false", dataType = "Boolean")

    })
    public LoginVO login(HttpServletResponse response, @RequestBody Map<String, String> map){
        String username = map.get("username");
        String password = map.get("password");
        Boolean rememberme = Boolean.valueOf(map.get("rememberme"));
        User user = userService.login(username, password, rememberme);
        Cookie cookie = new Cookie("token", user.getToken());
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);
        LoginVO loginVO = new LoginVO();
        loginVO.setMsg("SUCCESS");
        loginVO.setMsg("/");
        loginVO.setUser(user);
        return loginVO;
    }

    @ApiOperation("退出")
    @RequestMapping(value = "/layout", method = RequestMethod.GET)
    public HashMap<String, String> layout(){
        Cookie cookie = new Cookie("token", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        HashMap<String, String> result =new HashMap<>();
        result.put("msg", "SUCCESS");
        return result;
    }

}
