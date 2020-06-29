package com.perkinelmer.SSO_Spotfire.controller;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.perkinelmer.SSO_Spotfire.tools.Des3;

@Controller
@RequestMapping("/Perkinelmer")
public class ApiController {
	@Value("${spotfireIp}")
	private String spotfireIp;
	@Value("${spotfirePort}")
	private String spotfirePort;
	@Value("${defaultLibraryname}")
	private String defaultLibraryname;
	@RequestMapping("SSOTransfor")
	public void SSOTransfor(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws UnsupportedEncodingException, Exception {
		String username = "spotfire";
		String password = "spotfire";
		String libraryname = defaultLibraryname;
		//获得参数并解密，这里接受的数据要想没问题必须发送我们的主动做了URL编码处理
		String sec = httpServletRequest.getParameter("sec");
		//String urlDecoderSec =  URLDecoder.decode(sec, "UTF-8");
		String result=Des3.decode(sec);
		/*
		 * String userNamePattern = "dldm\\s*=\\s*(.*?)\\s*&"; Pattern userNamer =
		 * Pattern.compile(userNamePattern); Matcher userNamem =
		 * userNamer.matcher(result); if(userNamem.find()) { username =
		 * userNamem.group(1); } String passWordPattern = "dlmm\\s*=\\s*(.*)\\s*";
		 * Pattern passWordr = Pattern.compile(passWordPattern); Matcher passWordm =
		 * passWordr.matcher(result); if(passWordm.find()) { password =
		 * passWordm.group(1); }
		 */
		String UPPattern = "dldm\\s*=\\s*(.*?)\\s*&.*dlmm\\s*=\\s*(.*)\\s*";
		Pattern UPr = Pattern.compile(UPPattern);
		Matcher UPm = UPr.matcher(result); 
		if(UPm.find()) {
			username = UPm.group(1);
			password = UPm.group(2);
		}
		//带着参数访问spotfire链接
		String spotfireNoPUrl = "http://"+spotfireIp+":"+spotfirePort+"/spotfire/wp/analysis?file="+libraryname;
		String spotfireHasPUrl = "http://"+spotfireIp+":"+spotfirePort+"/spotfire/resources/custom-login/custom-login-app-example.html?username="+username+"&password="+password+"&libraryname="+libraryname;
		//如果目标url已经存了用户信息则直接访问不带参数的目标地址否则转发带参数的免密登录
		//做判断是否已登录代码
		HttpSession session = httpServletRequest.getSession();
		String flagSSOTransfor = session.getAttribute("flagSSOTransfor")==null? "0":session.getAttribute("flagSSOTransfor").toString();
		if("1".equals(flagSSOTransfor)) {
			//httpServletRequest.fo(spotfireNoPUrl).forward(httpServletRequest, httpServletResponse);
			httpServletResponse.sendRedirect(spotfireNoPUrl);
		}else {
			session.setAttribute("flagSSOTransfor", "1");
			httpServletResponse.sendRedirect(spotfireHasPUrl);
			//httpServletRequest.getRequestDispatcher(spotfireHasPUrl).forward(httpServletRequest, httpServletResponse);
		}
		
		
	}
}
