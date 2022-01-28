package com.example.gao;

import javax.servlet.MultipartConfigElement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.unit.DataSize;

@EnableScheduling
@SpringBootApplication
public class GaoApplication {

	public static void main(String[] args) {
//            Avoir les données du système SE
             System.out.println("System nom: " + System.getProperty("os.name"));
             System.out.println("System property: " + System.getProperty("user.dir"));
             System.out.println("System user: " + System.getProperty("user.name"));
             SpringApplication.run(GaoApplication.class, args);
	}
@Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofKilobytes(512));
        factory.setMaxRequestSize(DataSize.ofKilobytes(512));
        return factory.createMultipartConfig();
    }
}
