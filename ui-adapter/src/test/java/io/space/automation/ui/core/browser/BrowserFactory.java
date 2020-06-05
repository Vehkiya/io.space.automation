package io.space.automation.ui.core.browser;

import io.space.automation.ui.core.browser.impl.ChromeBrowser;
import io.space.automation.ui.core.browser.impl.FirefoxBrowser;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class BrowserFactory {

    @Autowired
    private BrowserProperties browserProperties;

    public Browser getBrowser() {
        if (browserProperties.getName().equalsIgnoreCase("chrome")) {
            return chrome();
        } else if (browserProperties.getName().equalsIgnoreCase("firefox")) {
            return firefox();
        } else {
            return chrome();
        }
    }

    private Browser chrome() {
        return new ChromeBrowser(getDriver())
                .setOptions(new ChromeOptions().addArguments(browserProperties.getArgs()));
    }

    private Browser firefox() {
        return new FirefoxBrowser(getDriver())
                .setOptions(new FirefoxOptions().addArguments(browserProperties.getArgs()));
    }

    private File getDriver() {
        return new File(browserProperties.getDriverLocation());
    }
}
