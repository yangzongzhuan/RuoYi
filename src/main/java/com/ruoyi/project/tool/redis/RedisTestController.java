package com.ruoyi.project.tool.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 测试类
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/redis")
public class RedisTestController {
	
	@Autowired
	private RedisUtils redisUtils;
	
	@GetMapping("/test")
    public void findUser() {
		Integer id = 123456;
        
        redisUtils.set("121233", id);
        System.out.println(redisUtils.get("121233"));
    }

   
}