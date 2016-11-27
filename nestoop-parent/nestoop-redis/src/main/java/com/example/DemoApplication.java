package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{

	@Autowired
	private RedisService redisService;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		redisService.addSet("admin", "maxwit");
		Set<String> valueSet = redisService.getSet("admin");
        for (String value : valueSet) {
            System.out.println("[value] " + value);
        }
    }
}
