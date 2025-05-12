package cn.zpoet.springbootadmin.repository;

import cn.zpoet.springbootadmin.model.DeptInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeptRepository extends JpaRepository<DeptInfoModel, Long> {
    // 可以根据需要加自定义方法
    Optional<DeptInfoModel> findByDeptName(String deptName);

    List<DeptInfoModel> findByParentId(Long parentId);
}
