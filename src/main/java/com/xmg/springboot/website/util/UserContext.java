package com.xmg.springboot.website.util;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xmg.springboot.p2p.base.domain.Logininfo;
import com.xmg.springboot.p2p.base.vo.VerifyCodeVO;

public class UserContext {
	public static String CURRENT_USER_IN_SESSION = "logininfo";
	public static String VERIFY_CODE_IN_SESSION = "VERIFY_CODE_IN_SESSION";

	public static HttpSession session() {
		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
	}

	/**
	 * 把当前验证码信息放到会话中去
	 */
	public static void setVerifyCodeVO(VerifyCodeVO vo) {
		session().setAttribute(VERIFY_CODE_IN_SESSION, vo);
	}

	/**
	 * 得到当前验证码相关信息
	 */
	public static VerifyCodeVO getVerifyCodeVO() {
		return (VerifyCodeVO) session().getAttribute(VERIFY_CODE_IN_SESSION);
	}

	/**
	 * 把当前用户放到会话中
	 */
	public static void putCurrent(Logininfo current) {
		session().setAttribute(CURRENT_USER_IN_SESSION, current);
	}

	/**
	 * 获取当前用户
	 * @return
	 */
	public static Logininfo getCurrent() {
		return (Logininfo) session().getAttribute(CURRENT_USER_IN_SESSION);
	}
}
