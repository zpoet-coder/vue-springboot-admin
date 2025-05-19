package cn.zpoet.springbootadmin.service;

import cn.zpoet.springbootadmin.model.LoginRequest;
import cn.zpoet.springbootadmin.utils.Result;

public interface AuthService {
    Result<?> login(LoginRequest loginRequest);
}
