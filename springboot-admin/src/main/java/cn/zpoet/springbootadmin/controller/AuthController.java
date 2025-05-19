package cn.zpoet.springbootadmin.controller;

import cn.zpoet.springbootadmin.model.LoginRequest;
import cn.zpoet.springbootadmin.service.AuthService;
import cn.zpoet.springbootadmin.utils.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
