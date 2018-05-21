package nl.centralpoint.task.utils;

import io.qameta.allure.Attachment;
import nl.centralpoint.task.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * Created by massacre99 on 09.05.2018.
 */
public class ScreenshotListener extends TestListenerAdapter {

    private Logger log = LoggerFactory.getLogger(ScreenshotListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        log.info("Test class started: " + result.getTestClass().getName());
        log.info("Test started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("Test SUCCESS: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        saveScreenshot(result);
        log.info("Test FAILED: " + result.getName());
        if (result.getThrowable() != null) {
            result.getThrowable().printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        saveScreenshot(result);
        log.info("Test SKIPPED: " + result.getName());
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    private byte[] saveScreenshot(ITestResult result) {
        Object currentClass = result.getInstance();
        WebDriver driver = ((BaseTest) currentClass).getDriver();
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}