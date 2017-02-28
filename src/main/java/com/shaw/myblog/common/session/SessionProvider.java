package com.shaw.myblog.common.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

public interface SessionProvider {
	
	void setAttribute(HttpServletRequest request, HttpServletResponse response, String key, Serializable value);
	
	Serializable getAttribute(HttpServletRequest request, HttpServletResponse response, String key);
	
	void logout(HttpServletRequest request, HttpServletResponse response);

	void casLogout(String sessionId);

	String getSessionId(HttpServletRequest request, HttpServletResponse response);

	Serializable getUserByToken(String token);
	
	@Deprecated
	void syncSessionAccessTimeToCache(HttpServletRequest request, HttpServletResponse response, String sessionId) ;
	
}
