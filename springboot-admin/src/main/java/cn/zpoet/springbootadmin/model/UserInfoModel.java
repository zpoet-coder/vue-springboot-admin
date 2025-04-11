package cn.zpoet.springbootadmin.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user_info")
public class UserInfoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 用户名称（真实姓名） */
    @Column(nullable = false)
    private String userName;

    /** 用户账号（唯一） */
    @Column(nullable = false, unique = true)
    private String accountName;

    /** 所属部门 */
    @Column(nullable = false)
    private String department;

    /** 关联角色（多个角色） */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<String> roles;

    /** 手机号 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 职务 */
    private String position;

    /** 性别（可选：Male, Female） */
    private String gender;

    /** 用户状态（true=启用，false=禁用） */
    @Column(nullable = false)
    private Boolean status = true;

    /** 用户描述 */
    private String description;

    /** 创建时间 */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createTime = new Date();

    /** 更新时间 */
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime = new Date();

    /** 用户头像地址（可选） */
    private String avatar;

    /** 密码（建议加密存储） */
    private String password;
}
