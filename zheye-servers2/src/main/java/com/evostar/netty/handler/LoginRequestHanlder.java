package com.evostar.netty.handler;

import com.evostar.VO.UserVO;
import com.evostar.model.User;
import com.evostar.netty.request.LoginRequestPacket;
import com.evostar.netty.response.LoginResponsePacket;
import com.evostar.netty.utils.Session;
import com.evostar.netty.utils.SessionUtil;
import com.evostar.netty.utils.SpringUtil;
import com.evostar.service.UserService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class LoginRequestHanlder extends SimpleChannelInboundHandler<LoginRequestPacket> {
    private static UserService userService;
    static {
        userService = SpringUtil.getBean(UserService.class);
    }
    public final static LoginRequestHanlder INSTANCE = new LoginRequestHanlder();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        //处理登录请求数据包
        LoginResponsePacket response = new LoginResponsePacket();
        int userId = Integer.parseInt(msg.getUserId());
        User user = userService.selectById(userId);
        if(user != null){
            //缓存当前用户和channel的关系
            UserVO userVO = userService.getUserVO(user);
            Session session = new Session(userVO);
            SessionUtil.bindSession(session, ctx.channel());
            response.setSuccess(true);
            response.setName(userVO.getName());
            System.out.println(userVO.getName()+"登录成功");
        }else{
            response.setSuccess(false);
            response.setReason("当前用户不存在");
        }
        ctx.channel().writeAndFlush(response);
    }
}
