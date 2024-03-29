package com.github.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author Dooby Kim
 * @Date 2024/3/7 下午10:01
 * @Version 1.0
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    // http://localhost:8090/doc.html
    // http://localhost:8091/doc.html  dev
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)  // 指定api类型为swagger2
                .apiInfo(apiInfo())                 // 用于定义api文档汇总信息
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.github.controller"))   // 指定controller包
                .paths(PathSelectors.any())         // 所有controller
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("automatically-publish-articles-on-wechat-ai 接口 api")        // 文档页标题
                .contact(new Contact("jrh,wjy",
                        "https://www.github.com",
                        "#"))        // 联系人信息
                .description("api文档")  // 详细信息
                .version("1.0.0")   // 文档版本号
                .build();
    }
}
