package com.tj_JavaEE;

import com.tj_JavaEE.controller.UserController;
import com.tj_JavaEE.entity.User;
import com.tj_JavaEE.mapper.UserMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest
class AssignmentBApplicationTests {

	@Autowired
	private UserController userController;

	@Autowired
	private UserMapper userMapper;

	@Test
	void controllerTest() {
		System.out.println(userController.list());
	}

	@Test
	void mapperTest() {
		System.out.println(userMapper.list());
	}


	@Test
	public void testGenJwt(){

		Map<String,Object> claims = new HashMap<>();
		claims.put("account",1);
		claims.put("passwordHash","Bob");

		String jwt=Jwts.builder()
				.signWith(SignatureAlgorithm.HS256,"luntan")
				.setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis()+60*60*1000))
				.compact();

		System.out.println(jwt);
	}

	@Test
	public void testParseJwt(){
		Claims claims =Jwts.parser()
				.setSigningKey("luntan")
				.parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MzQ0NTY0OTAsImFjY291bnQiOjEsInBhc3N3b3JkSGFzaCI6IkJvYiJ9.mu5zZi-D64HyAI9_TyODhB4wN6ANpoXNNosUquASdkE")
				.getBody();

		System.out.println(claims);
	}
}
