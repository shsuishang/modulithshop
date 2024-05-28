package com.suisung.shopsuite.common.config;

import cn.hutool.core.util.StrUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Swagger配置
 *
 * @author Xinze
 * @since 2018-02-22 11:29:05
 */
@EnableOpenApi
@Configuration
public class SwaggerConfig {
    @Resource
    private ConfigProperties config;

    @Bean
    public Docket createRestApi() {
        Docket docket = new Docket(DocumentationType.OAS_30);
        if (StrUtil.isNotBlank(config.getSwaggerHost())) {
            docket.host(config.getSwaggerHost());
        }
        return docket
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(config.getSwaggerBasePackage()))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(config.getSwaggerTitle())
                .description(config.getSwaggerDescription())
                .version(config.getSwaggerVersion())
                .termsOfServiceUrl("")
                .build();
    }

    private List<SecurityScheme> securitySchemes() {
        return Collections.singletonList(
                new ApiKey("Authorization", "Authorization", "header")
        );
    }

    private List<SecurityContext> securityContexts() {
        AuthorizationScope[] scopes = {new AuthorizationScope("global", "accessEverything")};
        List<SecurityReference> references = Collections.singletonList(
                new SecurityReference("Authorization", scopes)
        );
        return Collections.singletonList(SecurityContext.builder()
                .securityReferences(references)
                .build());
    }

}
