<template>
	<div class="system-dept-dialog-container">
		<el-dialog :title="state.dialog.title" v-model="state.dialog.isShowDialog" width="769px">
			<el-form ref="deptDialogFormRef" :model="state.ruleForm" size="default" label-width="90px" :rules="rules">
				<el-row :gutter="35">
					<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
						<el-form-item label="上级部门" prop="parentDept">
							<el-tree-select
								v-model="state.ruleForm.parentDept"
								:data="state.deptData"
								placeholder="无上级部门"
								class="w100"
								check-strictly
								:render-after-expand="false"
								show-checkbox
								:disabled="state.dialog.disableEditParentDept"
								:clearable="true"
							>
								<template #default="{ node, data }">
									<span>{{ data.deptName }}</span>
									<span v-if="!node.isLeaf"> ({{ data.children.length }}) </span>
								</template>
							</el-tree-select>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="部门名称" prop="deptName">
							<el-input v-model="state.ruleForm.deptName" placeholder="请输入部门名称" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="负责人" prop="leader">
							<el-input v-model="state.ruleForm.leader" placeholder="请输入负责人" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="手机号" prop="phone">
							<el-input v-model="state.ruleForm.phone" placeholder="请输入手机号" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="邮箱" prop="email">
							<el-input v-model="state.ruleForm.email" placeholder="请输入" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="排序">
							<el-input-number v-model="state.ruleForm.sort" :min="0" :max="999" controls-position="right" placeholder="请输入排序" class="w100" />
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="部门状态">
							<el-switch v-model="state.ruleForm.status" inline-prompt active-text="启" inactive-text="禁"></el-switch>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
						<el-form-item label="部门描述">
							<el-input v-model="state.ruleForm.remark" type="textarea" placeholder="请输入部门描述" maxlength="150"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="onCancel" size="default">取 消</el-button>
					<el-button type="primary" @click="onSubmit(deptDialogFormRef)" size="default">{{ state.dialog.submitTxt }}</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script setup lang="ts" name="systemDeptDialog">
import { reactive, ref, nextTick } from 'vue';
import { deptIndoApi } from '/@/api/dept/index';
import { ElMessage, FormInstance } from 'element-plus';

// 定义子组件向父组件传值/事件
const emit = defineEmits(['refresh']);

// 定义变量内容
const deptDialogFormRef = ref();
const state = reactive({
	ruleForm: {
		parentDept: '', // 上级部门
		deptName: '', // 部门名称
		leader: '', // 负责人
		phone: '', // 手机号
		email: '', // 邮箱
		sort: 0, // 排序
		status: true, // 部门状态
		remark: '', // 部门描述
	},
	deptData: [] as DeptCascaderTreeType[], // 部门数据
	dialog: {
		isShowDialog: false,
		type: '',
		title: '',
		submitTxt: '',
		disableEditParentDept: false, // 是否禁用上级部门编辑
	},
});
// 表单验证规则
const rules = {
	deptName: [{ required: true, message: '部门名称不能为空', trigger: 'blur' }],
	leader: [{ required: true, message: '负责人不能为空', trigger: 'blur' }],
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
};
// 重置表单
const initRuleForm = (row?: RowDeptType): typeof state.ruleForm => ({
	parentDept: row?.id?.toString() || '',
	deptName: '',
	leader: '',
	phone: '',
	email: '',
	sort: 0,
	status: true,
	remark: '',
});
// 打开弹窗
const openDialog = async (type: string, row?: RowDeptType) => {
	await getMenuData();

	const isEdit = type === 'edit';

	state.dialog = {
		isShowDialog: true,
		type,
		title: isEdit ? '修改部门' : '新增部门',
		submitTxt: isEdit ? '修 改' : '新 增',
		disableEditParentDept: !!row,
	};

	if (isEdit && row) {
		state.ruleForm = {
			...row,
			parentDept: row.parentId.toString(),
		};
	} else {
		nextTick(() => {
			deptDialogFormRef.value?.resetFields();
			state.ruleForm = initRuleForm(row);
		});
	}
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

	try {
		const valid = await formEl.validate(); // 不传回调，直接返回 Promise<boolean>
		if (!valid) {
			ElMessage.error('表单验证失败，请检查输入');
			return;
		}
		// 表单验证成功
		closeDialog();
		let { parentDept, ...restRuleForm } = state.ruleForm;
		let sendData = {
			...restRuleForm,
			parentId: parentDept,
		};
		if (state.dialog.title === '新增部门') {
			const res = await deptIndoApi.createDept(sendData);
			if (res.code === 200) {
				ElMessage.success(`新增部门${state.ruleForm.deptName}成功!`);
			} else {
				ElMessage.error(res.msg);
			}
		} else {
			const res = await deptIndoApi.editDept(sendData);
			if (res.code === 200) {
				ElMessage.success(`修改部门${state.ruleForm.deptName}成功!`);
			} else {
				ElMessage.error(res.msg);
			}
		}
		emit('refresh');
	} catch (err) {
		ElMessage.error('表单验证失败，请检查输入');
	}
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
const getMenuData = async () => {
	await deptIndoApi.getDeptTree().then((res) => {
		let allTreeDepts = res.data;
		state.deptData = filterDeptTreeData(allTreeDepts);
	});
	// state.deptData.push({
	// 	label: '无上级部门',
	// 	value: '0',
	// });
};

// 暴露变量
defineExpose({
	openDialog,
});
</script>
