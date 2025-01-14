package com.example.auth.rest;

import com.example.auth.service.UserService;
import com.example.openapi.api.UserApi;
import com.example.openapi.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService service;

    @Override
    public ResponseEntity<UserInfo> getUserInfo() {
        return ResponseEntity.ok(service.getUserInfo());
    }
}
