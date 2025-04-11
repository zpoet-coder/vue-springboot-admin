package cn.zpoet.springbootadmin.service;

import cn.zpoet.springbootadmin.model.UserInfoModel;
import java.util.List;

public interface UserService {
    List<UserInfoModel> getAllUsers();
    UserInfoModel getUserById(Long id);
    UserInfoModel createUser(UserInfoModel user);
    UserInfoModel updateUser(Long id, UserInfoModel user);
    void deleteUser(Long id);
}
