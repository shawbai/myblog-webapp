package com.shaw.myblog.facade.user.pojo.vo;

import java.io.Serializable;

public class LoginVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

    private String memberName;
    private String password;
    private Boolean rememberMe;
    private String captchaCode;
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getRememberMe() {
		return rememberMe;
	}
	public void setRememberMe(Boolean rememberMe) {
		this.rememberMe = rememberMe;
	}
	public String getCaptchaCode() {
		return captchaCode;
	}
	public void setCaptchaCode(String captchaCode) {
		this.captchaCode = captchaCode;
	}
	@Override
	public String toString() {
		return "LoginVo [memberName=" + memberName + ", password=" + password + ", rememberMe=" + rememberMe
				+ ", captchaCode=" + captchaCode + "]";
	}
    
	
}
