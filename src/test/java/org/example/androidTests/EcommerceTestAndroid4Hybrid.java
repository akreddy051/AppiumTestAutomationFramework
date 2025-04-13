package org.example.androidTests;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.AndroidDriver;
import org.example.pageObjects.android.CartPage;
import org.example.pageObjects.android.FormPage;
import org.example.pageObjects.android.ProductsPage;
import org.example.testUtils.BaseTestAndroid;
import org.example.utils.AppiumActions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class EcommerceTestAndroid4Hybrid extends BaseTestAndroid {
    public AppiumActions appiumActions = new AppiumActions();

    @BeforeMethod(alwaysRun = true)
    public void setHomePage() throws InterruptedException {
        getDriver().executeScript("mobile: startActivity", ImmutableMap.of(
                "intent","com.androidsample.generalstore/com.androidsample.generalstore.MainActivity"
        ));
        Thread.sleep(3000);
    }

    @Test(dataProvider = "jsonFileDataProvider")
    public void validatingTotalAmount(Map<String,String> input) throws InterruptedException {
        //Login page steps
        FormPage formPage = new FormPage((AndroidDriver) getDriver());
        formPage.setNameField(input.get("name"));
        formPage.selectGender(input.get("gender"));
        formPage.selectCountry(input.get("country"));
        formPage.submitForm();

        ProductsPage productsPage = new ProductsPage((AndroidDriver) getDriver());
        //Selecting products and clicking on the cart icon
        String[] productsToShop = {"Converse All Star","Air Jordan 9 Retro"};
        productsPage.addProductsToCart(productsToShop);
        productsPage.navigateToCart();

        CartPage cartPage = new CartPage((AndroidDriver) getDriver());
        double sumOfAllProducts = cartPage.getSumOfAllProducts();
        double totalPriceDisplayed = cartPage.getTotalPriceDisplayed();
        Assert.assertEquals(sumOfAllProducts,totalPriceDisplayed);

        //Validating terms and conditions
        cartPage.validateTermsAndConditions();

        //clicking and checkbox and clicking on purchase button
        cartPage.clickOnCheckBox();
        cartPage.submitOrder();

    }

    @DataProvider(name = "inFileDataProvider")
    public Object[][] getData(){
        return new Object[][]{
                {"Akshay","Male","Belgium"},
                {"Nidhi","Female","Australia"}
        };
    }

    @DataProvider(name = "jsonFileDataProvider")
    public Object[][] getJsonData() throws IOException {
        List<Map<String, String>> testData = appiumActions.getJsonTestData("/src/test/java/org/example/androidTests/testData/eCommerce/eCommerce.json");
        // Convert List<Map<String, String>> to Object[][] with individual String values
        Object[][] data = new Object[testData.size()][1]; // 3 because we have 3 String parameters
        for (int i = 0; i < testData.size(); i++) {
            data[i][0] = testData.get(i);
        }
        return data;
    }
}
