package com.yuanhang.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/***
 * https://www.cnblogs.com/lenve/p/10748453.html
 * @author 肖远航
 * @ControllerAdvice ，很多初学者可能都没有听说过这个注解，实际上，这是一个非常有用的注解，顾名思义，
 * 这是一个增强的 Controller。使用这个 Controller ，可以实现三个方面的功能：
 * 全局异常处理
 * 全局数据绑定
 * 全局数据预处理
 *  @RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)；
 *  GET方式无请求体，所以使用@RequestBody接收数据时，前端不能使用GET方式提交数据，
 *  而是用POST方式进行提交。在后端的同一个接收方法里，@RequestBody与@RequestParam()可以同时使用，
 *  RequestBody最多只能有一个，而@RequestParam()可以有多个。
 *  HttpStatus.INTERNAL_SERVER_ERROR服务器错误
 *  @ResponseBody的作用其实是将java对象转为json格式的数据。
 *
 * @ResponseBody注解的作用是将controller的方法返回的对象
 * 通过适当的转换器转换为指定的格式之后，写入到response对象的body区，通常用来返回JSON数据或者是XML数据。
 */
@ControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    /***
     * 抛出500错误
     */
    public HashMap<String, String> errorHandler(Exception e){
        HashMap<String,String> result = new HashMap<>();
        result.put("msg",e.getMessage());
        return result;
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    /***
     * 抛出500错误
     */
    public HashMap<String, String> errorHandler(ServiceException e){
        HashMap<String,String> result = new HashMap<>();
        result.put("msg",e.getMessage());
        result.put("code",e.getCode());
        return result;
    }
}
