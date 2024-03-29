package cn.crecode.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class MyCorsConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
    .allowedOrigins("*") //注意此处必须为前端地址，不能为*
    .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
    .allowCredentials(true).maxAge(3600);
     }
}