package nl.centralpoint.task;

import nl.centralpoint.task.utils.DriverFactory;
import nl.centralpoint.task.utils.EventHandler;
import nl.centralpoint.task.utils.ScreenshotListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by massacre99 on 08.05.2018.
 */
@Listeners({ScreenshotListener.class})
public abstract class BaseTest {

    protected EventFiringWebDriver driver;
    protected GeneralActions actions;
    protected boolean isMobileTesting;

    @BeforeClass
    @Parameters("selenium.browser")
    public void setUp(@Optional("chrome") String browser) {

        driver = new EventFiringWebDriver(DriverFactory.initDriver(browser));
        driver.register(new EventHandler());
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        // unable to maximize window in mobile mode
        if (!isMobileTesting(browser))
            driver.manage().window().maximize();

        isMobileTesting = isMobileTesting(browser);

        actions = new GeneralActions(driver);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private boolean isMobileTesting(String browser) {
        switch (browser) {
            case "android":
                return true;
            case "firefox":
            case "ie":
            case "internet explorer":
            case "headless-chrome":
            case "chrome":
            default:
                return false;
        }
    }

    public EventFiringWebDriver getDriver() {
        return driver;
    }
}
