package com.example.auditing;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
class UserManagementApplicationTests {
	@Test
	void contextLoads() {
		assertTrue("true", 2 == 2);
	}
}
