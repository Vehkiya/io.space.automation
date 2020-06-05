package io.space.automation.api.core;

import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.http.entity.ContentType;

import java.io.File;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum SpecificationParam {
    BASE_URI("base-uri") {
        @Override
        public RequestSpecification getRequest(RequestSpecification requestSpecification, Map<String, Object> params) {
            return requestSpecification.baseUri(params.get("uri").toString());
        }
    },
    CONTENT_TYPE("content-type") {
        @Override
        public RequestSpecification getRequest(RequestSpecification requestSpecification, Map<String, Object> params) {
            return requestSpecification.contentType(params.get("content-type").toString());
        }
    },
    HEADERS("headers") {
        @Override
        public RequestSpecification getRequest(RequestSpecification requestSpecification, Map<String, Object> params) {
            return requestSpecification.headers(params);
        }
    },
    PARAMS("param") {
        @Override
        public RequestSpecification getRequest(RequestSpecification requestSpecification, Map<String, Object> params) {
            return requestSpecification.params(params);
        }
    },
    PREDEFINED_BODY("predefined-body") {
        @Override
        public RequestSpecification getRequest(RequestSpecification requestSpecification, Map<String, Object> params) {
            return requestSpecification.body(params.get("body"));
        }
    },
    BODY("body") {
        @Override
        public RequestSpecification getRequest(RequestSpecification requestSpecification, Map<String, Object> params) {
            return requestSpecification.body(params);
        }
    },
    QUERY_PARAM("query-param") {
        @Override
        public RequestSpecification getRequest(RequestSpecification requestSpecification, Map<String, Object> params) {
            return requestSpecification.queryParams(params);
        }
    },
    PATH_PARAM("path-param") {
        @Override
        public RequestSpecification getRequest(RequestSpecification requestSpecification, Map<String, Object> params) {
            return requestSpecification.pathParams(params);
        }
    },
    AUTHENTICATION("auth") {
        @Override
        public RequestSpecification getRequest(RequestSpecification requestSpecification, Map<String, Object> params) {
            Map.Entry<String, Object> entry = params.entrySet().iterator().next();
            return requestSpecification.auth().preemptive().basic(entry.getKey(), (String) entry.getValue());
        }
    },

    FORM_DATA("form-data") {
        @Override
        public RequestSpecification getRequest(RequestSpecification requestSpecification, Map<String, Object> params) {
            return requestSpecification.multiPart((File) params.get("file"))
                    .contentType(ContentType.MULTIPART_FORM_DATA.getMimeType());
        }
    };

    @Getter
    String specName;

    public abstract RequestSpecification getRequest(RequestSpecification requestSpecification, Map<String, Object> params);

    public static SpecificationParam findSpec(String specName) {
        return EnumSet.allOf(SpecificationParam.class)
                .stream()
                .collect(Collectors.toMap(SpecificationParam::getSpecName, e -> e))
                .get(specName);
    }
}
