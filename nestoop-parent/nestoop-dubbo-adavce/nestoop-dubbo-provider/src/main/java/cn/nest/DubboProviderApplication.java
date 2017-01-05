package cn.nest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class DubboProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboProviderApplication.class, args);
		while (true) {
			try {
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
