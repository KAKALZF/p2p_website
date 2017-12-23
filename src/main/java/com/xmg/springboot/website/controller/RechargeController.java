package com.xmg.springboot.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion.User;
import com.xmg.springboot.p2p.business.domain.RechargeOffline;
import com.xmg.springboot.p2p.business.service.IPlatformBankInfoService;
import com.xmg.springboot.p2p.business.service.IRechargeOfflineService;
import com.xmg.springboot.p2p.util.AjaxResult;
import com.xmg.springboot.website.util.UserContext;

/**
 * 线下充值控制器
 * @author 1
 *
 */
@Controller
public class RechargeController {
	@Autowired
	private IRechargeOfflineService rechargeOfflineService;
	@Autowired
	private IPlatformBankInfoService bankInfoService;

	/**
	 * 导向到线下充值页面
	 * @param model
	 * @return
	 */
	@RequestMapping("recharge")
	public String recharge(Model model) {
		model.addAttribute("banks", bankInfoService.listAll());
		return "recharge";
	}

	@RequestMapping("recharge_save")
	@ResponseBody
	public AjaxResult apply(RechargeOffline re) {
		rechargeOfflineService.apply(re,UserContext.getCurrent());
		return new AjaxResult();
	}
}
