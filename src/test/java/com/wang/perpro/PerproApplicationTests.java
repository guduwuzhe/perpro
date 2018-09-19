package com.wang.perpro;

import com.wang.perpro.service.cache.CacheValueManager;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PerproApplicationTests {

	@Test
	public void contextLoads() {
	}


	@Autowired
	private CacheValueManager<Integer> cacheValueManager;
	@Test
	public void test1(){

		cacheValueManager.set("ss",1,10,TimeUnit.SECONDS);
		//System.out.println(cacheValueManager.get("ss"));
	}
}
