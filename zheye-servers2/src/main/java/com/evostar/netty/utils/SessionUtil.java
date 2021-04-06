package com.evostar.netty.utils;

import com.evostar.netty.attribute.Attributes;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会话工具类
 */
public class SessionUtil {

    /**
     * userId -> Channel 的映射
     */
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    /**
     * 登录成功后缓存【用户 -> 用户连接】的映射关系
     *
     * @param session 会话信息
     * @param channel 连接
     */
    public static void bindSession(Session session, Channel channel) {
        System.out.println("缓存【userId:channel】映射 " + session.getUserVO().getId() + ":" + channel.toString());
        userIdChannelMap.put(String.valueOf(session.getUserVO().getId()), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    /**
     * 退出登录或断开连接后清除【用户 -> 用户连接】的映射关系
     *
     * @param channel 连接
     */
    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            System.out.println("移除【userId:channel】映射 " + getSession(channel).getUserVO().getId() + ":" + channel.toString());
            userIdChannelMap.remove(String.valueOf(getSession(channel).getUserVO().getId()));
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    /**
     * 根据连接判断用户是否已经登录
     *
     * @param channel 连接
     * @return true 则表示已登录
     */
    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }

    /**
     * 根据连接获取用户会话信息
     *
     * @param channel 连接
     * @return 用户会话信息
     */
    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    /**
     * 根据用户ID获取用户连接
     *
     * @param userId 用户ID
     * @return 用户连接
     */
    public static Channel getChannel(String userId) {
        return userIdChannelMap.get(userId);
    }

}
