package com.evostar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@ControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HashMap<String, String> errorHandler(Exception e){
        HashMap<String, String> result = new HashMap<>();
        result.put("msg", e.getMessage());
        return result;
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HashMap<String, String> errorHandler(ServiceException e){
        HashMap<String, String> result = new HashMap<>();
        result.put("msg", e.getMsg());
        result.put("code",String.valueOf(e.getCode()));
        return result;
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public HashMap<String, Object> errorHandler(UnauthorizedException e){
        HashMap<String, Object> result = new HashMap<>();
        result.put("msg", e.getMsg());
        result.put("code",String.valueOf(e.getCode()));
        return result;
    }



}
