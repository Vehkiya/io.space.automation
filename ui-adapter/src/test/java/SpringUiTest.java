import io.space.automation.ui.core.browser.Browser;
import io.space.automation.ui.core.browser.BrowserFactory;
import io.space.automation.ui.core.config.UiConfiguration;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import pages.ArticlePage;
import pages.HomePage;

@Log4j2
@SpringBootTest
@ContextConfiguration(classes = {UiConfiguration.class})
public class SpringUiTest {

    @Autowired
    private BrowserFactory browserFactory;

    private Browser browser;

    @BeforeEach
    void setUp() {
        browser = browserFactory.getBrowser();
    }

    @Test
    void name() {
        HomePage homePage = browser.getPage(HomePage.class);
        homePage.open();
        homePage.submitSearch("Test Automation");

        ArticlePage articlePage = browser.getPage(ArticlePage.class);
        Assertions.assertTrue(articlePage.isCurrentPage(), "Article Page is current page");
        Assertions.assertTrue(articlePage.getHeader().isDisplayed(), "Header is displayed");
        Assertions.assertEquals("Test automation", articlePage.getHeader().getText());
    }

    @AfterEach
    void tearDown() {
        browser.quit();
        browser = null;
    }
}
