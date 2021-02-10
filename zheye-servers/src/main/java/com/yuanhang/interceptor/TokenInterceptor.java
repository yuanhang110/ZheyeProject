package com.yuanhang.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.yuanhang.dao.UserDAO;
import com.yuanhang.exception.UnauthorizedException;
import com.yuanhang.model.HostHolder;
import com.yuanhang.model.User;
import com.yuanhang.utils.EhcacheUtils;
import com.yuanhang.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) {
        String token = null;
        /***
         * 拦截器找cookie里的token
         */
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        /***
         * 如果没有找到就返回未认证异常，找到了就把userid拿出来
         */
        if (token != null) {
            User user = new User();
            Map<String, Claim> claim = JwtUtils.verifyJWTToken(token);
            int userId = claim.get("userId").asInt();
            if (ehcacheUtils.get(cacheName, TOKEN_PRE + userId) == null) {
                //先到ehcache缓存查，缓存中无此人数据，去mysql查询
                user = userDAO.selectById(userId);
                ehcacheUtils.put(cacheName, TOKEN_PRE + userId, user);
            } else {
                user = (User) ehcacheUtils.get(cacheName, TOKEN_PRE + userId);
            }
            /***
             * 查完后放到hostholder里
             */
            hostHolder.setUser(user);
            return true;
        } else {
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
