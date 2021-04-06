package com.evostar.service;

import com.evostar.exception.ServiceException;
import com.evostar.model.EntityType;
import com.evostar.model.MsgCodeEnum;
import org.springframework.stereotype.Service;

@Service
public class EntityService {
    public String getKeyByType(int type){
        String key = "";
        for(EntityType entityType : EntityType.values()){
            if(entityType.getType() == type){
                key = entityType.getKey();
                break;
            }
        }
        if(key.equals("")){
            throw new ServiceException(MsgCodeEnum.PARAM_ERROR);
        }
        return key;
    }

}
