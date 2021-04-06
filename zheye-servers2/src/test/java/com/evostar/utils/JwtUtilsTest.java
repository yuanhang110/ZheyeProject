package com.evostar.utils;

import com.auth0.jwt.interfaces.Claim;
import com.evostar.ApplicationTests;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsTest extends ApplicationTests {

    @Test
    public void testToken(){
        String token = JwtUtils.createJWTToken(2, 120);
        Map<String, Claim> map =  JwtUtils.verifyJWTToken(token);

        int userId = map.get("userId").asInt();
        String version = map.get("version").asString();
        System.out.println("userId:"+userId+",version:"+version);
    }

}