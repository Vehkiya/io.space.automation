package pages;

import io.space.automation.ui.core.browser.Browser;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebElement;
import io.space.automation.ui.core.page.Page;
import io.space.automation.ui.core.page.scanner.Locator;
import io.space.automation.ui.core.page.scanner.PageDescriptor;

@Getter
@Setter
@PageDescriptor(name = "Wikipedia homepage", url = "https://wikipedia.org")
public class HomePage extends Page {

    @Locator(name = "Search Button", xpath = "//*[@id=\"search-form\"]/fieldset/button/i")
    private WebElement searchButton;
    @Locator(name = "Search Bar", xpath = "//*[@id=\"searchInput\"]")
    private WebElement searchBar;

    public HomePage(Browser browser) {
        super(browser);
    }

    public void submitSearch(String term) {
        getSearchBar().sendKeys(term);
        getSearchButton().click();
    }

    public WebElement getSearchButton() {
        return searchButton;
    }

    public WebElement getSearchBar() {
        return searchBar;
    }
}
