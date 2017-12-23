package com.xmg.springboot.website.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.springboot.p2p.base.service.IEmailVerifyService;
import com.xmg.springboot.p2p.util.AjaxResult;
import com.xmg.springboot.website.util.UserContext;

/**
 * 邮箱验证控制器
 * @author 1
 *
 */
@Controller
public class EmailVerifyController {
	@Autowired
	private IEmailVerifyService emailVerifyService;

	@RequestMapping("sendEmail")
	@ResponseBody
	public AjaxResult sendVerifyCode(String email, HttpSession session) {
		AjaxResult json = new AjaxResult();
		emailVerifyService.sendVerifyEmail(email, UserContext.getCurrent()
				.getId());
		return json;
	}
}
