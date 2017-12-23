package com.xmg.springboot.website.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.springboot.p2p.base.Query.UserfileQueryObject;
import com.xmg.springboot.p2p.base.domain.Account;
import com.xmg.springboot.p2p.base.domain.Logininfo;
import com.xmg.springboot.p2p.base.domain.Userfile;
import com.xmg.springboot.p2p.base.domain.Userinfo;
import com.xmg.springboot.p2p.base.service.IAccountService;
import com.xmg.springboot.p2p.base.service.IRealAuthService;
import com.xmg.springboot.p2p.base.service.IUserFileService;
import com.xmg.springboot.p2p.base.service.IUserinfoService;
import com.xmg.springboot.p2p.business.domain.BidRequest;
import com.xmg.springboot.p2p.business.service.IBidRequestSevice;
import com.xmg.springboot.p2p.util.AjaxResult;
import com.xmg.springboot.p2p.util.Consts;
import com.xmg.springboot.website.util.UserContext;

@Controller
public class BorrowController {
	@Autowired
	private IUserinfoService userinfoService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IBidRequestSevice bidRequestSevice;
	@Autowired
	private IRealAuthService realAuthService;
	@Autowired
	private IUserFileService userFileService;

	@RequestMapping("/borrow")
	public String borrowView(Model model) {

		Logininfo current = UserContext.getCurrent();
		if (current != null) {
			Userinfo userinfo = userinfoService.get(current.getId());
			Account account = accountService.get(current.getId());
			model.addAttribute("account", account);
			model.addAttribute("userinfo", userinfo);
			model.addAttribute("creditBorrowScore", Consts.BORROW_SCORE_LIMIT);
			return "borrow";
		}
		return "redirect:borrowIndex.html";

	}

	/**
	 * 导向到借款页面
	 */
	@RequestMapping("/toBorrowApply")
	public String toborrowApplyPage(Model model) {
		//先判断当前用户满足了借款的前置条件
		Userinfo current = userinfoService
				.get(UserContext.getCurrent().getId());
		if (current.getHasBasicInfo() && current.getHasRealAuth()
				&& current.getHasVedioAuth()
				&& current.getScore() >= Consts.BORROW_SCORE_LIMIT) {
			if (current.getHasBidIRequestInProcess()) {
				return "borrow_apply_result";
			} else {
				//根据页面内容填充modal
				model.addAttribute("minBidRequestAmount",
						Consts.SMALLEST_BIDREQUEST_AMOUNT);
				model.addAttribute("account",
						accountService.get(current.getId()));
				model.addAttribute("minCurrentRate",
						Consts.SMALLEST_CURRENT_RATE);
				model.addAttribute("maxCurrentRate", Consts.MAX_CURRENT_RATE);
				model.addAttribute("minBidAmount", Consts.SMALLEST_BID_AMOUNT);
				return "borrow_apply";
			}
		}
		return "redirect:borrow";
	}

	/**
	 * 借款申请
	 * 
	 */
	@RequestMapping("borrow_apply")
	@ResponseBody
	public AjaxResult borrow_apply(BidRequest bidRequest) {
		bidRequestSevice.apply(bidRequest, UserContext.getCurrent());
		return new AjaxResult();

	}

	/**
	 * 前台借款详情
	 */

	@RequestMapping("borrow_info")
	public String borrowInfo(Long id, Model model) {
		BidRequest br = bidRequestSevice.get(id);
		model.addAttribute("bidRequest", br);
		//借款人信息
		Userinfo applier = userinfoService.get(br.getCreateUser().getId());
		model.addAttribute("userInfo", applier);
		//借款人实名认证资料
		model.addAttribute("realAuth",
				realAuthService.get(applier.getRealAuthId()));
		//借款人的风控资料
		UserfileQueryObject qo = new UserfileQueryObject();
		qo.setState(Userfile.STATE_PASS);
		qo.setUserId(applier.getId());
		qo.setPageSize(UserfileQueryObject.PAGE_SIZE_UMLIMIT);
		List<Userfile> userFiles = userFileService.queryForList(qo);
		model.addAttribute("userFiles", userFiles);
		if (UserContext.getCurrent() != null) {

			if (UserContext.getCurrent().getId().equals(applier.getId())) {
				model.addAttribute("self", true);

			} else {
				model.addAttribute("account",
						accountService.get(UserContext.getCurrent().getId()));
			}
		}
		return "borrow_info";
	}

	/**
	 *投标
	 */
	@RequestMapping("borrow_bid")
	@ResponseBody
	public AjaxResult bid(BigDecimal amount, Long bidRequestId) {
		bidRequestSevice.bid(amount,bidRequestId,UserContext.getCurrent());
		return new AjaxResult();

	}
}
