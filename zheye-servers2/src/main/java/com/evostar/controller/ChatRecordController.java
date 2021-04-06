package com.evostar.controller;

import com.evostar.VO.CharListVO;
import com.evostar.VO.ChatRecordVO;
import com.evostar.VO.UserVO;
import com.evostar.model.HostHolder;
import com.evostar.model.User;
import com.evostar.service.ChatRecordService;
import com.evostar.service.FollowService;
import com.evostar.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ChatRecordController {
    @Autowired
    private ChatRecordService chatRecordService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private FollowService followService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "清除未读消息提示")
    @RequestMapping(value = "/clear_unread_num", method = RequestMethod.GET)
    @ApiImplicitParam(name = "froUserId", value = "发送消息的人的id", required = true, defaultValue = "1")
    public HashMap<String, String> clearUnreadNum(int fromUserId){
        chatRecordService.clearUnreadNum(fromUserId, hostHolder.getUser().getId());
        HashMap<String, String> result =new HashMap<>();
        result.put("msg", "SUCCESS");
        return result;
    }

    @ApiOperation(value = "获取聊天记录")
    @RequestMapping(value = "/chat_record", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数，默认为1", defaultValue = "1", required = false),
            @ApiImplicitParam(name = "chatUserId", value = "聊天对象的id", defaultValue = "2", required = true)
    })
    public List<ChatRecordVO> recordList(@RequestParam(value = "page", required = false, defaultValue = "1") int page, int chatUserId){
        int limit = 20;
        int offset = (page - 1) * limit;
        int userId = hostHolder.getUser().getId();
        List<ChatRecordVO> chatRecordVOList = chatRecordService.getChatList(chatUserId, userId, offset, limit);
        Collections.reverse(chatRecordVOList);
        return chatRecordVOList;
    }


    @ApiOperation(value = "聊天列表")
    @RequestMapping(value = "/chatList", method = RequestMethod.GET)
    public List<CharListVO> chatList(){
        //获取我关注的人
        List<String> followedUser = followService.getFollowUsers(hostHolder.getUser().getId());
        //获取关注我的人
        List<String> followUser = followService.getFollowedUsers(hostHolder.getUser().getId());
        //去重合并
        followedUser.removeAll(followUser);
        followedUser.addAll(followUser);
        List<User> userList = userService.getUserByIds(followedUser);
        return userList.stream().map(user -> {
            CharListVO chatListVO = new CharListVO();
            UserVO userVO = userService.getUserVO(user);
            chatListVO.setUser(userVO);

            List<ChatRecordVO> recordVOList = chatRecordService.getChatList(user.getId(), hostHolder.getUser().getId(), 0, 1);
            if(recordVOList.size() != 0){
                String content = recordVOList.get(0).getContent();
                String summary = content.length() >= 10 ? content.substring(0, 10)+"...." : content;
                chatListVO.setContent(summary);
                chatListVO.setTime(recordVOList.get(0).getTime());
            }
            int unreadNum = chatRecordService.getUnreadNum(user.getId(), hostHolder.getUser().getId());
            chatListVO.setUnreadNum(unreadNum);
            return chatListVO;
        }).collect(Collectors.toList());

    }
}
