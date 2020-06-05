package io.space.automation.ui.core.browser;

import io.space.automation.ui.core.page.Page;
import lombok.Getter;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class Browser {

    protected File driverExecutable;
    private WebDriver driver;
    private final PageManager pageManager;
    @Getter
    private Page currentPage;

    public Browser(File driverExecutable) {
        this.driverExecutable = driverExecutable;
        this.pageManager = new PageManager(this);
    }


    public WebDriver driver() {
        driver = Optional.ofNullable(driver).orElseGet(newDriver());
        return driver;
    }

    protected abstract Supplier<WebDriver> newDriver();

    public <T extends Page> T getPage(Class<T> pageClass) {
        T page = pageManager.initPage(pageClass);
        if (page.isCurrentPage()) {
            page.scan();
            currentPage = page;
        }
        return page;
    }

    public <T extends Page> T getPage(String name) {
        Class<? extends Page> pageClass = pageManager.getPage(name).orElseThrow(() -> new RuntimeException("Page not found"));
        return (T) getPage(pageClass);
    }

    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }

}
