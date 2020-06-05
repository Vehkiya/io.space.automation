package io.space.automation.ui.core.browser;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("config.ui.browser")
public class BrowserProperties {

    private String name;

    private String driverLocation;

    private String[] args;
}
