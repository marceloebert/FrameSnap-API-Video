package com.fiap.framesnap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.fiap.framesnap.infrastructure.video.configuration.RedisProperties;

@SpringBootApplication(scanBasePackages = "com.fiap.framesnap")
@EnableConfigurationProperties(RedisProperties.class)
@ComponentScan(basePackages = "com.fiap.framesnap")
public class FrameSnapApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrameSnapApplication.class, args);
	}

}
