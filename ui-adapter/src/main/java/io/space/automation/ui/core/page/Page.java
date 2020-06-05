package io.space.automation.ui.core.page;

import io.space.automation.ui.core.browser.Browser;
import io.space.automation.ui.core.page.scanner.PageDescriptor;
import io.space.automation.util.reflection.Reflections;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import io.space.automation.ui.core.page.scanner.Locator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@RequiredArgsConstructor
public abstract class Page {

    private final Browser browser;

    private String url;

    public String url() {
        url = Optional.ofNullable(url).orElseGet(urlSupplier());
        return url;
    }

    private Supplier<String> urlSupplier() {
        return () -> getUrl(this.getClass());
    }

    private String getUrl(Class<?> clazz) {
        return Reflections.getAnnotation(clazz, PageDescriptor.class)
                .map(PageDescriptor::url)
                .orElse(null);
    }

    public String fullUrl() {
        return getUrlForHierarchy(this.getClass());
    }

    private String getUrlForHierarchy(Class<?> clazz) {
        List<String> urlParts = new ArrayList<>();
        while (clazz.getSuperclass() != null) {
            String url = getUrl(clazz);
            if (url != null) {
                urlParts.add(url);
            }
            clazz = clazz.getSuperclass();
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = urlParts.size() - 1; i >= 0; i--) {
            stringBuilder.append(urlParts.get(i));
        }
        return stringBuilder.toString();
    }

    public Boolean isCurrentPage() {
        return browser.driver().getCurrentUrl().toLowerCase().contains(url().toLowerCase());
    }

    public Optional<WebElement> getElementByName(String name) {
        return Reflections.getFieldsWithAnnotation(getClass(), Locator.class)
                .stream()
                .filter(field -> name.equalsIgnoreCase(Reflections.getAnnotation(field, Locator.class).map(Locator::name).get()))
                .findFirst()
                .map(field -> ((WebElement) Reflections.getFieldValue(field, this)));
    }

    public void open() {
        browser.driver().get(fullUrl());
        scan();
    }

    public void scan() {
        PageScanner.scan(this, browser);
    }
}
