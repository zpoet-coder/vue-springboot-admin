import request from '/@/utils/request';

export const userInfoApi = {
	// 获取所有用户信息
	queryAllUsers: () => {
		return request({
			url: '/users',
			method: 'get',
		});
	},
	// 根据用户名称获取用户信息
	querryUserByUserName: (userName: string) => {
		return request({
			url: `/users/by-userName`,
			method: 'get',
			params: { userName: userName },
		});
	},
	// 新增用户
	createUser: (data: object) => {
		return request({
			url: '/users',
			method: 'post',
			data,
		});
	},
	// 修改用户
	editUser: (data: object) => {
		return request({
			url: `/users/by-id`,
			method: 'put',
			data,
		});
	},
	// 删除用户
	deleteUser: (id: number) => {
		return request({
			url: `/users/by-id`,
			method: 'delete',
			params: { id: id },
		});
	},
};
