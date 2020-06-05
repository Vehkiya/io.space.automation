import io.restassured.http.Method;
import io.space.automation.api.core.ApiSpecification;
import io.space.automation.api.core.config.ApiConfiguration;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import testdata.Endpoints;
import testdata.PostmanEchoGetResposne;
import testdata.PostmanEchoService;
import testdata.TestConfig;

import java.util.Arrays;
import java.util.List;

@Log4j2
@SpringBootTest
@ContextConfiguration(classes = {ApiConfiguration.class, TestConfig.class})
@ActiveProfiles("test")
public class ApiServiceTest {

    @Autowired
    private PostmanEchoService postmanEchoService;

    @Test
    void verifyProperties() {
        final String foo1 = "bar1";
        final String foo2 = "bar2";
        ApiSpecification foo1Spec = new ApiSpecification("query-param", "foo1", foo1);
        ApiSpecification foo2Spec = new ApiSpecification("query-param", "foo2", foo2);
        List<ApiSpecification> specificationList = Arrays.asList(foo1Spec, foo2Spec);
        PostmanEchoGetResposne response = postmanEchoService.performRequest(Endpoints.RESPONSE_HEADERS, Method.GET, specificationList)
                .as(PostmanEchoGetResposne.class);
        Assertions.assertEquals(response.getFoo1(), foo1);
        Assertions.assertEquals(response.getFoo2(), foo2);
    }
}