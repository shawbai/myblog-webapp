package com.shaw.myblog.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shaw.myblog.common.constant.ConstantsUtil;
import com.shaw.myblog.common.redis.RedisTempalte;

/**
 * Copyright (C), 2012-2014, 苏州海云融通有限公司
 * Author:   Wanglei
 * Date:    2016-1-4下午4:00:46
 * Description: 验证码设置到缓存
 * <author>      <time>      <version>    <desc>
 * 修改人姓名        修改时间             版本号              描述
 */
@Component
public class CaptchaCacheUtil {

	public static final Integer index_captch = ConstantsUtil.REDIS_VERIFYCODE_INDEX;
	private static final int sms_seconds = 60 * 3;
	private static final int capth_seconds = 60 * 5;//5分钟
	private static final String IMAGE_TK="IMAGE_TK";

	@Autowired
	private RedisTempalte redisTempalte;
	
	
	/**
	 * @description： 图片验证码
	 * @author ：wanglei
	 * 2016-1-12下午3:52:30
	 * @param token
	 * @param code
	 */
	public void setImageCodeToCache(String token,String code){
		redisTempalte.set(index_captch, IMAGE_TK+":"+token,code,capth_seconds);
	}
	
	//图片验证码
	public String getImageCodeFromCache(String token){
		return redisTempalte.get(index_captch, IMAGE_TK+":"+token);
	}
	
	
	/**
	 * @description： 验证码设置到缓存
	 * @author ：wanglei
	 * 2016-1-4下午3:58:46
	 * @param telephone
	 * @param code
	 */
	public void setSMSCodeToCache(String telephone, String code) {
		redisTempalte.set(index_captch, telephone, code, sms_seconds);
	}

	/**
	 * @description： 验证码校验
	 * @author ：wanglei
	 * 2016-1-4下午3:59:03
	 * @param telephone
	 * @param validateCode
	 * @return
	 */
	public boolean vertifyCodeFromCache(String telephone,String validateCode) {
		String code = redisTempalte.get(index_captch, telephone);
		return !StringUtil.isNullString(code) && validateCode.equals(code);
	}

}
