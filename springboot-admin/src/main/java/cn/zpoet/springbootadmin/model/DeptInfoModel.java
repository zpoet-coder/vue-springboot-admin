package cn.zpoet.springbootadmin.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "dept_info")
@Setter
@Getter
public class DeptInfoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 上级部门 ID
     */
    @Column(name = "parent_id", nullable = false)
    private Long parentId = 0L;

    /**
     * 部门名称
     */
    @Column(name = "dept_name", nullable = false, length = 50)
    private String deptName;

    /**
     * 负责人
     */
    @Column(length = 50)
    private String leader;

    /**
     * 手机号
     */
    @Column(length = 20)
    private String phone;

    /**
     * 邮箱
     */
    @Column(length = 100)
    private String email;

    /**
     * 排序
     */
    @Column(name = "sort", nullable = false)
    private Integer sort = 0;

    /**
     * 部门状态：0-启用，1-禁用
     */
    @Column(nullable = false)
    private boolean status = true;

    /**
     * 备注
     */
    @Column(columnDefinition = "TEXT")
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime = LocalDateTime.now();

    @Transient
    private List<DeptInfoModel> children = new ArrayList<>();
}
