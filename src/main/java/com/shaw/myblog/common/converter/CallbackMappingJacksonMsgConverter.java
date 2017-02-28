package com.shaw.myblog.common.converter;

import java.io.IOException;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.shaw.myblog.common.utils.StringUtil;


public class CallbackMappingJacksonMsgConverter extends MappingJackson2HttpMessageConverter {
    // 做jsonp的支持的标识，在请求参数中加该参数
    private String callbackName;

    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
        // 从threadLocal中获取当前的Request对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String callbackParam = request.getParameter(callbackName);
        System.out.println("是否为jsonp"+StringUtil.isNullString(callbackParam));
        if (StringUtil.isNullString(callbackParam)) {
            // 没有找到callback参数，直接返回json数据
            super.writeInternal(object,type, outputMessage);
        } else {
            JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
            try {
				String result = callbackParam + "(" + super.getObjectMapper().writeValueAsString(object) + ");";
                IOUtils.write(result, outputMessage.getBody(), encoding.getJavaName());
            } catch (JsonProcessingException ex) {
                throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
            }
        }

    }

    public String getCallbackName() {
        return callbackName;
    }

    public void setCallbackName(String callbackName) {
        this.callbackName = callbackName;
    }

}