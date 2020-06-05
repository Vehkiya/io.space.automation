package io.space.automation.ui.core.browser.impl;

import io.space.automation.ui.core.browser.Browser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;

import java.io.File;
import java.util.Optional;
import java.util.function.Supplier;

public class FirefoxBrowser extends Browser {
    private FirefoxOptions options;
    private GeckoDriverService geckoDriverService;

    public FirefoxBrowser(File driverExecutable) {
        super(driverExecutable);
    }

    @Override
    protected Supplier<WebDriver> newDriver() {
        return () -> {
            options = Optional.ofNullable(options).orElse(new FirefoxOptions());
            geckoDriverService = Optional.ofNullable(geckoDriverService).orElseGet(driverService());
            return new FirefoxDriver(geckoDriverService, options);
        };
    }

    private Supplier<GeckoDriverService> driverService() {
        return () -> new GeckoDriverService.Builder()
                .usingDriverExecutable(driverExecutable)
                .usingAnyFreePort()
                .build();
    }

    public FirefoxBrowser setOptions(FirefoxOptions options) {
        this.options = options;
        return this;
    }
}
