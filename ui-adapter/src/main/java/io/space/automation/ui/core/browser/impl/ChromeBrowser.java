package io.space.automation.ui.core.browser.impl;

import io.space.automation.ui.core.browser.Browser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.Optional;
import java.util.function.Supplier;

public class ChromeBrowser extends Browser {
    private ChromeOptions options;
    private ChromeDriverService chromeDriverService;

    public ChromeBrowser(File driverExecutable) {
        super(driverExecutable);
    }

    @Override
    protected Supplier<WebDriver> newDriver() {
        return () -> {
            options = Optional.ofNullable(options).orElse(new ChromeOptions());
            chromeDriverService = Optional.ofNullable(chromeDriverService).orElseGet(driverService());
            return new ChromeDriver(chromeDriverService, options);
        };
    }

    private Supplier<ChromeDriverService> driverService() {
        return () -> new ChromeDriverService.Builder()
                .usingDriverExecutable(driverExecutable)
                .usingAnyFreePort()
                .build();
    }

    public ChromeBrowser setOptions(ChromeOptions options) {
        this.options = options;
        return this;
    }
}
