package com.evostar.netty.handler;

import com.evostar.VO.UserVO;
import com.evostar.model.ChatRecord;
import com.evostar.model.User;
import com.evostar.netty.request.LoginRequestPacket;
import com.evostar.netty.request.MessageRequestPacket;
import com.evostar.netty.response.MessageResponsePacket;
import com.evostar.netty.utils.Session;
import com.evostar.netty.utils.SessionUtil;
import com.evostar.netty.utils.SpringUtil;
import com.evostar.service.ChatRecordService;
import com.evostar.service.UserService;
import com.evostar.utils.RedisUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    private static UserService userService;
    private static ChatRecordService chatRecordService;
    private static RedisUtils redisUtils;
    static {
        userService = SpringUtil.getBean(UserService.class);
        chatRecordService = SpringUtil.getBean(ChatRecordService.class);
        redisUtils = SpringUtil.getBean(RedisUtils.class);
    }

    public final static MessageRequestHandler INSTANCE = new MessageRequestHandler();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        MessageResponsePacket responsePacket = new MessageResponsePacket();
        // 获取当前的人
        Session session = SessionUtil.getSession(ctx.channel());
        UserVO fromUser = session.getUserVO();
        responsePacket.setFromUser(fromUser);
        responsePacket.setContent(msg.getContent());
        responsePacket.setTime(msg.getTime());
        int toUserId = msg.getToUserId();
        User toUser = userService.selectById(toUserId);
        if(toUser != null){
            UserVO toUserVO = userService.getUserVO(toUser);
            responsePacket.setToUser(toUserVO);
            this.unreadNumInc(fromUser.getId(), toUserId);
            int unreadNum = this.getUnreadNum(fromUser.getId(), toUserId);
            responsePacket.setUnreadNum(unreadNum);

            this.save_record(responsePacket);
            Channel toUserChannel = SessionUtil.getChannel(String.valueOf(msg.getToUserId()));
            if(toUserChannel != null){
                toUserChannel.writeAndFlush(responsePacket);
            }
            ctx.channel().writeAndFlush(responsePacket);
        }else{
            System.out.println("用户不存在");
        }

    }

    public void save_record(MessageResponsePacket messageResponsePacket){
        //保存聊天记录
        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setFromUserId(messageResponsePacket.getFromUser().getId());
        chatRecord.setToUserId(messageResponsePacket.getToUser().getId());
        chatRecord.setContent(messageResponsePacket.getContent());
        chatRecord.setTime(messageResponsePacket.getTime());
        chatRecordService.addRecord(chatRecord);
    }
    //获取未读数量
    public int getUnreadNum(int fromUserId, int toUserId){
        String key = fromUserId+"_UnreadNum_"+toUserId;
        if(!redisUtils.hasKey(key)){
            return 1;
        }else{
            return Integer.parseInt((String) redisUtils.getValue(key));
        }
    }
    //未读数量+1
    public void unreadNumInc(int fromUserId, int toUserId){
        String key = fromUserId+"_UnreadNum_"+toUserId;
        redisUtils.increment(key, 1);
    }


}