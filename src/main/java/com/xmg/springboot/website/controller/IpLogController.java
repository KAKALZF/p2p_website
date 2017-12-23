package com.xmg.springboot.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xmg.springboot.p2p.base.Query.IpLogQueryObject;
import com.xmg.springboot.p2p.base.page.PageResult;
import com.xmg.springboot.p2p.base.service.IIpLogService;
import com.xmg.springboot.website.controller.interceptors.RequireLogin;
import com.xmg.springboot.website.util.UserContext;

/**
 * 前台登录日志控制器
 * @author 1
 *
 */
@Controller
public class IpLogController {
	@Autowired
	private IIpLogService ipLogService;

	/**
	 * 登录日志列表
	 * @param model
	 * @return
	 */
	@RequireLogin
	@RequestMapping("ipLog")
	public String ipLogList(@ModelAttribute("qo") IpLogQueryObject qo,
			Model model) {
		qo.setUsername(UserContext.getCurrent().getUsername());
		PageResult pageResult = ipLogService.query(qo);
		model.addAttribute("pageResult", pageResult);
		return "iplog_list";
		//return "iplog1";
	}
	//	/**
	//	 * 登录日志外壳
	//	 * @param model
	//	 * @return
	//	 */
	//	@RequireLogin
	//	@RequestMapping("ipLog")
	//	public String ipLog() {
	//		return "iplog_list";
	//	}
	//	/**
	//	 * 登录日志内容
	//	 * @param model
	//	 * @return
	//	 */
	//	@RequireLogin
	//	@RequestMapping("ipLog_list")
	//	public String ipLogList(@ModelAttribute("qo") IpLogQueryObject qo,
	//			Model model) {
	//		qo.setUsername(UserContext.getCurrent().getUsername());
	//		PageResult pageResult = ipLogService.query(qo);
	//		model.addAttribute("pageResult", pageResult);
	//		return "iplog1";
	//	}
}
