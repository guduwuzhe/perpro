package com.wang.perpro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2//此注解用于启动swagger
@SpringBootApplication
public class PerproApplication {

	public static void main(String[] args) {
		SpringApplication.run(PerproApplication.class, args);
	}
}
