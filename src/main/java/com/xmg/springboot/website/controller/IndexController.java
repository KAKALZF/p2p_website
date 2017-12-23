package com.xmg.springboot.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xmg.springboot.p2p.base.page.PageResult;
import com.xmg.springboot.p2p.business.query.BidRequestQueryObject;
import com.xmg.springboot.p2p.business.service.IBidRequestSevice;
import com.xmg.springboot.p2p.util.Consts;

/**
 * 首页
 * @author 1
 *
 */
@Controller
public class IndexController {
	@Autowired
	private IBidRequestSevice bidRequestSevice;

	@RequestMapping("index")
	public String index(Model model) {
		BidRequestQueryObject qo = new BidRequestQueryObject();
		qo.setBidRequestStates(new int[] { Consts.BIDREQUEST_STATE_BIDDING,
				Consts.BIDREQUEST_STATE_PAYING_BACK,
				Consts.BIDREQUEST_STATE_COMPLETE_PAY_BACK });
		qo.setPageSize(5);
		qo.setOrderBy("br.bidRequestState");
		qo.setOrderType("ASC");
		model.addAttribute("bidRequests", bidRequestSevice.queryForList(qo));
		return "main";
	}

	/**
	 * 投资列表外壳
	 */
	@RequestMapping("invest")
	public String investFrame() {
		return "invest";
	}

	/**
	 * 投资列表内容
	 */
	@RequestMapping("invest_list")
	public String investList(BidRequestQueryObject qo, Model model) {
		if (qo.getBidRequestState() == -1) {
			qo.setBidRequestStates(new int[] { Consts.BIDREQUEST_STATE_BIDDING,
					Consts.BIDREQUEST_STATE_PAYING_BACK,
					Consts.BIDREQUEST_STATE_COMPLETE_PAY_BACK });
		}
		PageResult pageResult = bidRequestSevice.query(qo);
		model.addAttribute("pageResult", pageResult);
		return "invest_list";
	}

}
