package com.xxl.job.admin.controller;

import com.xxl.job.admin.controller.annotation.PermessionLimit;
import com.xxl.job.admin.controller.interceptor.MorePermissionInterceptor;
import com.xxl.job.admin.core.util.I18nUtil;
import com.xxl.job.admin.service.XxlJobService;
import com.xxl.job.core.biz.model.ReturnT;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * index controller
 * @author xuxueli 2015-12-19 16:13:16
 */
@Controller
public class IndexController {

	@Resource
	private XxlJobService xxlJobService;

	@RequestMapping("/")
	public String index(Model model) {

		Map<String, Object> dashboardMap = xxlJobService.dashboardInfo();
		model.addAllAttributes(dashboardMap);
		return "index";
	}

    @RequestMapping("/chartInfo")
	@ResponseBody
	public ReturnT<Map<String, Object>> chartInfo(Date startDate, Date endDate) {
        ReturnT<Map<String, Object>> chartInfo = xxlJobService.chartInfo(startDate, endDate);
        return chartInfo;
    }
	
	@RequestMapping("/toLogin")
	@PermessionLimit(limit=false)
	public String toLogin(Model model, HttpServletRequest request) {
		if (MorePermissionInterceptor.ifAuthority(request)>-1) {
			return "redirect:/";
		}
		return "login";
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	@ResponseBody
	@PermessionLimit(limit=false)
	public ReturnT<String> loginDo(HttpServletRequest request, HttpServletResponse response, String userName, String password, String ifRemember){
		// valid
		if (MorePermissionInterceptor.ifAuthority(request)>-1) {
			return ReturnT.SUCCESS;
		}

		// param
		if (userName==null || userName.trim().length()==0 || password==null || password.trim().length()==0){
			return new ReturnT<String>(500, I18nUtil.getString("login_param_empty"));
		}
		boolean ifRem = (ifRemember!=null && ifRemember.trim().length()>0 && "on".equals(ifRemember))?true:false;

		// do login
		boolean loginRet = MorePermissionInterceptor.login(response, userName, password, ifRem);
		if (!loginRet) {
			return new ReturnT<String>(500, I18nUtil.getString("login_param_unvalid"));
		}
		return ReturnT.SUCCESS;
	}
	
	@RequestMapping(value="logout", method=RequestMethod.POST)
	@ResponseBody
	@PermessionLimit(limit=false)
	public ReturnT<String> logout(HttpServletRequest request, HttpServletResponse response){
		if (MorePermissionInterceptor.ifAuthority(request)>-1) {
			MorePermissionInterceptor.logout(request, response);
		}
		return ReturnT.SUCCESS;
	}
	
	@RequestMapping("/help")
	public String help() {

		/*if (!PermissionInterceptor.ifLogin(request)) {
			return "redirect:/toLogin";
		}*/

		return "help";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
}
