<template>
	<div class="system-user-dialog-container">
		<el-dialog :title="state.dialog.title" v-model="state.dialog.isShowDialog" width="769px">
			<el-form ref="userDialogFormRef" :model="state.ruleForm" size="default" :rules="rules" label-width="90px">
				<el-row :gutter="35">
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="账户名称" prop="userName">
							<el-input v-model="state.ruleForm.userName" placeholder="请输入用户名称" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="账户名称" prop="accountName">
							<el-input v-model="state.ruleForm.accountName" placeholder="请输入账户名称" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="关联角色" prop="roles">
							<el-select v-model="state.ruleForm.roles" placeholder="请选择关联角色" multiple clearable class="w100">
								<el-option label="超级管理员" value="admin"></el-option>
								<el-option label="普通用户" value="common"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="部门" prop="department">
							<el-tree-select
								v-model="state.ruleForm.department"
								:data="state.deptData"
								check-strictly
								:render-after-expand="false"
								show-checkbox
								placeholder="请选择部门"
							/>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="手机号" prop="phone">
							<el-input v-model="state.ruleForm.phone" placeholder="请输入手机号" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="邮箱" prop="email">
							<el-input v-model="state.ruleForm.email" placeholder="请输入邮箱" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="性别" prop="gender">
							<el-select v-model="state.ruleForm.gender" placeholder="请选择性别" clearable class="w100">
								<el-option label="男" value="男"></el-option>
								<el-option label="女" value="女"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="账户密码" prop="password">
							<el-input v-model="state.ruleForm.password" placeholder="请输入" type="password" clearable show-password></el-input>
						</el-form-item>
					</el-col>
					<!-- <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="账户过期">
							<el-date-picker v-model="state.ruleForm.overdueTime" type="date" placeholder="请选择" class="w100"> </el-date-picker>
						</el-form-item>
					</el-col> -->
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="用户状态">
							<el-switch v-model="state.ruleForm.status" inline-prompt active-text="启" inactive-text="禁"></el-switch>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
						<el-form-item label="用户描述">
							<el-input v-model="state.ruleForm.describe" type="textarea" placeholder="请输入用户描述" maxlength="150"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="onCancel" size="default">取 消</el-button>
					<el-button type="primary" @click="onSubmit(userDialogFormRef)" size="default">{{ state.dialog.submitTxt }}</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script setup lang="ts" name="systemUserDialog">
import { reactive, ref, nextTick } from 'vue';
import { userInfoApi } from '/@/api/user/index';
import { cloneDeep } from 'lodash-es';
import { ElMessage, type FormInstance } from 'element-plus';
import { deptIndoApi } from '/@/api/dept';

// 定义子组件向父组件传值/事件
const emit = defineEmits(['refresh']);

// 定义变量内容
const userDialogFormRef = ref();
const state = reactive({
	ruleForm: {
		userName: '', // 用户名称
		accountName: '', // 账户名称
		roles: [] as string[], // 关联角色
		department: '', // 部门
		phone: '', // 手机号
		email: '', // 邮箱
		gender: '', // 性别
		password: '', // 账户密码
		overdueTime: '', // 账户过期
		status: true, // 用户状态
		describe: '', // 用户描述
	},
	deptData: [] as DeptTreeType[], // 部门数据
	dialog: {
		isShowDialog: false,
		type: '',
		title: '',
		submitTxt: '',
	},
});

const rules = {
	userName: [{ required: true, message: '用户名称不能为空', trigger: 'blur' }],
	accountName: [{ required: true, message: '账户名称不能为空', trigger: 'blur' }],
	roles: [{ required: true, message: '关联角色不能为空', trigger: 'blur' }],
	department: [{ required: true, message: '部门不能为空', trigger: 'blur' }],
	phone: [
		{ required: true, message: '手机号不能为空', trigger: 'blur' },
		{
			pattern: /^1[3-9]\d{9}$/,
			message: '请输入有效的手机号',
			trigger: 'blur',
		},
	],
	email: [
		{ required: true, message: '邮箱不能为空', trigger: 'blur' },
		{
			type: 'email',
			message: '请输入有效的邮箱地址',
			trigger: ['blur', 'change'],
		},
	],
	gender: [{ required: true, message: '性别不能为空', trigger: 'blur' }],
	password: [
		{ required: true, message: '密码不能为空', trigger: 'blur' },
		{
			pattern: /^(?=.*[A-Za-z])(?=.*\d).{6,}$/,
			message: '密码必须大于6位，且包含字母和数字',
			trigger: 'blur',
		},
	],
};

// 打开弹窗
const openDialog = (type: string, row: RowUserType) => {
	getMenuData();
	if (type === 'edit') {
		state.ruleForm = cloneDeep(row);
		state.dialog.title = '修改用户';
		state.dialog.submitTxt = '修 改';
	} else {
		state.dialog.title = '新增用户';
		state.dialog.submitTxt = '新 增';
		// 清空表单
		nextTick(() => {
			userDialogFormRef.value?.resetFields();
			state.ruleForm = {
				userName: '', // 用户名称
				accountName: '', // 账户名称
				roles: [] as string[], // 关联角色
				department: '', // 部门
				phone: '', // 手机号
				email: '', // 邮箱
				gender: '', // 性别
				password: '', // 账户密码
				overdueTime: '', // 账户过期
				status: true, // 用户状态
				describe: '', // 用户描述
			};
		});
	}
	state.dialog.isShowDialog = true;
};
// 关闭弹窗
const closeDialog = () => {
	state.dialog.isShowDialog = false;
};
// 取消
const onCancel = () => {
	closeDialog();
};
// 提交
const onSubmit = async (formEl: FormInstance | undefined) => {
	if (!formEl) return;
	await formEl.validate(async (valid: boolean) => {
		if (!valid) {
			ElMessage.error('表单验证失败，请检查输入');
			return false;
		}
		// 表单验证成功
		else {
			closeDialog();
			if (state.dialog.title === '新增用户') {
				await userInfoApi.createUser(state.ruleForm).then((res) => {
					if (res.code === 200) {
						ElMessage.success(`新增用户${state.ruleForm.userName}成功!`);
					} else {
						ElMessage.error(res.msg);
					}
				});
			} else {
				await userInfoApi.editUser(state.ruleForm).then((res) => {
					if (res.code === 200) {
						ElMessage.success(`修改用户${state.ruleForm.userName}成功!`);
					} else {
						ElMessage.error(res.msg);
					}
				});
			}
			emit('refresh');
		}
	});
};

const filterDeptTreeData = (nodeList: any) => {
	let result = nodeList.map((item: any) => {
		return {
			...item,
			label: item.deptName,
			value: item.id.toString(),
			children: item.children.length > 0 ? filterDeptTreeData(item.children) : [],
		};
	});
	return result;
};
// 初始化部门数据
const getMenuData = () => {
	deptIndoApi.getDeptTree().then((res) => {
		let allTreeDepts = res.data;
		state.deptData = filterDeptTreeData(allTreeDepts);
	});
};
// 暴露变量
defineExpose({
	openDialog,
});
</script>
