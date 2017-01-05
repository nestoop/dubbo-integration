package cn.nest;

import cn.nest.interfaces.IHelloService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class DubboConsumerApplication implements CommandLineRunner {

	ApplicationContext factory=new ClassPathXmlApplicationContext("classpath:spring-dubbo-consumer.xml");

	public static void main(String[] args) {
		SpringApplication.run(DubboConsumerApplication.class, args);
		while (true) {
			try {
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run(String... strings) throws Exception {
		IHelloService iHelloService = (IHelloService) factory.getBean("iHelloService");
		iHelloService.sayHello("botter");
	}
}
