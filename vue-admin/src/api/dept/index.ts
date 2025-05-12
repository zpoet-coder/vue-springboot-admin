import request from '/@/utils/request';

export const deptIndoApi = {
	// 获取所有部门信息
	queryAllDepts: () => {
		return request({
			url: '/depts',
			method: 'get',
		});
	},
	// 根据 id 获取部门信息
	queryDeptById: (id: Number) => {
		return request({
			url: '/depts/by-id',
			method: 'get',
			params: { id: id },
		});
	},
	// 根据部门名称获取部门信息
	queryDeptByDeptName: (deptName: string) => {
		return request({
			url: '/depts/by-deptName',
			method: 'get',
			params: { deptName: deptName },
		});
	},
	// 新增部门
	createDept: (data: object) => {
		return request({
			url: '/depts',
			method: 'post',
			data,
		});
	},
	// 修改部门
	editDept: (data: object) => {
		return request({
			url: `/depts/by-id`,
			method: 'put',
			data,
		});
	},
	// 删除部门
	deleteDept: (id: number) => {
		return request({
			url: `/depts/by-id`,
			method: 'delete',
			params: { id: id },
		});
	},
	// 获取部门树形结构
	getDeptTree: () => {
		return request({
			url: '/depts/dept-tree',
			method: 'get',
		});
	},
};
