package com.example;

import com.example.dao.UserDao;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;

@SpringBootApplication
@MapperScan("com.example.model.mapper")
public class DemoApplication implements CommandLineRunner{

	@Resource
	private UserDao userDao;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userDao.getUser("admin");
	}
}
