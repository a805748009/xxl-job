package com.xxl.job.admin.controller.interceptor;

import com.xxl.job.admin.controller.annotation.PermessionLimit;
import com.xxl.job.admin.core.conf.XxlJobAdminConfig;
import com.xxl.job.admin.core.model.XxlJobUser;
import com.xxl.job.admin.core.util.AESUtil;
import com.xxl.job.admin.core.util.CookieUtil;
import com.xxl.job.admin.core.util.SpringApplicationContextHolder;
import com.xxl.job.admin.dao.XxlJobUserDao;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 权限拦截, 升级简易版
 *
 * @author hxy 2019-03-09 15:06:24
 */
@Component
public class MorePermissionInterceptor extends PermissionInterceptor {

	private static String startStr = "dpMwh2AsPgh";

	public  static String getAesKey() {
		String str = SpringApplicationContextHolder.getSpringBeanForClass(XxlJobAdminConfig.class).getAesKey();
		return str;
	}


	public static String getLoginIdentityToken( String username, int authority) {
		String token = null;
		try {
			token = AESUtil.encrypt(startStr+username+"_"+authority,getAesKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}

	public static boolean login(HttpServletResponse response, String username, String password, boolean ifRemember){
		XxlJobUserDao xxlJobUserDao = ((XxlJobUserDao) SpringApplicationContextHolder.getSpringBean("xxlJobUserDao"));
		XxlJobUser xxlJobUser = xxlJobUserDao.findByUserName(username);
		if(xxlJobUser==null||!xxlJobUser.getPassword().equals(DigestUtils.md5DigestAsHex(String.valueOf(password).getBytes()))){
			return false;
		}

		// do login
		CookieUtil.set(response, LOGIN_IDENTITY_KEY, getLoginIdentityToken(xxlJobUser.getUserName(),xxlJobUser.isAuthority()?1:0), ifRemember);
		xxlJobUserDao.update(xxlJobUser.setPassword(null)
				.setLastLoginTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
		return true;
	}

	public static int ifAuthority(HttpServletRequest request){
		String indentityInfo = CookieUtil.getValue(request, LOGIN_IDENTITY_KEY);
		if(StringUtils.isEmpty(indentityInfo)) return -1;
		String str = null;
		try {
			str = AESUtil.decrypt(indentityInfo,getAesKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (indentityInfo==null || !str.startsWith(startStr)) {
			return -1;
		}
		return Integer.parseInt(str.split("_")[1]);
	}



	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		if (!(handler instanceof HandlerMethod)) {
			return super.preHandle(request, response, handler);
		}

		int authority = ifAuthority(request);

		HandlerMethod method = (HandlerMethod)handler;
		PermessionLimit permission = method.getMethodAnnotation(PermessionLimit.class);
		//未登陆
		if (authority==-1) {
			if (permission == null || permission.limit()) {
				response.sendRedirect(request.getContextPath() + "/toLogin");
				return false;
			}
		}

		//没有写权限
		if(permission != null&&authority==0&&permission.write()){
			response.sendRedirect(request.getContextPath() + "/toLogin");
			return false;
		}

		request.setAttribute("authority",authority);
		
		return true;
	}
	
}
