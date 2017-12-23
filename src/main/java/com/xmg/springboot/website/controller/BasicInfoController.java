package com.xmg.springboot.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.springboot.p2p.base.domain.Userinfo;
import com.xmg.springboot.p2p.base.service.ISystemDictionaryService;
import com.xmg.springboot.p2p.base.service.IUserinfoService;
import com.xmg.springboot.p2p.util.AjaxResult;
import com.xmg.springboot.website.util.UserContext;

/**
 * 用户基本信息控制器啊
 * @author 1
 *
 */
@Controller
public class BasicInfoController {
	@Autowired
	private IUserinfoService userinfoService;
	@Autowired
	private ISystemDictionaryService dicService;

	@RequestMapping("basicInfo")
	public String basicInfo(Model model) {
		model.addAttribute("educationBackgrounds",
				dicService.loadItemsBySn("educationBackground"));
		model.addAttribute("incomeGrades",
				dicService.loadItemsBySn("incomeGrade"));
		model.addAttribute("marriages", dicService.loadItemsBySn("marriage"));
		model.addAttribute("kidCounts", dicService.loadItemsBySn("kidCount"));
		model.addAttribute("houseConditions",
				dicService.loadItemsBySn("houseCondition"));
		model.addAttribute("userinfo",
				userinfoService.get(UserContext.getCurrent().getId()));
		return "userInfo";

	}

	@RequestMapping("basicInfo_save")
	@ResponseBody
	public AjaxResult basicInfo_save(Userinfo userinfo) {
		AjaxResult ajaxResult = new AjaxResult();
		userinfo.setId(UserContext.getCurrent().getId());
		userinfoService.updateBasicInfo(userinfo);
		return ajaxResult;
	}
}
