package com.evostar.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.*;

public class JwtUtils {
    private static final String secret = "evostar2020";

    /**
     * 生成 JWT Token
     * */
    public static String createJWTToken(Integer userId, Integer expire) throws JWTCreationException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        //jwt 头部信息
        Map<String,Object> map=new HashMap<>();
        map.put("alg","HS256");
        map.put("typ","JWT");

        Date nowDate = new Date();
        Date expireDate = AddDate(nowDate,expire);

        String token= JWT.create()
                .withHeader(map)
                .withIssuer("SERVICE") //对应 paylaod iss 节点：签发人
                .withClaim("userId",userId)
                .withClaim("version","1.0")
                .withIssuedAt(nowDate)//对应 paylaod iat 节点：生效时间
                .withExpiresAt(expireDate) //对应 paylaod  exp 签发人 节点：过期时间
                .sign(algorithm);
        return  token;
    }


    /**
     * 验证 token
     * */
    public static Map<String, Claim> verifyJWTToken(String token) throws JWTVerificationException {
        Algorithm algorithm=Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("SERVICE")
                .build();

        DecodedJWT jwt =verifier.verify(token);
        String subject=jwt.getSubject();
        Map<String, Claim> claims=jwt.getClaims();
        return claims;
        //Claim claim = claims.get("loginName");
    }

    /**
     * 时间加减法
     * */
    private static Date AddDate(Date date,Integer minute){
        if(null==date)
            date=new Date();
        Calendar cal=new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minute);
        return cal.getTime();

    }
}
