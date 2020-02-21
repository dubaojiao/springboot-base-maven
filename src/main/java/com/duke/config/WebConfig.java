package com.duke.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.duke.config.interceptor.WebHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Web配置适配器扩展
 * Author: duke
 * Date: 2020-02-22
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /** 拦截排除路径 */
    private static String[] excludePathArray = new String[] {
        "/web/user/login"
    };

    private static WebHandlerInterceptor webHandlerInterceptor = new WebHandlerInterceptor();
    /**
     * 注入拦截器bean
     */
    @Bean
    WebHandlerInterceptor webHandlerInterceptor(){
        return  webHandlerInterceptor;
    }
    /**
     * 注册 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webHandlerInterceptor).excludePathPatterns(excludePathArray);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
                .allowCredentials(true).maxAge(3600);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        /*
        1.需要先定义一个convert转换消息的对象；
        2.添加fastjson的配置信息，比如是否要格式化返回的json数据
        3.在convert中添加配置信息
        4.将convert添加到converters中
         */
        //1.定义一个convert转换消息对象
        FastJsonHttpMessageConverter fastConverter=new FastJsonHttpMessageConverter();
        //2.添加fastjson的配置信息，比如：是否要格式化返回json数据
        FastJsonConfig fastJsonConfig=new FastJsonConfig();
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.WriteNullStringAsEmpty,  SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteDateUseDateFormat,  SerializerFeature.WriteMapNullValue);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastConverter);
    }

}