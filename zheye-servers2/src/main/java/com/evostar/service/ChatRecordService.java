package com.evostar.service;

import com.evostar.VO.ChatRecordVO;
import com.evostar.dao.ChatRecordDAO;
import com.evostar.model.ChatRecord;
import com.evostar.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRecordService {
    @Autowired
    private ChatRecordDAO chatRecordDAO;
    @Autowired
    private RedisUtils redisUtils;
    public int addRecord(ChatRecord chatRecord){
        return chatRecordDAO.addChatRecord(chatRecord);
    }


    public void clearUnreadNum(int fromUserId, int toUserId){
        String key = fromUserId+"_UnreadNum_"+toUserId;
        redisUtils.clearNum(key);
    }

    public List<ChatRecordVO> getChatList(int chatUserId, int userId, int offset, int limit){
        return chatRecordDAO.getChatRecordList(chatUserId, userId, offset, limit);
    }

    public int getUnreadNum(int fromUserId, int toUserId){
        String key = fromUserId+"_UnreadNum_"+toUserId;
        if(!redisUtils.hasKey(key)){
            return 0;
        }else{
            return Integer.parseInt((String) redisUtils.getValue(key));
        }
    }
}
