package com.shaw.myblog.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shaw.myblog.common.redis.RedisTempalte;

@Controller
@RequestMapping("/index")
public class IndexController {
//	@Resource默认按 byName自动注入
	@Autowired
	private RedisTempalte redisTempalte;

	
	@RequestMapping("/toIndex.do")
	public String toIndex(HttpServletRequest request,Model model){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:dd");
		String nowtime = sdf.format(new Date());
		String lastTime = redisTempalte.get(0, "lasttime");
		redisTempalte.set(0, "lasttime", nowtime);
		model.addAttribute("lasttime", lastTime);
		model.addAttribute("nowtime", nowtime);
		return "index";
	}
	
	

}
