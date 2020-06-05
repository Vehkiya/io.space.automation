package testdata;

import io.restassured.specification.RequestSpecification;
import io.space.automation.api.core.ApiService;
import io.space.automation.api.core.config.ApiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostmanEchoService implements ApiService {

    @Autowired
    private ApiProperties apiProperties;

    @Override
    public RequestSpecification baseSpec() {
        return apiProperties.getBaseSpec("postman-echo");
    }
}
