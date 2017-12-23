package com.xmg.springboot.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.springboot.p2p.base.domain.Account;
import com.xmg.springboot.p2p.base.page.PageResult;
import com.xmg.springboot.p2p.base.service.IAccountService;
import com.xmg.springboot.p2p.business.query.PaymentScheduleQueryObject;
import com.xmg.springboot.p2p.business.service.IBidRequestSevice;
import com.xmg.springboot.p2p.util.AjaxResult;
import com.xmg.springboot.website.util.UserContext;

/**
 * 还款控制器
 * @author 1
 *
 */
@Controller
public class RetrunMoneyController {
	@Autowired
	private IBidRequestSevice bidRequestSevice;
	@Autowired
	private IAccountService accountService;

	/**
	 * 还款列表
	 * @param model
	 * @param qo
	 * @return
	 */
	@RequestMapping("borrowBidReturn_list")
	public String borrowBidReturnList(Model model,
			@ModelAttribute("qo") PaymentScheduleQueryObject qo) {
		PageResult pageResult = bidRequestSevice.queryPaymentSchedule(qo);
		Account account = accountService.get(UserContext.getCurrent().getId());
		model.addAttribute("pageResult", pageResult);
		model.addAttribute("account", account);
		return "returnmoney_list";
	}

	/**
	 *还款
	 */
	@RequestMapping("returnMoney")
	@ResponseBody
	public AjaxResult returnMoney(Long id) {
		bidRequestSevice.returnMoney(id, UserContext.getCurrent());
		return new AjaxResult();
	}
}
