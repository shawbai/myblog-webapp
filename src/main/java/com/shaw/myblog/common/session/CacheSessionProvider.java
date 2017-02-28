package com.shaw.myblog.common.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.shaw.myblog.common.constant.ConstantsUtil;
import com.shaw.myblog.common.redis.RedisTempalte;
import com.shaw.myblog.common.utils.CookieUtils;
import com.shaw.myblog.common.utils.StringUtil;
@Component
public class CacheSessionProvider implements SessionProvider {
    private static Logger logger = LoggerFactory.getLogger(CacheSessionProvider.class);
    private String cookie_token = "rtk";
    private String session_domain = "session_domain:";


    private int expiry = 30;                                            // 分钟
    private static int cache_index = ConstantsUtil.REDIS_SESSION_INDEX;
    private int expirationUpdateInterval;                                // Session最大更新间隔时间

    @Autowired
    private RedisTempalte redisTempalte;

    public void setAttribute(HttpServletRequest request, HttpServletResponse response, String key, Serializable value) {
        Map<String, Object> sessionMap = null;
        //先取出原来session中的值 添加到map中
        String sessionId = getSessionId(request, response);
        logger.info("sessionId is {}", sessionId);
        String old_session_json = redisGetWithSessionId(sessionId);
        if (null != old_session_json) {
            RedisSession oldSession = JSON.parseObject(old_session_json, RedisSession.class);
            sessionMap = oldSession.getData();
        }
        sessionMap = null != sessionMap ? sessionMap : new HashMap<String, Object>();
        sessionMap.put(key, value);

        RedisSession session = new RedisSession();
        session.setCreationTime(System.currentTimeMillis());
        session.setLastAccessedTime(System.currentTimeMillis());
        session.setData(sessionMap);
        session.setId(sessionId);

        String redisSessionValue = JSON.toJSONString(session);
        this.redisSetWithDomain(sessionId, redisSessionValue, expiry * 60);
    }

    private void redisSetWithDomain(String sessionId, String value, int seconds) {
        redisTempalte.set(cache_index, session_domain.concat(sessionId), value, seconds);
    }


    private String redisGetWithSessionId(String sessionId) {
        return redisTempalte.get(cache_index, session_domain.concat(sessionId));
    }


   
    public Serializable getAttribute(HttpServletRequest request, HttpServletResponse response, String key) {
        String sessionId = getSessionId(request, response);
        logger.info("sessionId is {}", sessionId);
        String session_json = redisGetWithSessionId(sessionId);
        if (null != session_json) {
            RedisSession session = JSON.parseObject(session_json, RedisSession.class);
            /* modify by wanglei session 延长 */
            this.syncSessionAccessTimeToCache(sessionId, session);
            return (Serializable) session.getData().get(key);
        }
        return null;
    }


   
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String sessionId = getSessionId(request, response);
        if (redisTempalte.exists(cache_index, session_domain.concat(sessionId))) {
            redisTempalte.del(cache_index, session_domain.concat(sessionId));
        }
        CookieUtils.deleteCookie(request, response, cookie_token);
    }

   
    public void casLogout(String sessionId) {
        if (redisTempalte.exists(cache_index, session_domain.concat(sessionId))) {
            redisTempalte.del(cache_index, session_domain.concat(sessionId));
        }
    }

   
    public String getSessionId(HttpServletRequest request, HttpServletResponse response) {
        String ckSessionId = CookieUtils.getCookieValue(request, cookie_token);
        if (!StringUtil.isNullString(ckSessionId)) {
            return ckSessionId;
        }

        // 生成一个
        ckSessionId = UUID.randomUUID().toString().replaceAll("-", "");
        CookieUtils.setCookie(true, request, response, cookie_token, ckSessionId); // httpOnly true
        return ckSessionId;
    }


    private void syncSessionAccessTimeToCache(String sessionId, RedisSession session) {
        if (session == null)
            return;

        Long iter = System.currentTimeMillis() - session.getLastAccessedTime();
        dodgyCode(sessionId, session, iter);
    }

    private void dodgyCode(String sessionId, RedisSession session, Long iter) {
        Long i = expirationUpdateInterval * 60000L;
        if (iter.compareTo(i) >= 0) {// 更新缓存 session 时间 session延长
            session.setLastAccessedTime(System.currentTimeMillis());
            this.redisSetWithDomain(sessionId, JSON.toJSONString(session), expiry * 60);
        }
    }

    /**
     * session 延长
     *
     */
   
    @Deprecated
    public void syncSessionAccessTimeToCache(HttpServletRequest request, HttpServletResponse response, String sessionId) {
        String session_json = this.redisGetWithSessionId(sessionId);
        RedisSession session = JSON.parseObject(session_json, RedisSession.class);
        if (session == null)
            return;

        Long iter = System.currentTimeMillis() - session.getLastAccessedTime();
        dodgyCode(sessionId, session, iter);
    }

    public void setExpiry(int expiry) {
        this.expiry = expiry;
    }

    public void setExpirationUpdateInterval(int expirationUpdateInterval) {
        this.expirationUpdateInterval = expirationUpdateInterval;
    }

    public void setSession_domain(String session_domain) {
        this.session_domain = session_domain;
    }

    public void setCookie_token(String cookie_token) {
        this.cookie_token = cookie_token;
    }

   
    public Serializable getUserByToken(String sessionId) {
        String session_json = redisGetWithSessionId(sessionId);
        RedisSession session = JSON.parseObject(session_json, RedisSession.class);
        if (null != session) {
            Serializable serializable = (Serializable) session.getData().get(ConstantsUtil.LOGIN_USER_SESSION_KEY);
            this.syncSessionAccessTimeToCache(sessionId, session);
            return serializable;
        }
        return null;
    }

}
