package com.beardream.config;

import com.beardream.Utils.Constants;
import com.beardream.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by soft01 on 2017/5/7.
 */
@EnableWebMvc
@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {

    @Bean
    LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(Constants.LOGIN_URL)
                .excludePathPatterns(Constants.TOKEN_URL)
                .excludePathPatterns(Constants.FILE_URL)
                .excludePathPatterns(Constants.MENU_URL)
                .excludePathPatterns(Constants.PAYNOTIFY_URL);
        super.addInterceptors(registry);
    }


}
