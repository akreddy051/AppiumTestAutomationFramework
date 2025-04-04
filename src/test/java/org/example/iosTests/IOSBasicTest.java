package org.example.iosTests;

import org.example.pageObjects.ios.AlertPage;
import org.example.pageObjects.ios.HomePage;
import org.example.testUtils.BaseTestIOS;
import org.testng.annotations.Test;

public class IOSBasicTest extends BaseTestIOS {
    @Test
    public void basic() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.clickOnAlertViews();
        AlertPage alertPage = new AlertPage(driver);
        alertPage.selectTextEntry();
        alertPage.enterTextInTextField("Akshay");
        alertPage.clickOKBtn();
        alertPage.verifyConfirmCancel();
        Thread.sleep(5000);
    }
}
