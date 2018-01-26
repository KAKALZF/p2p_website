package com.xmg.springboot.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.springboot.p2p.base.service.IAccountService;
import com.xmg.springboot.p2p.base.service.IUserinfoService;
import com.xmg.springboot.p2p.base.vo.VerifyCodeVO;
import com.xmg.springboot.p2p.business.service.IExpAccountService;
import com.xmg.springboot.p2p.util.AjaxResult;
import com.xmg.springboot.website.controller.interceptors.RequireLogin;
import com.xmg.springboot.website.util.UserContext;

@Controller
public class PersonalController {
	@Autowired
	private IAccountService accountServiceImpl;
	@Autowired
	private IUserinfoService userinfoService;
	@Autowired
	private IExpAccountService expAccountService;

	@RequireLogin
	@RequestMapping("personal")
	public String personCenter(Model model) {
		model.addAttribute("account",
				accountServiceImpl.get(UserContext.getCurrent().getId()));
		model.addAttribute("userinfo",
				userinfoService.get(UserContext.getCurrent().getId()));
		System.out.println("============================"+UserContext.getCurrent().toString());
//		model.addAttribute("expAccount",
//				expAccountService.get(UserContext.getCurrent().getId()));
		return "personal";
	}

	/**
	 * 执行绑手机
	 */
	@RequestMapping("bindPhone")
	@ResponseBody
	public AjaxResult bindPhone(String phoneNumber, String verifyCode) {
		AjaxResult json = new AjaxResult();
		//执行校验
		VerifyCodeVO vo = UserContext.getVerifyCodeVO();
		boolean ret = userinfoService.bindPhone(vo, phoneNumber, verifyCode,
				UserContext.getCurrent().getId());
		if (!ret) {
			json.setMsg("绑定失败");
		}
		return json;
	}

	/**
	 * 执行绑邮箱
	 */
	@RequestMapping("bindEmail")
	public String bindEmail(String uuid, Model model) {
		try {
			userinfoService.bindEmail(uuid);
			model.addAttribute("success", true);
		} catch (RuntimeException e) {
			//model.addAttribute("success", false);
			model.addAttribute("msg", e.getMessage());
		}
		return "checkmail_result";
	}
}
