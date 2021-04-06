package com.evostar.controller;

import com.evostar.model.HostHolder;
import com.evostar.service.SupportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Api(tags = "点赞")
@RestController
public class SupportController {
    @Autowired
    private SupportService supportService;
    @Autowired
    private HostHolder hostHolder;
    //点赞
    @ApiOperation(value = "点赞")
    @RequestMapping(value = "/support", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "点赞的id", defaultValue = "1", required = true),
            @ApiImplicitParam(name = "type", value = "类型，1代表给question，2answer，3comment", defaultValue = "1", required = true)
    })
    public HashMap<String, String> support(@RequestParam(required = true) int id, @RequestParam(required = true) int type){
        //判断id对应的数据是否存在
        supportService.checkExist(id, type);
        supportService.support(id, type, hostHolder.getUser().getId());
        HashMap<String, String> result =new HashMap<>();
        result.put("msg", "SUCCESS");
        return result;
    }

    //取消赞
    @ApiOperation(value = "取消赞")
    @RequestMapping(value = "/support_cancel", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "点赞的id", defaultValue = "1", required = true),
            @ApiImplicitParam(name = "type", value = "类型，1代表给question，2answer，3comment", defaultValue = "1", required = true)
    })
    public HashMap<String, String> support_cancel(@RequestParam(required = true) int id, @RequestParam(required = true) int type){
        //判断id对应的数据是否存在
        supportService.checkExist(id, type);
        supportService.support_cancel(id, type, hostHolder.getUser().getId());
        HashMap<String, String> result =new HashMap<>();
        result.put("msg", "SUCCESS");
        return result;
    }


    //点踩
    @ApiOperation(value = "点踩")
    @RequestMapping(value = "/unsupport", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "点踩的id", defaultValue = "1", required = true),
            @ApiImplicitParam(name = "type", value = "类型，1代表给question，2answer，3comment", defaultValue = "1", required = true)
    })
    public HashMap<String, String> unSupport(@RequestParam(required = true) int id, @RequestParam(required = true) int type){
        //判断id对应的数据是否存在
        supportService.checkExist(id, type);
        supportService.unSupport(id, type, hostHolder.getUser().getId());
        HashMap<String, String> result =new HashMap<>();
        result.put("msg", "SUCCESS");
        return result;
    }



    //去消踩
    @ApiOperation(value = "取消踩")
    @RequestMapping(value = "/unsupport_cancel", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "点踩的id", defaultValue = "1", required = true),
            @ApiImplicitParam(name = "type", value = "类型，1代表给question，2answer，3comment", defaultValue = "1", required = true)
    })
    public HashMap<String, String> unsupport_cancel(@RequestParam(required = true) int id, @RequestParam(required = true) int type){
        //判断id对应的数据是否存在
        supportService.checkExist(id, type);
        supportService.unsupport_cancel(id, type, hostHolder.getUser().getId());
        HashMap<String, String> result =new HashMap<>();
        result.put("msg", "SUCCESS");
        return result;
    }
}
