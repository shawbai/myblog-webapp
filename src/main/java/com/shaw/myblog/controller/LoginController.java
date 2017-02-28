package com.shaw.myblog.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shaw.myblog.common.constant.ConstantsUtil;
import com.shaw.myblog.common.pojo.BaseResponse;
import com.shaw.myblog.common.session.SessionProvider;
import com.shaw.myblog.common.utils.CaptchaCacheUtil;
import com.shaw.myblog.common.utils.CookieUtils;
import com.shaw.myblog.common.utils.SHA1MD5;
import com.shaw.myblog.common.utils.StringUtil;
import com.shaw.myblog.facade.user.pojo.dto.MemberDto;
import com.shaw.myblog.facade.user.pojo.vo.LoginVo;
import com.shaw.myblog.facade.user.service.MemberQueryFacade;

@Controller
@RequestMapping("/passport")
public class LoginController {
	/**
	 * 失败次数 cookie 的 key
	 */
	private static final String Input_ERROR_COUNT = "_capt";

	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private CaptchaCacheUtil captchaCacheUtil;
	@Autowired
	private SessionProvider sessionProvider;
	
    @Autowired
    private MemberQueryFacade memberQueryFacade;

	@RequestMapping("/login")
	@ResponseBody
	public Object login(HttpServletRequest req, HttpServletResponse resp, LoginVo LoginVo) {
		System.out.println(req.getRequestURL());
		int temErrorNum = 0;
		String errorNum = CookieUtils.getCookieValue(req, Input_ERROR_COUNT);
		if (!StringUtil.isNullString(errorNum)) {
			temErrorNum = Integer.parseInt(errorNum);
		}
		/* 超过5次验证验证码 */
		if (temErrorNum > 5) {
			String captchaCode = LoginVo.getCaptchaCode();
			if (StringUtil.isNullString(captchaCode)) {
				return BaseResponse.error(ConstantsUtil.UserMessage.ERROR_CODE);
			}
			String sessionId = sessionProvider.getSessionId(req, resp);
			String sessionSecurityCode = captchaCacheUtil.getImageCodeFromCache(sessionId);
			if (StringUtil.isNullString(sessionSecurityCode) || !sessionSecurityCode.equalsIgnoreCase(captchaCode)) {
				return BaseResponse.error(ConstantsUtil.UserMessage.ERROR_CODE);
			}
		}
		MemberDto memberDto = memberQueryFacade.queryByTelePhoneOrUserName(LoginVo.getMemberName());
		 try {
	            logger.info("登录校验中.... memebr is {}", memberDto);
	            if (memberDto != null) {
	                String sha1Str = SHA1MD5.getSha1(LoginVo.getPassword() + memberDto.getSalt());
	                System.out.println(LoginVo.getPassword());
	                System.out.println(sha1Str);
	                if (memberDto.getPassword().equals(sha1Str)) {
	                    logger.info("登录校验成功! ");
	                    memberDto.setPassword(null);
	                    memberDto.setSalt(null);
	                    sessionProvider.setAttribute(req, resp, ConstantsUtil.LOGIN_USER_SESSION_KEY, memberDto);// put user{Object} into session
	                    sessionProvider.setAttribute(req, resp, ConstantsUtil.MEMBERID_SESSION_KEY, memberDto.getId()); // put userId into session
	                    // 清除验证匹配失败次数cookie
	                    CookieUtils.deleteCurrentDomainCookie(req, resp, Input_ERROR_COUNT);
	                    Boolean rememberMe = LoginVo.getRememberMe();
	                    // 记住用户名
	                    if (rememberMe != null && rememberMe) {
	                        CookieUtils.setCookie(req, resp, ConstantsUtil.REMEMBER_ME_KEY, LoginVo.getMemberName(), 60 * 60 * 24 * 20);
	                    } else {
	                        CookieUtils.deleteCookie(req, resp, ConstantsUtil.REMEMBER_ME_KEY);
	                    }
	                    return BaseResponse.success(memberDto);
	                } else {
	                    setErrorCount(temErrorNum, req, resp);
	                    return BaseResponse.error(ConstantsUtil.UserMessage.ERROR_LOGIN_PASSWORD);
	                }
	            }
	            setErrorCount(temErrorNum, req, resp);    //登录统计次数加1
	            return BaseResponse.error(ConstantsUtil.UserMessage.ERROR_USERNAM_NOT_EXSIST);
	        } catch (Exception e) {
	            logger.error(e.getMessage(), e);
	            return BaseResponse.error("系统繁忙,请稍后重试");
	        }
	}
	
    /**
     * 退出登录
     *
     * @param req
     * @param resp
     * @return
     */
    @ResponseBody
    @RequestMapping("/logout")
    public Object logout(HttpServletRequest req, HttpServletResponse resp) {
        sessionProvider.logout(req, resp);
        return BaseResponse.success();
    }

	private void setErrorCount(int countNum, HttpServletRequest request, HttpServletResponse response) {
		CookieUtils.setCookie(request, response, Input_ERROR_COUNT, String.valueOf(++countNum),
				ConstantsUtil.ERROR_LOGIN_COOKIE_MAX_AGE);
	}

}
