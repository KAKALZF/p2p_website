package com.xmg.springboot.website.controller.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xmg.springboot.website.util.UserContext;

/**
 * 登录拦截器
 * @author 1
 *
 */
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod hMethod = (HandlerMethod) handler;
			if (hMethod.hasMethodAnnotation(RequireLogin.class)
					&& UserContext.getCurrent() == null) {
				response.sendRedirect("/login.html");
				return false;
			}
		}
		return super.preHandle(request, response, handler);
	}
}
