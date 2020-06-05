package io.space.automation.ui.core.page;

import io.space.automation.ui.core.browser.Browser;
import io.space.automation.util.reflection.Reflections;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebElement;
import io.space.automation.ui.core.page.scanner.Locator;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageScanner {

    public static <T extends Page> void scan(@Nonnull T page, Browser browser) {
        Reflections.getFieldsWithAnnotation(page.getClass(), Locator.class)
                .forEach(field -> initializeField(field, page, browser));
    }

    private static <T> void initializeField(Field field, T o, Browser browser) {
        Locator declaredAnnotation = field.getDeclaredAnnotation(Locator.class);
        findByXpath(declaredAnnotation.xpath(), browser)
                .ifPresent(webElement -> Reflections.setFieldValue(field, o, webElement));
    }

    private static Optional<WebElement> findByXpath(String xpath, Browser browser) {
        try {
            return Optional.ofNullable(browser.driver()
                    .findElement(By.xpath(xpath)));
        } catch (ElementNotInteractableException e) {
            return Optional.empty();
        }
    }
}
