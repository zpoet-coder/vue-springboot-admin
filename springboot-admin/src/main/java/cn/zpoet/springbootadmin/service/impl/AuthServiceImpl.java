package cn.zpoet.springbootadmin.service.impl;

import cn.zpoet.springbootadmin.model.LoginRequest;
import cn.zpoet.springbootadmin.repository.UserRepository;
import cn.zpoet.springbootadmin.service.AuthService;
import cn.zpoet.springbootadmin.utils.JwtUtil;
import cn.zpoet.springbootadmin.utils.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public Result<?> login(LoginRequest loginRequest) {
        return userRepository.findByAccountName(loginRequest.getUsername())
                .map(user -> {
                    if (user.getPassword().equals(loginRequest.getPassword())) {
                        String token = JwtUtil.generateToken(user.getAccountName());
                        return Result.success(token);
                    } else {
                        return Result.fail("Invalid username or password");
                    }
                })
                .orElse(Result.fail("User not found"));
    }
}
