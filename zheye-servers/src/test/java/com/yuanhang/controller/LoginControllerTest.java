package com.yuanhang.controller;

import com.yuanhang.ApplicationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

class LoginControllerTest extends ApplicationTests {
    @Test
    public void testReg() {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", "zhangs");
        map.put("password", "133456");
        /***
         * ResponseEntity ：标识整个http相应：状态码、头部信息、响应体内容(spring)
         */
        ResponseEntity<String> entity = restTemplate.postForEntity("/reg", map, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
        /***
         * 返回的httpstatus，要的和实际执行的一样就正确
         */
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }


    @Test
    public void testLogin() {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", "zhangSan");
        map.put("password", "123456");
        ResponseEntity<String> entity = restTemplate.postForEntity("/login", map, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testLayout() {
        ResponseEntity<String> entity = restTemplate.getForEntity("/layout", String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }
}