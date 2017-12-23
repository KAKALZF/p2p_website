package com.xmg.springboot.website.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.springboot.p2p.base.service.IVerifyCodeService;
import com.xmg.springboot.p2p.base.vo.VerifyCodeVO;
import com.xmg.springboot.p2p.util.AjaxResult;
import com.xmg.springboot.website.util.UserContext;

/**
 * 发送验证码控制器
 * @author 1
 *
 */
@Controller
public class SendVerifyCodeController {
	@Autowired
	private IVerifyCodeService verifyCodeService;

	@RequestMapping("sendVerifyCode")
	@ResponseBody
	public AjaxResult sendVerifyCode(String phoneNumber, HttpSession session) {
		AjaxResult json = new AjaxResult();
		VerifyCodeVO verifyCodeVO = UserContext.getVerifyCodeVO();
		verifyCodeVO = verifyCodeService.sendVerifCode(phoneNumber,
				verifyCodeVO);
		if (verifyCodeVO == null) {
			json.setMsg("验证码发送失败");
		} else {
			UserContext.setVerifyCodeVO(verifyCodeVO);
		}
		return json;
	}
}
