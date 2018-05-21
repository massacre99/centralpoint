package nl.centralpoint.task.tests;

import io.qameta.allure.Description;
import nl.centralpoint.task.BaseTest;
import nl.centralpoint.task.utils.CustomReporter;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by massacre99 on 08.05.2018.
 */
public class CentralPointTest extends BaseTest {

    @Test
    @Description("Select random filter and check the resul thave the same quantity as selected filter")
    public void task1() {

        actions.openNotebookUrl();
        actions.chooseFillRandomFilter();
        int quantityCheckbox = actions.getCheckboxQuantity();
        actions.submitFilter();
        actions.waitForContentLoad();
        int quantity = actions.getProductQuantity();

        Assert.assertEquals(quantity, quantityCheckbox, "Size not equals");

        CustomReporter.log("Test passed. Checkbox quantity equals search quantity");

    }

    @Test
    @Description("Apply price filter and check that result doesn’t contain price less than minimum price that we set.")
    public void task2() {

        actions.openLcdUrl();
        actions.titleFilterClick("Prijs");
        actions.fillPrice(1000, 5000);
        actions.submitFilter();
        actions.waitForContentLoad();
        actions.titleFilterClick("Sortering");
        actions.clickSortPriceAsc();
        actions.waitForContentLoad();
        int firstAscPrice = actions.getFirstPrice();

        Assert.assertTrue(firstAscPrice - 1000 >= 0, "Have element cheaper than 1000");

        CustomReporter.log("Test passed. Checkbox quantity equals search quantity");
    }


    @DataProvider(name = "task5")
    public Object[][] taskData() {
        return new Object[][] {
                { "J153289" }, { "MQ3D2ZD/A" }, { "L36852-H2436-M101" }, { "1WZ03EA#ABH" }, { "875839-425" },
                { "C5J91A#B19" }, { "FM32SD45B/10" }, { "204446-101" }, { "GV-N710D3-1GL" }, { "02G-P4-6150-KR" }
        };
    }


    @Test(dataProvider = "task5")
    @Description("Search for each product by ProdId using DataProvider")
    public void task5(String code) {

        actions.openMainUrl();
        actions.fillSearchFormAndSubmit(code);
        actions.waitForContentLoad();
        String fullProductCode = actions.getProductCode();

        Assert.assertTrue(fullProductCode.contains(code), "Product code not contains");

        CustomReporter.log("Test passed. Code contains in product code string");
    }

}
