package testdata;

import io.space.automation.api.core.Endpoint;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Endpoints implements Endpoint {
    RESPONSE_HEADERS("/response-headers");

    private final String url;

    @Override
    public String url() {
        return url;
    }
}
