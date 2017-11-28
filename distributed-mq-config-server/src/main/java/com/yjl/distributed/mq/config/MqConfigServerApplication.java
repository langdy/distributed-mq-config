package com.yjl.distributed.mq.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * 
 * @author zhaoyc@1109
 * @version 创建时间：2017年9月1日 上午11:44:58
 */
@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.yjl.distributed")
public class MqConfigServerApplication extends SpringBootServletInitializer {


	public static void main(String[] args) {
		SpringApplication.run(MqConfigServerApplication.class, args);
	}

	/**
	 * Application类中被重写的configure方法就是使用嵌入式的Spring上下文注册应用的地方。 在更为正式的场景之中，这个方法可能会用来注册Spring Java配置类，它会定义应用中所有
	 * controller和服务的bean。
	 *
	 * @param application
	 * @see <a href="http://www.infoq.com/cn/articles/microframeworks1-spring-boot">info</a>
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MqConfigServerApplication.class);
	}
}
