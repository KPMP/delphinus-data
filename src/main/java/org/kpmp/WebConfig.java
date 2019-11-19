package org.kpmp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		CorsRegistration corsRegistration = registry.addMapping("/**");
		corsRegistration.allowedOrigins("*");
		corsRegistration.allowedMethods("GET", "POST");
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
