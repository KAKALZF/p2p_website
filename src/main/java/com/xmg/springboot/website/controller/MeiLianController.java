package com.xmg.springboot.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MeiLianController {
	@RequestMapping("sendSms")
	@ResponseBody
	public String sendSms(String username, String password, String phoneNumber,
			String content, String apikey) {
		System.out.println("短信已发" + content);
		return "success:msgid";

	}
}
