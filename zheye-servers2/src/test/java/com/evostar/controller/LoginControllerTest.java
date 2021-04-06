package com.evostar.controller;

import com.evostar.ApplicationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest extends ApplicationTests {

    @Test
    public void testReg(){
        HashMap<String, String> map = new HashMap<>();
        map.put("username", "zhangs");
        map.put("password", "133456");
        ResponseEntity<String> entity = restTemplate.postForEntity("/reg", map, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }


    @Test
    public void testLogin(){
        HashMap<String, String> map = new HashMap<>();
        map.put("username", "zhangSan");
        map.put("password", "123456");
        ResponseEntity<String> entity = restTemplate.postForEntity("/login", map, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testLayout(){
        ResponseEntity<String> entity = restTemplate.getForEntity("/layout", String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }
}