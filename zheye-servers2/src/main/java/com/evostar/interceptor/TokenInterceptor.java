package com.evostar.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.evostar.annotation.AvoidToken;
import com.evostar.dao.UserDAO;
import com.evostar.exception.UnauthorizedException;
import com.evostar.model.HostHolder;
import com.evostar.model.MsgCodeEnum;
import com.evostar.model.User;
import com.evostar.utils.EhcacheUtils;
import com.evostar.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

//拦截器，验证用户cookie-token是否有效

@Component
public class TokenInterceptor implements HandlerInterceptor {
    private final String TOKEN_PRE = "UserId_";
    private final String cacheName = "token";
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EhcacheUtils ehcacheUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object handler) {
        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if(token != null){
            User user = new User();
            Map<String, Claim> claim = JwtUtils.verifyJWTToken(token);
            int userId = claim.get("userId").asInt();
            if(ehcacheUtils.get(cacheName, TOKEN_PRE+userId) == null){
                //缓存中无此人数据，去mysql查询
                user = userDAO.selectById(userId);
                ehcacheUtils.put(cacheName, TOKEN_PRE+userId, user);
            }else{
                user = (User) ehcacheUtils.get(cacheName, TOKEN_PRE+userId);
            }
            hostHolder.setUser(user);
            return true;
        }else{
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AvoidToken avoidToken = handlerMethod.getMethod().getAnnotation(AvoidToken.class);
            if(avoidToken != null && avoidToken.value().equals("avoid")){
                return true;
            }
            throw new UnauthorizedException();
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostHolder.clear();
    }
}
