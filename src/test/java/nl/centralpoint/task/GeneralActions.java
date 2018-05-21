package nl.centralpoint.task;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

/**
 * Created by massacre99 on 08.05.2018.
 */
public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExec;
    Random random;
    int checkboxQuantity;


    String notebookUrl = "https://www.centralpoint.nl/notebooks-laptops/";
    String lcdUrl = "https://www.centralpoint.nl/monitoren/";
    String mainUrl = "https://www.centralpoint.nl/";


    // notebook page locators

    private By allFilter = By.cssSelector(".mobileSwitchFiltersOff > .filter");
    private By openFilter = By.cssSelector(".active ul");
    private By closeContainerButton = By.cssSelector(".icon-cross3");
    private By orders = By.cssSelector("div.order");
    private By openFilterCheckbox = By.cssSelector(".active li > label > .number");
    private By submitFilterButton = By.cssSelector(".active button");
    private By productQuantity = By.cssSelector(".title > h1");

    // lcd page locators

    private By minPrice = By.cssSelector(".minRange");
    private By maxPrice = By.cssSelector(".maxRange");
    private By sortPriceAsc = By.xpath("//a[contains(@onclick, 'priceASC')]");
    private By price = By.cssSelector(".col3 > .priceExcl");

    // other locators

    private By searchForm = By.xpath("(//input[@name='search'])[1]");
    private By productCode = By.xpath("//*[@class='productCode']");
    private By productNumber = By.xpath("//*[@class='productNumber']");


    public String getProductCode() {
        try {
            return driver.findElement(productCode).getText();
        } catch (NoSuchElementException e) {
            return driver.findElement(productNumber).getText();
        }

    }

    public int getElementQuantity(WebElement element) {
        return Integer.parseInt(element.getText().replaceAll("[\\D]", "").trim());
    }

    public int getLocatorQuantity(By locator) {
        return Integer.parseInt(driver.findElement(locator).
                getText().replaceAll("[\\D]", "").trim());
    }

    public int getProductQuantity() {
        return getLocatorQuantity(productQuantity);
    }

    public void chooseFillRandomFilter() {
        new Actions(driver).moveToElement(driver.findElements(orders).get(0)).perform();
        randomFilterClick();
        wait.until(ExpectedConditions.visibilityOfElementLocated(openFilter));
        randomFilterCheckboxClick();
    }

    public void submitFilter() {
        new Actions(driver).moveToElement(driver.findElement(submitFilterButton)).click().perform();
    }

    public int getCheckboxQuantity() {
        return checkboxQuantity;
    }


    public void openNotebookUrl() {
        driver.get(notebookUrl);
        waitForContentLoad();
        closeContainerButton();
    }

    public void openLcdUrl() {
        driver.get(lcdUrl);
        waitForContentLoad();
    }

    public void openMainUrl() {
        driver.get(mainUrl);
        waitForContentLoad();
    }

    public void fillPrice(int min, int max) {
        new Actions(driver).
                doubleClick(driver.findElement(minPrice)).
                sendKeys(String.valueOf(min)).perform();
        new Actions(driver).
                doubleClick(driver.findElement(maxPrice)).
                sendKeys(String.valueOf(max)).perform();
    }

    public void clickSortPriceAsc() {
        driver.findElement(sortPriceAsc).click();
    }

    public int getFirstPrice() {
        return getElementQuantity(driver.findElements(price).get(0));
    }


    public void closeContainerButton() {
        if (driver.findElement(closeContainerButton).isDisplayed()) {
            driver.findElement(closeContainerButton).click();
        }
    }


    public void randomFilterClick() {
        List<WebElement> list = driver.findElements(allFilter);
        int random = getRandomListValue(list);
        if (!list.get(random).getText().equals("Prijs")) {
            if (!list.get(random).getText().equals("Sortering")) {
                list.get(random).click();
            } else randomFilterClick();
        } else randomFilterClick();
    }

    public void titleFilterClick(String title) {
        List<WebElement> list = driver.findElements(allFilter);
        Boolean find = false;

        for (WebElement webElement : list) {
            if (find) break;
            if (webElement.getText().equals(title)) {
                webElement.click();
                find = true;
            }
        }

    }

    public int getRandomListValue(List<WebElement> list) {
        return new Random().nextInt(list.size());
    }


    public void randomFilterCheckboxClick() {
        List<WebElement> list = driver.findElements(openFilterCheckbox);
        int random = getRandomListValue(list);
        WebElement selectedCheckbox = list.get(random);
        if (selectedCheckbox.isDisplayed()) {
            selectedCheckbox.click();
            checkboxQuantity = getElementQuantity(selectedCheckbox);
        } else {
            new Actions(driver).moveToElement(selectedCheckbox).perform();
            if (selectedCheckbox.isDisplayed()) {
                selectedCheckbox.click();
                checkboxQuantity = getElementQuantity(selectedCheckbox);
            }
        }
    }

    public void fillSearchFormAndSubmit(String product) {
        driver.findElement(searchForm).sendKeys(product);
        driver.findElement(searchForm).submit();
    }


    public void waitForContentLoad() {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        Boolean isReady = false;
        while (!isReady) {
            isReady = executor.executeScript("return document.readyState").equals("complete");
        }
    }

    public WebElement scrollToElement(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView();", element);
        return element;
    }

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
        jsExec = (JavascriptExecutor) driver;
    }
}
