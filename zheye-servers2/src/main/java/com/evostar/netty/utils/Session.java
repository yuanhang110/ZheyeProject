package com.evostar.netty.utils;

import com.evostar.VO.UserVO;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Session会话（用户信息）
 */
@Data
@NoArgsConstructor
public class Session {

    /**
     * 用户唯一标识
     */
    private UserVO userVO;

    public Session(UserVO userVO) {
        this.userVO = userVO;
    }

    @Override
    public String toString() {
        return userVO + ":" + userVO;
    }
}
