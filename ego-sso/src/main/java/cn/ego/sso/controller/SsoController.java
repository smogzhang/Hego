package cn.ego.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ego.bean.EgoResult;
import cn.ego.pojo.TbUser;
import cn.ego.sso.service.SsoService;

/**
 * 单点登录控制器
 * 这里主要写以下几个接口
 * 1.CheckParam
 * 2.Register
 * 3.Login
 * 4.CheckToken
 * 5.Logout
 * @author Sully
 *
 */
@Controller
@RequestMapping("/sso")
public class SsoController {

	@Autowired
	private SsoService ssoService;
	
	/**
	 * 格式如：zhangsan/1
	 * @param param 校验的数据
	 * @param type 可选参数，代表类型1、2、3分别代表username、phone、email
	 * @return
	 */
	@RequestMapping(value="/check/{param}/{type}", method=RequestMethod.GET)
	@ResponseBody
	public Object CheckParam(@PathVariable("param") String param, @PathVariable("type") int type, String callback) {
		EgoResult result = ssoService.checkParam(param, type);
		if("".equals(callback) || callback == null) {
			return result;
		} 
		MappingJacksonValue jacksonValue = new MappingJacksonValue(result);
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue;
	}
	
	/**
	 * {
	 * status: 400
	 * msg: "注册失败. 请校验数据后请再提交数据."
	 * data: null
	 * }
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/register", method=RequestMethod.POST)
	@ResponseBody
	public EgoResult register(TbUser user) {
		try {
			ssoService.saveUser(user);
			return EgoResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EgoResult.build(400, "注册失败");
	}
	
	/**
	 * 用户登录，返回参数：json，格式如下
	 * {
	 * status: 200
	 * msg: "OK"
	 * data: "fe5cb546aeb3ce1bf37abcb08a40493e" //登录成功，返回token 写入客户端
	 * }
	 * @param username
	 * @param password
	 * @return EgoResult
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public EgoResult login(String username, String password) {
		return ssoService.getUserByLogin(username, password);
	}
	
	/**
	 * 根据token查询用户是否过期，返回参数如下
	 * {
	 * status: 200
	 * msg: "OK"
	 * data: "{"id":1,"username":"zhangjun","phone":"15800807944","email":"420840806@qq.com","created":1414119176000,"updated":1414119179000}"
	 * }
	 * @param token
	 * @return
	 */
	@RequestMapping("/token/{token}")
	@ResponseBody
	public EgoResult checkToken(@PathVariable String token) {
		return ssoService.getUserInfoByToken(token);
	}
	
	@RequestMapping("/logout")
	@ResponseBody
	public EgoResult logout() {
		return null;
	}
	
}