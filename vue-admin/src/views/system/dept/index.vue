<template>
	<div class="system-dept-container layout-padding">
		<el-card shadow="hover" class="layout-padding-auto">
			<div class="system-dept-search mb15">
				<el-input size="default" v-model="deptName" placeholder="请输入部门名称" style="max-width: 180px"> </el-input>
				<el-button size="default" type="primary" class="ml10" @click="onQueryDeptByDeptName(deptName)">
					<el-icon>
						<ele-Search />
					</el-icon>
					查询
				</el-button>
				<el-button size="default" type="success" class="ml10" @click="onOpenAddDept('add')">
					<el-icon>
						<ele-FolderAdd />
					</el-icon>
					新增部门
				</el-button>
			</div>
			<el-table
				:data="state.tableData.data"
				v-loading="state.tableData.loading"
				style="width: 100%"
				row-key="id"
				default-expand-all
				:tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
			>
				<el-table-column prop="deptName" label="部门名称" show-overflow-tooltip> </el-table-column>
				<el-table-column label="排序" show-overflow-tooltip width="80">
					<template #default="scope">
						{{ scope.$index }}
					</template>
				</el-table-column>
				<el-table-column prop="status" label="部门状态" show-overflow-tooltip>
					<template #default="scope">
						<el-tag type="success" v-if="scope.row.status">启用</el-tag>
						<el-tag type="info" v-else>禁用</el-tag>
					</template>
				</el-table-column>
				<el-table-column prop="describe" label="部门描述" show-overflow-tooltip></el-table-column>
				<el-table-column prop="createTime" label="创建时间" show-overflow-tooltip></el-table-column>
				<el-table-column label="操作" show-overflow-tooltip width="140">
					<template #default="scope">
						<el-button size="small" text type="primary" @click="onOpenAddDept('add', scope.row)">新增</el-button>
						<el-button size="small" text type="primary" @click="onOpenEditDept('edit', scope.row)">修改</el-button>
						<el-button size="small" text type="primary" @click="onTabelRowDel(scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
		</el-card>
		<DeptDialog ref="deptDialogRef" @refresh="getTableData" />
	</div>
</template>

<script setup lang="ts" name="systemDept">
import { defineAsyncComponent, ref, reactive, onMounted } from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import { deptIndoApi } from '/@/api/dept/index';

// 引入组件
const DeptDialog = defineAsyncComponent(() => import('/@/views/system/dept/dialog.vue'));

// 定义变量内容
const deptDialogRef = ref();
const deptName = ref('');
const state = reactive<SysDeptState>({
	tableData: {
		data: [],
		total: 0,
		loading: false,
		param: {
			pageNum: 1,
			pageSize: 10,
		},
	},
});

// 初始化表格数据
const getTableData = () => {
	state.tableData.loading = true;
	state.tableData.data = [];
	deptIndoApi.getDeptTree().then((res) => {
		if (res.code === 200) {
			state.tableData.data = res.data;
		} else {
			ElMessage.error(res.msg);
		}
	});
	state.tableData.total = state.tableData.data.length;
	setTimeout(() => {
		state.tableData.loading = false;
	}, 500);
};
// 根据部门名称获取部门信息
const onQueryDeptByDeptName = (deptName: string) => {
	state.tableData.loading = true;
	deptIndoApi.queryDeptByDeptName(deptName).then((res) => {
		state.tableData.data = res.data ? [res.data] : [];
		state.tableData.total = state.tableData.data.length;
		state.tableData.loading = false;
	});
};
// 打开新增菜单弹窗
const onOpenAddDept = (type: string, row?: DeptTreeType) => {
	deptDialogRef.value.openDialog(type, row);
};
// 打开编辑菜单弹窗
const onOpenEditDept = (type: string, row: DeptTreeType) => {
	deptDialogRef.value.openDialog(type, row);
};
// 删除当前行
const onTabelRowDel = (row: DeptTreeType) => {
	ElMessageBox.confirm(`此操作将永久删除部门：${row.deptName}, 是否继续?`, '提示', {
		confirmButtonText: '删除',
		cancelButtonText: '取消',
		type: 'warning',
	})
		.then(() => {
			deptIndoApi.deleteDept(row.id).then((res) => {
				if (res.code === 200) {
					ElMessage.success('删除成功');
					getTableData();
					ElMessage.success('删除成功');
				} else {
					ElMessage.error(res.msg);
				}
			});
		})
		.catch(() => {});
};
// 页面加载时
onMounted(() => {
	getTableData();
});
</script>
