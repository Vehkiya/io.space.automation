package io.space.automation.api.core.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.space.automation.api.core.ApiService;
import io.space.automation.api.core.ApiSpecification;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties("config.api")
public class ApiProperties {

    private final Map<String, List<ApiSpecification>> services;

    public RequestSpecification getBaseSpec(String serviceName) {
        RequestSpecification baseRequest = new RequestSpecBuilder().build();
        List<ApiSpecification> apiSpecifications = services.getOrDefault(serviceName, Collections.emptyList());
        return ApiService.updateSpecs(baseRequest, apiSpecifications);
    }
}
