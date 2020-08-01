package com.bjtu.testbox.config.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import  org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import  springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import  springfox.documentation.swagger2.annotations.EnableSwagger2;
//swagger2的配置文件，在项目的启动类的同级文件建立
//@Configuration
//@EnableSwagger2
//是否开启swagger，正式环境一般是需要关闭的（避免不必要的漏洞暴露！），
// 可根据springboot的多环境配置进行设置
//@ConditionalOnProperty(name = "swagger.enable",  havingValue = "true")
public class Swagger2Configuration {
     // swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等

    //api接口包扫描路径
    public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.bjtu.testbox.controller";

     @Bean
     public Docket createRestApi() {
          return new Docket(DocumentationType.SWAGGER_2)
                   .apiInfo(apiInfo())
                   .select()
                   // 为当前包路径
                   .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                   .paths(PathSelectors.any())
                   .build();
     }
     // 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
     private ApiInfo apiInfo() {
          return new ApiInfoBuilder()
                   // 页面标题
                   .title("Spring Boot + Swagger2 构建RESTful API")
                   // 创建人信息
                   // .contact(new Contact("ztz",  "Null",  "874102849@qq.com"))
                   .description("试验箱后台管理系统 API 接口文档") // 设置文档的描述
                   // 版本号
                   .version("1.0")
                   // 描述
                   .build();
     }
}