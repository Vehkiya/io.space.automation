package io.space.automation.ui.core.browser;

import lombok.extern.log4j.Log4j2;
import io.space.automation.ui.core.page.Page;
import io.space.automation.ui.core.page.scanner.PageDescriptor;
import io.space.automation.util.reflection.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
public final class PageManager {

    private final Map<String, Class<? extends Page>> pageMap;

    private final Browser browser;

    public PageManager(Browser browser) {
        this.browser = browser;
        this.pageMap = new HashMap<>();
    }

    public <T extends Page> Optional<Class<T>> getPage(String pageName) {
        return Optional.ofNullable((Class<T>) pageMap.get(pageName));
    }

    public <T extends Page> T initPage(Class<T> pageClass) {
        return instantiatePage(pageClass);
    }

    public void scanAll(String... packages) {
        var reflections = new org.reflections.Reflections(packages);
        reflections.getSubTypesOf(Page.class)
                .forEach(pageClass -> Reflections.getAnnotation(pageClass, PageDescriptor.class)
                        .ifPresent(descriptor -> pageMap.put(descriptor.name(), pageClass)));
    }

    private <T extends Page> T instantiatePage(Class<T> pageClass) {
        T instance = null;
        try {
            Constructor<T> constructor = pageClass.getConstructor(Browser.class);
            instance = constructor.newInstance(browser);
            return instance;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            log.warn("Failed to instantiate page", e);
        }
        return instance;
    }
}
