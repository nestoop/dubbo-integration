package cn.nest;

import cn.nest.entity.EntityBean;
import cn.nest.until.AnnotationUnitl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		EntityBean entityBean = new EntityBean();
		entityBean.setVisaCode("TYUI6788");
		entityBean.setVisaName("98999999");
		System.out.println(AnnotationUnitl.bean2String(entityBean));
		SpringApplication.run(DemoApplication.class, args);
	}
}
