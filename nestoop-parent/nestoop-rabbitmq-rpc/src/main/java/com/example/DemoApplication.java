package com.example;

import com.example.client.RabbitMQClient;
import com.example.server.RabbitMQServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{

//	@Autowired
//	private RabbitMQClient rabbitMQClient;

	@Autowired
	private RabbitMQServer rabbitMQServer;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
//		rabbitMQClient.call("test*********");

		Thread.sleep(1 * 1000);


	}
}
