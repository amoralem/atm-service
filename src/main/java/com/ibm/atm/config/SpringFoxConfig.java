package com.ibm.atm.config;
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

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
	
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiEndPointsInfo())
                .useDefaultResponseMessages(false)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.ibm.atm.controller"))
                    .paths(PathSelectors.ant("/api/v1/**"))
                .build();
    }

    
    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Profile REST API")
            .description("Api para ubicar Cajeros Automáticos de Citibanamex")
            .contact(new Contact("Angel Morales", "", ""))
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .version("1.0.0")
            .build();
    }
    

}