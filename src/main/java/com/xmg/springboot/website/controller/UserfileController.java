package com.xmg.springboot.website.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xmg.springboot.p2p.base.domain.Userfile;
import com.xmg.springboot.p2p.base.service.ISystemDictionaryService;
import com.xmg.springboot.p2p.base.service.IUserFileService;
import com.xmg.springboot.p2p.util.AjaxResult;
import com.xmg.springboot.p2p.util.Consts;
import com.xmg.springboot.website.util.UploadUtil;
import com.xmg.springboot.website.util.UserContext;

@Controller
public class UserfileController {
	private static final Logger logger = LoggerFactory
			.getLogger(UserfileController.class);
	@Autowired
	private IUserFileService userFileService;
	@Autowired
	private ISystemDictionaryService dicService;

	@RequestMapping("userFile")
	public String userFile(Model model) {
		//先查询没有选择风控材料分类的风控材料
		List<Userfile> userFiles = userFileService.listSelectTypeUserFiles(
				UserContext.getCurrent().getId(), false);
		if (userFiles.size() > 0) {
			//如果有
			model.addAttribute("userFiles", userFiles);
			model.addAttribute("fileTypes",
					dicService.loadItemsBySn("userFileType"));
			return "userFiles_commit";
		} else {
			//跳转到userFile_commit
			//如果没有,再查询所有选择了风控材料分类的风控材料
			//跳转到userFiles
			userFiles = userFileService.listSelectTypeUserFiles(UserContext
					.getCurrent().getId(), true);
			model.addAttribute("userFiles", userFiles);

			return "userFiles";
		}

	}

	/**
	 * 处理风控图片上传
	 * @param userinfo
	 * @return
	 */
	@RequestMapping("userFileApply")
	@ResponseBody
	public String userFileApply(MultipartFile image, Long userid) {
		String fileName = UploadUtil.upload(image, Consts.IMG_DIR_PATH);
		userFileService.apply(fileName, userid);
		return fileName;
	}

	/**
	 * 处理风控材料分类
	 */
	@RequestMapping("userFile_selectType")
	@ResponseBody
	public AjaxResult choiceTypes(Long[] id, Long[] fileType) {
		if (id != null && fileType != null && id.length == fileType.length) {
			userFileService.batchChoiceType(id, fileType);
		}
		return new AjaxResult();
	}
}
