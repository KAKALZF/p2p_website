package com.xmg.springboot.website.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.springboot.p2p.base.domain.Logininfo;
import com.xmg.springboot.p2p.base.service.ILogininfoService;
import com.xmg.springboot.p2p.util.AjaxResult;
import com.xmg.springboot.website.util.UserContext;

@Controller
public class RegisterCotroller {

	@Autowired
	private ILogininfoService logininfoServiceImpl;

	/**
	 * 注册用户
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("userRegister")
	@ResponseBody
	public AjaxResult register(String username, String password) {
		AjaxResult result = new AjaxResult();
		try {
			logininfoServiceImpl.register(username, password);
		} catch (Exception e) {
			result.setMsg(e.getMessage());
		}
		return result;
	}

	/**
	 * 检查用户名是否存在
	 * @param username
	 * @return
	 */
	@RequestMapping("checkUsername")
	@ResponseBody
	public boolean register(String username) {
		return !logininfoServiceImpl.checkUsernameExist(username);
	}

	/**
	 * 登录控制
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("userLogin")
	@ResponseBody
	public AjaxResult userLogin(String username, String password,
			HttpServletRequest request) {
		AjaxResult result = new AjaxResult();

		Logininfo current = logininfoServiceImpl.login(username, password,
				request.getRemoteAddr(), Logininfo.TYPE_USER);

		if (current == null) {
			result.setMsg("用户名或密码错误");
		} else {
			//将用户保存到session中去
			UserContext.putCurrent(current);
		}
		return result;
	}
}
