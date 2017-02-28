package com.shaw.myblog.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shaw.myblog.common.pojo.BaseResponse;
import com.shaw.myblog.common.session.SessionProvider;
import com.shaw.myblog.common.utils.CaptchaCacheUtil;
import com.shaw.myblog.common.utils.SecurityCode;
import com.shaw.myblog.common.utils.SecurityImage;
import com.shaw.myblog.common.utils.StringUtil;

@Controller
@RequestMapping("/common")
public class CommonController {

    private static Logger logger = LoggerFactory.getLogger(CommonController.class);
    @Autowired
    private CaptchaCacheUtil captchaCacheUtil;
    @Autowired
    private SessionProvider sessionProvider;


    @RequestMapping(value = "/captcha")
    @ResponseBody
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ByteArrayInputStream imageStream;
        String capcode = SecurityCode.getSecurityCode();

        /* 关闭流之前写给浏览器token */
        String sessionId = sessionProvider.getSessionId(request, response);
        captchaCacheUtil.setImageCodeToCache(sessionId, capcode);

        imageStream = SecurityImage.getImageAsInputStream(capcode);
        ServletOutputStream stream = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = imageStream.read(buffer)) > 0) {
            stream.write(buffer, 0, len);
        }
        imageStream.close();
        response.setContentType("image/jpeg");
        stream.flush();
        stream.close();
    }

    /**
     * @param captch
     * @param request
     * @throws
     * @说明：验证码验证
     * @return: Object
     * @author: xuefeng
     * @2015年7月23日 下午3:58:18
     */
    @RequestMapping(value = "/vldCaptch")
    @ResponseBody
    public Object validateSecurityCode(String captch, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtil.isNullString(captch) || captch.length() < 4) {
            return BaseResponse.paramError();
        }

        String cacheCode = captchaCacheUtil.getImageCodeFromCache(sessionProvider.getSessionId(request, response));
        if (!StringUtil.isNullString(cacheCode)) {
            if (cacheCode.equals(captch)) {
                return BaseResponse.success();
            }
        }
        return BaseResponse.error();
    }

}
