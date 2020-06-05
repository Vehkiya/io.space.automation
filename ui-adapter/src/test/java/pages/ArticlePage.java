package pages;

import io.space.automation.ui.core.browser.Browser;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebElement;
import io.space.automation.ui.core.page.scanner.Locator;
import io.space.automation.ui.core.page.scanner.PageDescriptor;

@Getter
@Setter
@PageDescriptor(name = "Article Page", url = "/wiki/Test_automation")
public class ArticlePage extends HomePage {

    @Locator(name = "header", xpath = "//*[@id=\"firstHeading\"]")
    private WebElement header;

    public ArticlePage(Browser browser) {
        super(browser);
    }
}
