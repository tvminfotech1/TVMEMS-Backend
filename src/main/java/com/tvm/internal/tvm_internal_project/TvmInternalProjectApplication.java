package com.tvm.internal.tvm_internal_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class TvmInternalProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TvmInternalProjectApplication.class, args);
	}

}