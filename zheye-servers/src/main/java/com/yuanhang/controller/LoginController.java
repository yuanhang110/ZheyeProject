package com.yuanhang.controller;

import com.yuanhang.VO.LoginVO;
import com.yuanhang.exception.ServiceException;
import com.yuanhang.model.MsgCodeEnum;
import com.yuanhang.model.User;
import com.yuanhang.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 肖远航
 * 相当于@Controller+@ResponseBody，返回json用的
 */
@RestController
@Api(tags = "登陆")
public class LoginController {
    @Autowired
    private UserService userService;

    @ApiOperation("注册")
    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "注册账号", required = true, defaultValue = "zhangsan", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, defaultValue = "123456", dataType = "String")
    })
    /***
     *  controller从前端获得用户输入的参数值然后reg方法调用service的reg
     */
    public HashMap<String, String> reg(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");

        if (userService.reg(username, password) > 0) {
            HashMap<String, String> result = new HashMap<>();
            result.put("msg", "SUCCESS");
            return result;

        } else {
            throw new ServiceException(MsgCodeEnum.REGISTERED_FAILED.getCode(), MsgCodeEnum.REGISTERED_FAILED.getMessage());
        }
    }

    @ApiOperation("登陆")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号", required = true, defaultValue = "zhangsan", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, defaultValue = "123456", dataType = "String"),
            @ApiImplicitParam(name = "rememberme", value = "7天免登陆", required = false, defaultValue = "false", dataType = "Boolean")
    })
    public LoginVO login(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");
        Boolean rememberme = Boolean.valueOf(map.get("rememberme"));
        User user = userService.login(username, password, rememberme);
        /***
         * 初始化cookie，获得设置的token
         */
        Cookie cookie = new Cookie("token", user.getToken());
        cookie.setPath("/");
        /***
         * 设置cookie有效时间7天
         */
        cookie.setMaxAge(7 * 24 * 60 * 60);
        LoginVO loginVO = new LoginVO();
        loginVO.setMsg("SUCCESS");
        loginVO.setMsg("/");
        loginVO.setUser(user);
        return loginVO;
    }

    @ApiOperation("退出")
    @RequestMapping(value = "/layout", method = RequestMethod.GET)
    public HashMap<String, String> layout() {
        Cookie cookie = new Cookie("token", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        HashMap<String, String> result = new HashMap<>();
        result.put("msg", "SUCCESS");
        return result;
    }
}
