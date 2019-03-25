package cn.sqwsy.health365interface;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("cn.sqwsy.health365interface.dao.mapper")
public class Health365InterfaceApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(Health365InterfaceApplication.class, args);
	}
}