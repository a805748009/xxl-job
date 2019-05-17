package com.xxl.job.admin.controller;

import com.xxl.job.admin.controller.annotation.PermessionLimit;
import com.xxl.job.admin.core.model.XxlJobUser;
import com.xxl.job.admin.core.util.I18nUtil;
import com.xxl.job.admin.dao.XxlJobUserDao;
import com.xxl.job.core.biz.model.ReturnT;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * job group controller
 * @author xuxueli 2016-10-02 20:52:56
 */
@Controller
@RequestMapping("/jobuser")
public class JobUserController {

	@Resource
	public XxlJobUserDao xxlJobUserDao;

	@RequestMapping
	public String index(Model model) {
		List<XxlJobUser> list = xxlJobUserDao.findAll();
		model.addAttribute("list", list);
		return "jobuser/jobuser.index";
	}

	@RequestMapping("/save")
	@ResponseBody
	@PermessionLimit(write=true)
	public ReturnT<String> save(XxlJobUser xxlJobUser){

		// valid
		if (xxlJobUser.getUserName()==null || StringUtils.isBlank(xxlJobUser.getUserName())) {
			return new ReturnT<String>(500, (I18nUtil.getString("system_please_input")+"userName") );
		}
		if (xxlJobUser.getUserName().length()<5 || xxlJobUser.getUserName().length()>16) {
			return new ReturnT<String>(500, I18nUtil.getString("user_name_error") );
		}
		if (xxlJobUser.getPassword().length()<5 || xxlJobUser.getPassword().length()>32) {
			return new ReturnT<String>(500, I18nUtil.getString("user_password_error") );
		}

		String password = DigestUtils.md5DigestAsHex(String.valueOf(xxlJobUser.getPassword()).getBytes());
		xxlJobUser.setPassword(password);
		xxlJobUser.setLastLoginTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		int ret = xxlJobUserDao.save(xxlJobUser);
		return (ret>0)?ReturnT.SUCCESS:ReturnT.FAIL;
	}

	@RequestMapping("/update")
	@ResponseBody
	@PermessionLimit(write=true)
	public ReturnT<String> update(XxlJobUser xxlJobUser){
		// valid
		if (xxlJobUser.getUserName()==null || StringUtils.isBlank(xxlJobUser.getUserName())) {
			return new ReturnT<String>(500, (I18nUtil.getString("system_please_input")+"userName") );
		}
		if (xxlJobUser.getUserName().length()<5 || xxlJobUser.getUserName().length()>16) {
			return new ReturnT<String>(500, I18nUtil.getString("user_name_error") );
		}
		if (xxlJobUser.getPassword()!=null && StringUtils.isNotBlank(xxlJobUser.getPassword())) {
			if (xxlJobUser.getPassword().length()<5 || xxlJobUser.getPassword().length()>32) {
				return new ReturnT<String>(500, I18nUtil.getString("user_password_error") );
			}
			String password = DigestUtils.md5DigestAsHex(String.valueOf(xxlJobUser.getPassword()).getBytes());
			xxlJobUser.setPassword(password);
		}
		xxlJobUserDao.update(xxlJobUser);
		return ReturnT.SUCCESS;
	}




	@RequestMapping("/remove")
	@ResponseBody
	@PermessionLimit(write=true)
	public ReturnT<String> remove(String username){
		int ret = xxlJobUserDao.remove(username);
		return (ret>0)?ReturnT.SUCCESS:ReturnT.FAIL;
	}

}
