import request from '/@/utils/request';

/**
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 *
 * 登录api接口集合
 * @method login 用户登录
 * @method loginout 用户退出登录
 */
export const useLoginApi = {
	login: (data: object) => {
		return request({
			url: '/auth/login',
			method: 'post',
			data,
		});
	},
	loginout: (data: object) => {
		return request({
			url: '/user/signOut',
			method: 'post',
			data,
		});
	},
};
