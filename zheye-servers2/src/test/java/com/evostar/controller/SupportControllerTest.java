package com.evostar.controller;

import com.evostar.ApplicationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

class SupportControllerTest extends ApplicationTests {
    private String token = "token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJTRVJWSUNFIiwiZXhwIjoxNTk1Mzg0MzE3LCJ1c2VySWQiOjEsInZlcnNpb24iOiIxLjAiLCJpYXQiOjE1OTQ3Nzk1MTd9.f-DWlFeH2DBWdW9-iUDvjA-ntX3wIidhRNA0qfNF-jY";

    @Test
    public void testSupport(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("cookie",token);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> entity = restTemplate.exchange("/support?id=1&type=1", HttpMethod.GET, request, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }
    @Test
    public void testUnSupport(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("cookie",token);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> entity = restTemplate.exchange("/unsupport?id=1&type=1", HttpMethod.GET, request, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testSupportCancel(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("cookie",token);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> entity = restTemplate.exchange("/support_cancel?id=1&type=1", HttpMethod.GET, request, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testUnSupportCancel(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("cookie",token);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> entity = restTemplate.exchange("/unsupport_cancel?id=1&type=1", HttpMethod.GET, request, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    }


}