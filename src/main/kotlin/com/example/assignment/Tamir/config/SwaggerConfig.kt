package com.example.assignment.Tamir.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.regex("/.*"))
        .build()
        .apiInfo(apiEndsPointsInfo())

    private fun apiEndsPointsInfo() = ApiInfoBuilder()
        .title("Games shop with account management and purchase system")
        .description("CRUD Rest API in Spring boot(Docker, Swagger, Spring Jpa, PostgreSql, JUnit)")
        .build()

}
