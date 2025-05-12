package cn.zpoet.springbootadmin.service.impl;

import cn.zpoet.springbootadmin.model.DeptInfoModel;
import cn.zpoet.springbootadmin.repository.DeptRepository;
import cn.zpoet.springbootadmin.service.DeptService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DeptServiceImpl implements DeptService {

    private final DeptRepository deptRepository;

    public DeptServiceImpl(DeptRepository deptRepository) {
        this.deptRepository = deptRepository;
    }

    @Override
    public List<DeptInfoModel> getAllDepts() {
        return deptRepository.findAll();
    }

    @Override
    public DeptInfoModel getDeptById(Long id) {
        return deptRepository.findById(id).orElse(null);
    }

    @Override
    public DeptInfoModel getDeptByDeptName(String deptName) {
        return deptRepository.findByDeptName(deptName).orElse(null);
    }

    @Override
    public DeptInfoModel fillChildren(DeptInfoModel dept) {
        List<DeptInfoModel> children = deptRepository.findByParentId(dept.getId());
        for (DeptInfoModel child : children) {
            fillChildren(child);
        }
        dept.setChildren(children);
        return dept;
    }

    @Override
    public DeptInfoModel createDept(DeptInfoModel dept) {
        return deptRepository.save(dept);
    }

    @Override
    public DeptInfoModel updateDept(Long id, DeptInfoModel dept) {
        dept.setId(id);
        return deptRepository.save(dept);
    }

    @Override
    public void deleteDept(Long id) {
        List<DeptInfoModel> children = deptRepository.findByParentId(id);
        for (DeptInfoModel child : children) {
            // 递归删除每一个子部门
            deleteDept(child.getId());
        }
        // 删除当前部门
        deptRepository.deleteById(id);
    }

    @Override
    public List<DeptInfoModel> getDeptTree() {
        List<DeptInfoModel> allDepts = deptRepository.findAll();
        return buildDeptTree(allDepts, 0L); // 从 parentId = 0 开始构建顶层节点
    }

    private List<DeptInfoModel> buildDeptTree(List<DeptInfoModel> allDepts, Long parentId) {
        List<DeptInfoModel> deptTree = new ArrayList<>();
        for (DeptInfoModel dept : allDepts) {
            if (Objects.equals(dept.getParentId(), parentId)) {
                dept.setChildren(buildDeptTree(allDepts, dept.getId()));
                deptTree.add(dept);
            }
        }
        return deptTree;
    }
}
