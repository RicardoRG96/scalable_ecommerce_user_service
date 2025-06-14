package com.ricardo.scalable.ecommerce.platform.userService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableFeignClients
@SpringBootApplication
@EntityScan({
	"com.ricardo.scalable.ecommerce.platform.libs_common.entities", 
	"com.ricardo.scalable.ecommerce.platform.userService.model.entities"
})
@EnableScheduling
public class UserServiceApplication {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
