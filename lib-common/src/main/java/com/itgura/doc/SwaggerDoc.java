package com.itgura.doc;

import com.itgura.config.SwaggerConfig;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
//@EnableSwagger2
public class SwaggerDoc {
	
	@Autowired
	private SwaggerConfig swaggerConfigProperties;


//	@Bean
//    public Docket eDesignApi(TypeResolver typeResolver) {
//        return new Docket(DocumentationType.SWAGGER_2)
//        		.apiInfo(apiInfo(swaggerConfigProperties))
//        		.additionalModels(typeResolver.resolve(AddressResponseDto.class))
//        		.globalOperationParameters(defaultParamaters(swaggerConfigProperties))
//        		.groupName("Core Service")
//        		.useDefaultResponseMessages(false)
//        		.enable(Boolean.valueOf(swaggerConfigProperties.getEnabled())) //
//        		.select()
//        		.apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build()
//                .pathMapping("/")
//                .directModelSubstitute(LocalDate.class, String.class)
//                .genericModelSubstitutes(ResponseEntity.class)
//                .useDefaultResponseMessages(Boolean.valueOf(swaggerConfigProperties.getUseDefaultResponseMessages())) //
//                .enableUrlTemplating(Boolean.valueOf(swaggerConfigProperties.getEnableUrlTemplating()))
//                ; 
//    }
//
//
//	
//	private ApiInfo apiInfo(SwaggerConfig swaggerConfigProperties) {
//        return new ApiInfoBuilder()
//        		.title(swaggerConfigProperties.getTitle()) //
//        		.description(swaggerConfigProperties.getDescription()) //
//                .version(swaggerConfigProperties.getApiVersion()) //
//                .build();
//    }
//	
//	private List<Parameter> defaultParamaters(SwaggerConfig swaggerConfigProperties) {
//        ParameterBuilder parameterBuilder = new ParameterBuilder();
//        parameterBuilder
//        .name("Authorization")
//        .modelRef(new ModelRef("String"))
//        .parameterType("header")
//        .defaultValue(swaggerConfigProperties.getDefaultAuthorizationHeader()) 
//        .required(false)
//        .build();
//        
//        ParameterBuilder apiHeaderBuilder = new ParameterBuilder();
//        apiHeaderBuilder.name("api-key")
//        .modelRef(new ModelRef("String"))
//        .parameterType("header")
//        .defaultValue("")
//        .required(false)
//        .build();
//        java.util.List<Parameter> parameters = new ArrayList<>();
//        parameters.add(parameterBuilder.build());
//        parameters.add(apiHeaderBuilder.build());
//        return parameters;
//    }

	@Bean
	public OpenAPI inovaOpenAPI() {
		// Define Info
		Info info = new Info().title("Project and Resource Management API Documentation")
				.version("1.0")
				.description("This is the complete API documentation of this particular microservice that exposes Project and Resource Management System");

		SecurityScheme securityScheme = new SecurityScheme()
				.type(SecurityScheme.Type.HTTP)
				.scheme("bearer")
				.bearerFormat("JWT");

		Components components = new Components()
				.addSecuritySchemes("bearerAuth", securityScheme);

		SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

		return new OpenAPI().info(info)
				.components(components)
				.addSecurityItem(securityRequirement);
	}
}
