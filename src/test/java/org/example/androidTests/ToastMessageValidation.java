package org.example.androidTests;

import com.google.common.collect.ImmutableMap;
import org.example.pageObjects.android.FormPage;
import org.example.testUtils.BaseTestAndroid;
import org.example.testUtils.BrowserStackAPI;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ToastMessageValidation extends BaseTestAndroid {

    @BeforeMethod(alwaysRun = true)
    public void setHomePage() throws InterruptedException {
        driver.executeScript("mobile: startActivity", ImmutableMap.of(
                "intent","com.androidsample.generalstore/com.androidsample.generalstore.MainActivity"
        ));
        Thread.sleep(3000);
    }

    @Test(groups = {"Smoke"})
    public void fillFormToastMessageValidation() throws InterruptedException {
        FormPage formPage = new FormPage(driver);
        formPage.selectGender("Female");
        formPage.selectCountry("Australia");
        formPage.submitForm();
        String toastMessage = formPage.getToastMessage();
        Assert.assertEquals(toastMessage,"Please ente your name");
    }

    @Test
    public void fillFormPositiveFlow() throws InterruptedException {
        FormPage formPage = new FormPage(driver);
        formPage.setNameField("Akshay");
        formPage.selectGender("Female");
        formPage.selectCountry("Australia");
        formPage.submitForm();
        int totalToastMessagePopups = formPage.getTotalToastMessagePopups();
        Assert.assertTrue(totalToastMessagePopups<1);
    }
}
