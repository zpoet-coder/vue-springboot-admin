package cn.zpoet.springbootadmin.controller;

import cn.zpoet.springbootadmin.model.UserInfoModel;
import cn.zpoet.springbootadmin.service.UserService;
import cn.zpoet.springbootadmin.utils.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Result<List<UserInfoModel>> getAllUsers() {
        List<UserInfoModel> users = userService.getAllUsers();
        return Result.success(users);
    }

    @GetMapping("/by-id")
    public Result<UserInfoModel> getUserById(@RequestParam Long id) {
        UserInfoModel userInfo = userService.getUserById(id);
        return Result.success(userInfo);
    }

    @GetMapping("/by-userName")
    public Result<UserInfoModel> getUserByUserName(@RequestParam String userName) {
        UserInfoModel userInfo = userService.getUserByUserName(userName);
        return Result.success(userInfo);
    }

    @PostMapping
    public Result<UserInfoModel> createUser(@RequestBody UserInfoModel user) {
        UserInfoModel userInfo = userService.createUser(user);
        return Result.success(userInfo);
    }

    @PutMapping("/by-id")
    public Result<UserInfoModel> updateUser(@RequestBody UserInfoModel user) {
        UserInfoModel userInfo = userService.updateUser(user.getId(), user);
        return Result.success(userInfo);
    }

    @DeleteMapping("/by-id")
    public Result<Void> deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return Result.success();
    }
}
