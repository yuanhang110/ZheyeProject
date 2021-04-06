package com.evostar.controller;

import com.evostar.ApplicationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class QuestionControllerTest extends ApplicationTests {
    private String token = "token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJTRVJWSUNFIiwiZXhwIjoxNTk0Mzg5Mjg5LCJ1c2VySWQiOjEsInZlcnNpb24iOiIxLjAiLCJpYXQiOjE1OTQzODIwODl9.0gf6gItlPPo2inQVlFnvDU1efLOWwTW_Ud_G5Ljfn9k";
    @Test
    public void testQuestionAdd(){
        HashMap<String, String> map = new HashMap<>();
        map.put("title", "zhangs");
        map.put("content", "133456");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie",token);
        HttpEntity<HashMap<String, String>> request = new HttpEntity<>(map,headers);
        ResponseEntity<String> entity = restTemplate.exchange("/question/add", HttpMethod.POST, request, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testAnswerAdd(){
        HashMap<String, String> map = new HashMap<>();
        map.put("qid", "1");
        map.put("content", "133456");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie",token);
        HttpEntity<HashMap<String, String>> request = new HttpEntity<>(map,headers);
        ResponseEntity<String> entity = restTemplate.exchange("/answer/add", HttpMethod.POST, request, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testQeestionDetail(){
        ResponseEntity<String> entity = restTemplate.getForEntity("/question/detail?qid=1", String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }
}