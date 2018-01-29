package cn.ego.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import cn.ego.bean.EgoResult;
import cn.ego.jedis.JedisClient;
import cn.ego.mapper.TbUserMapper;
import cn.ego.pojo.TbUser;
import cn.ego.pojo.TbUserExample;
import cn.ego.pojo.TbUserExample.Criteria;
import cn.ego.sso.service.SsoService;
import cn.ego.utils.JsonUtils;

@Service
public class SsoServiceImpl implements SsoService {

	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisCluster;
	//标识用户信息 用于redis 生成key
	@Value("${REDIS_USER_SESSION_KEY}")
	private String REDIS_USER_SESSION_KEY;
	@Value("${REDIS_USER_SESSION_KEY_EXPIRE}")
	//设置用户信息在redis中的过期时间
	private int REDIS_USER_SESSION_KEY_EXPIRE;
	
	@Override
	public EgoResult checkParam(String param, int type) {
		
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		if(1 == type) {
			String username = param;
			// 判断用户名是否已存在
			criteria.andUsernameEqualTo(username);
			List<TbUser> users = userMapper.selectByExample(example);
			if(users != null && users.size() > 0) {
				return EgoResult.build(200, "该用户名已存在", false);
			}
		} else if(2 == type) {
			String phone = param;
			// 判断电话号码是否可用
			criteria.andPhoneEqualTo(phone);
			List<TbUser> users = userMapper.selectByExample(example);
			if(users != null && users.size() > 0) {
				return EgoResult.build(200, "该手机号码已绑定用户", false);
			}
		} else if(3 == type) {
			return EgoResult.ok(true);
		}
		return EgoResult.build(200, "请输入正确类型", false);
	}

	@Override
	public void saveUser(TbUser user) throws Exception {
		Date date = new Date();
		// 密码MD5加密后存放数据库
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		user.setCreated(date);
		user.setUpdated(date);
		userMapper.insert(user);
	}

	@Override
	public EgoResult getUserByLogin(String username, String password) {
		//先判断是否存在该用户名
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> users = userMapper.selectByExample(example);
		if(users == null || users.size() < 1)
			return EgoResult.build(400, "该用户没有注册！", null);
		//存在用户名，则比较密码
		criteria.andPasswordEqualTo(DigestUtils.md5DigestAsHex(password.getBytes()));
		users = userMapper.selectByExample(example);
		if(users == null || users.size() < 1)
			return EgoResult.build(400, "用户名或密码错误！", null);
		// 存在 , 将用户信息写入到redis中，并且设置Cookie
		TbUser user = users.get(0);
		user.setPassword(null);
		// 生成Token
		String token = UUID.randomUUID().toString();
		jedisCluster.set(REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(user));
		jedisCluster.expire(REDIS_USER_SESSION_KEY + ":" + token, REDIS_USER_SESSION_KEY_EXPIRE);
		// 返回token
		return EgoResult.ok(token);
	}

	@Override
	public EgoResult getUserInfoByToken(String token) {
		String userInfo = jedisCluster.get(REDIS_USER_SESSION_KEY + ":" + token);
		if(StringUtils.isEmpty(userInfo)) {
			return EgoResult.build(400, "时间超时，请重新登录");
		}
		// 更新用户时间
		jedisCluster.expire(REDIS_USER_SESSION_KEY + ":" + token, REDIS_USER_SESSION_KEY_EXPIRE);
		return EgoResult.ok(userInfo);
	}

}
