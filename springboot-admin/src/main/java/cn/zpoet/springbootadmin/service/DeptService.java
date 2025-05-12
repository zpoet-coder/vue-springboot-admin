package cn.zpoet.springbootadmin.service;

import cn.zpoet.springbootadmin.model.DeptInfoModel;

import java.util.List;

public interface DeptService {
    List<DeptInfoModel> getAllDepts();

    DeptInfoModel getDeptById(Long id);

    DeptInfoModel getDeptByDeptName(String deptName);

    void fillChildren(DeptInfoModel dept);

    DeptInfoModel createDept(DeptInfoModel dept);

    DeptInfoModel updateDept(Long id, DeptInfoModel dept);

    void deleteDept(Long id);

    List<DeptInfoModel> getDeptTree();
}