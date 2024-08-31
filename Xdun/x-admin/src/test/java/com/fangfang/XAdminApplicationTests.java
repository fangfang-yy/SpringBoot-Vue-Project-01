package com.fangfang;

import com.fangfang.sys.entity.User;
import com.fangfang.sys.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class XAdminApplicationTests {
	@Resource
	private UserMapper userMapper;

//	测试mapper
	@Test
	void testMapper() {
		List<User> users = userMapper.selectList(null);
		for (User user : users) {
			System.out.println(user);
		}
	}

}
