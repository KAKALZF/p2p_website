package com.xmg.springboot.website.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xmg.springboot.p2p.base.domain.RealAuth;
import com.xmg.springboot.p2p.base.domain.Userinfo;
import com.xmg.springboot.p2p.base.service.IRealAuthService;
import com.xmg.springboot.p2p.base.service.IUserinfoService;
import com.xmg.springboot.p2p.util.AjaxResult;
import com.xmg.springboot.p2p.util.Consts;
import com.xmg.springboot.website.util.UploadUtil;
import com.xmg.springboot.website.util.UserContext;

/**
 * 实名认证控制器
 * @author 1
 *
 */
@Controller
public class RealAuthorController {
	private static final Logger logger = LoggerFactory
			.getLogger(RealAuthorController.class);
	@Autowired
	private IUserinfoService userinfoService;
	@Autowired
	private IRealAuthService realAuthService;

	@RequestMapping("realAuth")
	public String toRealAuth(Model model) {
		//得到当前用户
		Userinfo current = userinfoService
				.get(UserContext.getCurrent().getId());
		//1.如果实名认证通过
		if (current.getHasRealAuth()) {
			//查询实名认证对象,放到model中.跳转到realAuth_result.ftl
			model.addAttribute("realAuth",
					realAuthService.get(current.getRealAuthId()));
			return "realAuth_result";
		} else {
			//2.如果实名制认证没有通过
			//2.1 realAuthId 为空,跳转到realAuth.ftl
			if (current.getRealAuthId() == null) {
				return "realAuth";
			} else {
				//2.2realauthid不为空,model添加auditing,跳转到realAuth_result.ftl
				model.addAttribute("auditing", true);
				return "realAuth_result";
			}
		}

	}

	/**
	 * 实名认证申请
	 * @param model
	 * @return
	 */
	@RequestMapping("realAuth_save")
	@ResponseBody
	public AjaxResult apply(RealAuth ra) {
		AjaxResult json = new AjaxResult();
		realAuthService.apply(ra, UserContext.getCurrent());
		return json;
	}

	/**
	 * 实名认证图片上传
	 * @param model
	 * @return
	 */
	@RequestMapping("realAuthImageUpload")
	@ResponseBody
	public String realAuthImageUpload(MultipartFile image) {
		logger.info("==========图片上传");
		String fileName = UploadUtil.upload(image, Consts.IMG_DIR_PATH);
		return fileName;
	}
}
