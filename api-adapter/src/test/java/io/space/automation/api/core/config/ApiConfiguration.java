package io.space.automation.api.core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@EnableConfigurationProperties(
        ApiProperties.class
)
@ComponentScan("io.space.automation.api.core")
public class ApiConfiguration {
}
