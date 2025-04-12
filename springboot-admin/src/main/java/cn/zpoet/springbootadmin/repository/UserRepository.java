package cn.zpoet.springbootadmin.repository;

import cn.zpoet.springbootadmin.model.UserInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfoModel, Long> {
    // 可扩展自定义查询，如：
    Optional<UserInfoModel> findByUserName(String userName);
}
