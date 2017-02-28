package com.shaw.test;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shaw.myblog.common.redis.RedisTempalte;

@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试  
@ContextConfiguration({"/spring/spring-mvc.xml","/spring/spring-context.xml"})
public class TestJedis extends AbstractJUnit4SpringContextTests{
	
	@Resource
	private RedisTempalte redisTempalte;
	
//	@Resource
//	private UserService userService;
//	
//	@Test
//	public void testJedis() {
//		User user = userService.getUserById(1);
//		redisTempalte.hset(1, "hashKey", "testKey", JSON.toJSONString(user));
//		redisTempalte.set(1, "testKey", "content"); 
//		redisTempalte.expire(1, "testKey",10);//10s 后删除
//		User jUser = JSON.parseObject(redisTempalte.hget(1, "my","test"), User.class);
//		System.out.println("testKey:"+redisTempalte.get(1, "test"));
//		System.out.println("hashKey:"+jUser.getUserName());
//	}

}
