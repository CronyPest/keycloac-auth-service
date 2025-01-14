package com.example.auth.service;

import com.example.openapi.model.UserInfo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    //todo:  сконфигурировать spring security так, чтобы в контексте уже лежал распарсенный Jwt
    public UserInfo getUserInfo() {
        Jwt token = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> claims = token.getClaims();

        return UserInfo.builder()
                .userId(claims.get("sub").toString())
                .domainUsername(claims.get("preferred_username").toString())
                .fullName(claims.get("name").toString())
                .build();
    }
}
