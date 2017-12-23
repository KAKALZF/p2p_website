package com.xmg.springboot.website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import com.xmg.springboot.p2p.CoreAppConfig;

/**
 * 前台应用总控制器
 * @author 1
 *
 */
@SpringBootApplication
@Import(CoreAppConfig.class)
@PropertySource("classpath:application-website.properties")
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
