package io.space.automation.ui.core.page;

import io.space.automation.ui.core.browser.Browser;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Module {

    private final Module parent;

    private final String xpath;

    private final Browser browser;
}
