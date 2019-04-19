package com.ningmeng.vueblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@EnableCaching
@SpringBootApplication
@MapperScan("com.ningmeng.vueblog.mapper")
public class VueBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(VueBlogApplication.class, args);
    }


//    @Bean
//    @Primary
//    public ObjectMapper myObjectMapper(){
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        return objectMapper;
//    }


}
