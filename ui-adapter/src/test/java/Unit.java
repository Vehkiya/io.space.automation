import io.space.automation.ui.core.browser.Browser;
import io.space.automation.ui.core.browser.PageManager;
import io.space.automation.ui.core.browser.impl.ChromeBrowser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.ArticlePage;
import pages.HomePage;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Unit {

    private Browser browser;

    private PageManager pageManager;

    @BeforeEach
    void setUp() {
        browser = new ChromeBrowser(new File("src\\main\\resources\\drivers\\chromedriver.exe"))
                .setOptions(new ChromeOptions().addArguments("headless=true"))
//                .setOptions(new ChromeOptions().setHeadless(true))
        ;
        browser.driver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        pageManager = new PageManager(browser);
        pageManager.scanAll("pages");
    }

    @Test
    void pageManager() throws InterruptedException {
        HomePage homePage = browser.getPage(HomePage.class);
        homePage.open();
        homePage.submitSearch("Test Automation");

        ArticlePage articlePage = browser.getPage(ArticlePage.class);
        Assertions.assertTrue(articlePage.isCurrentPage(), "Article Page is current page");
        Assertions.assertTrue(articlePage.getHeader().isDisplayed(), "Header is displayed");
        Assertions.assertEquals("Test automation", articlePage.getHeader().getText());
        // for demo only
//        Thread.sleep(5000);
    }

    @AfterEach
    void tearDown() {
        browser.quit();
    }
}
