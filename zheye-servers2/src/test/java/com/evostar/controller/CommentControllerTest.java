package com.evostar.controller;

import com.evostar.ApplicationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CommentControllerTest extends ApplicationTests {
    private String token = "token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJTRVJWSUNFIiwiZXhwIjoxNTk1Mzg0MzE3LCJ1c2VySWQiOjEsInZlcnNpb24iOiIxLjAiLCJpYXQiOjE1OTQ3Nzk1MTd9.f-DWlFeH2DBWdW9-iUDvjA-ntX3wIidhRNA0qfNF-jY";

    @Test
    public void testAddComment(){
        HashMap<String, String> map = new HashMap<>();
        map.put("id", "1");
        map.put("type","1");
        map.put("content", "133456");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie",token);
        HttpEntity<HashMap<String, String>> request = new HttpEntity<>(map,headers);
        ResponseEntity<String> entity = restTemplate.exchange("/comment/add", HttpMethod.POST, request, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testCommentList(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("cookie",token);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> entity = restTemplate.exchange("/comment/list?id=1&type=1", HttpMethod.GET, request, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }
}