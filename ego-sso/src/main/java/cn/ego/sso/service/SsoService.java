package cn.ego.sso.service;

import cn.ego.bean.EgoResult;
import cn.ego.pojo.TbUser;

public interface SsoService {

	/**
	 * {status: 200 //200 成功
	 * msg: "OK" // 返回信息消息 
	 * data: false // 返回数据，true：数据可用，false：数据不可用}
	 * @param param
	 * @param type
	 * @return
	 */
	EgoResult checkParam(String param, int type);
	
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	void saveUser(TbUser user) throws Exception;

	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	EgoResult getUserByLogin(String username, String password);

	/**
	 * 根据cookie 中 token 获取用户信息，判断用户是否登录
	 * @param token
	 * @return
	 */
	EgoResult getUserInfoByToken(String token);

}
