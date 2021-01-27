package com.yuanhang.controller;

import com.yuanhang.exception.ServiceException;
import com.yuanhang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 肖远航
 */
@RestController   //相当于@Controller+@ResponseBody，返回json用的
public class LoginController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/reg")
    /***
     *  controller从前端获得用户输入的参数值然后reg方法调用service的reg
     */
    public HashMap<String, String> reg(@RequestBody Map<String,String> map){
        String username=map.get("username");
        String password=map.get("password");

        if(userService.reg(username,password)>0){
            HashMap<String, String> result = new HashMap<>();
            result.put("msg","SUCCESS");
            return result;

        }else {
            throw new ServiceException("400","注册失败");
        }

    }
}
