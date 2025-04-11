package cn.zpoet.springbootadmin.service.impl;

import cn.zpoet.springbootadmin.model.UserInfoModel;
import cn.zpoet.springbootadmin.repository.UserRepository;
import cn.zpoet.springbootadmin.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserInfoModel> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserInfoModel getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserInfoModel createUser(UserInfoModel user) {
        return userRepository.save(user);
    }

    @Override
    public UserInfoModel updateUser(Long id, UserInfoModel user) {
        user.setId(id); // 保证是更新而不是新增
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
