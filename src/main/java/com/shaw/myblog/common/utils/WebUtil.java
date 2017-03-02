package com.shaw.myblog.common.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.List;

public class WebUtil {


	// 通过前端的负载均衡服务器时，请求对象中的IP会变成负载均衡服务器的IP，因此需要特殊处理，下同。
	public static final String X_REAL_IP = "X-Real-IP";
	public static final String X_FORWARDED_FOR = "X-Forwarded-For";
	
	
	/**
	 * 获取请求来源的 IP 地址。由于在集群环境下，请求通过前端的负 载 均 衡 器 再 传 给 后 端 集 群 的 某 个 具 体 服 务 节 点 。
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String remoteIp = request.getHeader(X_REAL_IP); // nginx反向代理
		if (StringUtil.hasText(remoteIp)) {
			return remoteIp;
		} else {
			remoteIp = request.getHeader(X_FORWARDED_FOR);// apache反射代理
			if (StringUtil.hasText(remoteIp)) {
				String[] ips = remoteIp.split(",");
				for (String ip : ips) {
					if (!"null".equalsIgnoreCase(ip)) {
						return ip;
					}
				}
			}
			return request.getRemoteAddr();
		}
	}
	

    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }
	public static HttpSession getSession() {
		HttpSession session = getRequest().getSession();
		return session;

	}
	
	public static HttpServletResponse getResponse() {
		HttpServletResponse resp = ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();
		return resp;
	}
	   
    public static List<MultipartFile> getFiles(HttpServletRequest request,String fileNme)  throws Exception{
        if(request instanceof MultipartHttpServletRequest){
            MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest) request;
            return multipartHttpServletRequest.getFiles(fileNme);
        }else{
            throw new Exception("不是上传模式，请检查form表头设置是否正确！");
        }

    }


    public static MultipartFile getFile(HttpServletRequest request,String fileName)  throws Exception{
        if(request instanceof MultipartHttpServletRequest){

            MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest) request;
            return multipartHttpServletRequest.getFile(fileName);

        }else{
            throw new Exception("不是上传模式，请检查form表头设置是否正确！");
        }
    }
    
    
	public void setCookies(String key, String value, HttpServletResponse response) {
		response.setHeader("P3P","CP=\"NON DSP COR CURa ADMa DEVa TAIa PSAa PSDa IVAa IVDa CONa HISa TELa OTPa OUR UNRa IND UNI COM NAV INT DEM CNT PRE LOC\"");
		response.setHeader("Access-Control-Allow-Origin", "*");
		Cookie cktemp = new Cookie(key, value);
		// cktemp.setDomain(".haiyunx.com");
		cktemp.setPath("/haiyunfarmshop/");
		cktemp.setMaxAge(60 * 60 * 24);
		response.addCookie(cktemp);

	}

	public String getCookies(String key, HttpServletRequest request) {
		String cookieString = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie c = cookies[i];
				if (c.getName().equalsIgnoreCase(key)) {
					cookieString = c.getValue();
				}
			}
		}
		return cookieString;
	}
	
	
	 /**
     * 设置下载文件中文件的名称
     *
     * @param filename
     * @param request
     * @return
     */
    public static String encodeFilename(String filename, HttpServletRequest request) {
        /**
         * 获取客户端浏览器和操作系统信息
         * 在IE浏览器中得到的是：User-Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Maxthon; Alexa Toolbar)
         * 在Firefox中得到的是：User-Agent=Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.7.10) Gecko/20050717 Firefox/1.0.6
         */
        String agent = request.getHeader("USER-AGENT");
        try {
            if ((agent != null) && (-1 != agent.indexOf("MSIE"))) {
                String newFileName = URLEncoder.encode(filename, "UTF-8");
                newFileName = StringUtils.replace(newFileName, "+", "%20");
                if (newFileName.length() > 150) {
                    newFileName = new String(filename.getBytes("GB2312"), "ISO8859-1");
                    newFileName = StringUtils.replace(newFileName, " ", "%20");
                }
                return newFileName;
            }
            if ((agent != null) && (-1 != agent.indexOf("Mozilla")))
                return MimeUtility.encodeText(filename, "UTF-8", "B");

            return filename;
        } catch (Exception ex) {
            return filename;
        }
    }

	
}