package cn.zpoet.springbootadmin.repository;

import cn.zpoet.springbootadmin.model.UserInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfoModel, Long> {
    // 可扩展自定义查询，如：
    UserInfoModel findByUserName(String userName);
}
