package io.space.automation.api.core;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ApiService {

    RequestSpecification baseSpec();

    default Response performRequest(Endpoint endpoint, Method method, List<ApiSpecification> specificationList) {
        return performRequest(endpoint.url(), method, specificationList);
    }

    default Response performRequest(String url, Method method, List<ApiSpecification> specificationList) {
        RequestSpecification requestSpecification = updateSpecs(baseSpec(), specificationList);
        return RestAssured.given()
                .spec(requestSpecification)
                .request(method, url)
                .andReturn();
    }


    static RequestSpecification updateSpecs(RequestSpecification baseRequest, List<ApiSpecification> apiSpecifications) {
        Map<String, Map<String, Object>> paramMap = apiSpecifications
                .stream()
                .collect(Collectors.groupingBy(ApiSpecification::getSpecification,
                        Collectors.toMap(ApiSpecification::getKey, ApiSpecification::getValue)));
        paramMap.forEach((spec, param) -> SpecificationParam.findSpec(spec).getRequest(baseRequest, param));
        return baseRequest;
    }
}
