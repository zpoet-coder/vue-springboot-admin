package cn.zpoet.springbootadmin.controller;

import cn.zpoet.springbootadmin.model.DeptInfoModel;
import cn.zpoet.springbootadmin.service.DeptService;
import cn.zpoet.springbootadmin.utils.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/depts")
public class DeptController {

    private final DeptService deptService;

    public DeptController(DeptService deptService) {
        this.deptService = deptService;
    }

    @GetMapping
    public Result<List<DeptInfoModel>> getAllDepts() {
        List<DeptInfoModel> depts = deptService.getAllDepts();
        return Result.success(depts);
    }

    @GetMapping("/by-id")
    public Result<DeptInfoModel> getDeptById(@RequestParam Long id) {
        DeptInfoModel dept = deptService.getDeptById(id);
        return Result.success(dept);
    }

    @GetMapping("/by-deptName")
    public Result<DeptInfoModel> getDeptByDeptName(@RequestParam String deptName) {
        DeptInfoModel dept = deptService.getDeptByDeptName(deptName);
        if (dept != null) {
            deptService.fillChildren(dept);
        }
        return Result.success(dept);
    }

    @PostMapping
    public Result<DeptInfoModel> createDept(@RequestBody DeptInfoModel dept) {
        DeptInfoModel createdDept = deptService.createDept(dept);
        return Result.success(createdDept);
    }

    @PutMapping("/by-id")
    public Result<DeptInfoModel> updateDept(@RequestBody DeptInfoModel dept) {
        DeptInfoModel updatedDept = deptService.updateDept(dept.getId(), dept);
        return Result.success(updatedDept);
    }

    @DeleteMapping("/by-id")
    public Result<Void> deleteDept(@RequestParam Long id) {
        deptService.deleteDept(id);
        return Result.success();
    }

    @GetMapping("/dept-tree")
    public Result<List<DeptInfoModel>> getDeptTree() {
        List<DeptInfoModel> depts = deptService.getDeptTree();
        return Result.success(depts);
    }
}
