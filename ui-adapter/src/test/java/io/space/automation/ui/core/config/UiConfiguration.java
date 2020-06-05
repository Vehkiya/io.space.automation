package io.space.automation.ui.core.config;

import io.space.automation.ui.core.browser.BrowserProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(BrowserProperties.class)
@Configuration
@ComponentScan("io.space.automation.ui.core.browser")
public class UiConfiguration {
}
