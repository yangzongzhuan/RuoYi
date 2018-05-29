package com.ruoyi.framework.config;

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
 * Swagger2的接口配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //.apis(RequestHandlerSelectors.basePackage("com.ruoyi.project.*.*.controller"))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("系统接口列表")
                .description("API接口测试平台\",\n" +
                        "                \"提供后台所有Restful接口\",")
                .termsOfServiceUrl("http://localhost/swagger-ui.html")
                .contact(new Contact("RuoYi", "https://gitee.com/y_project/RuoYi", "1403014932@qq.com"))
                .version("1.1.0")
                .build();
    }
}
