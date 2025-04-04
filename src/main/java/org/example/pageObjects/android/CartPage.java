package org.example.pageObjects.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.example.utils.AndroidActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends AndroidActions {
    AndroidDriver driver;

    public CartPage(AndroidDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver),this);
    }

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productPrice")
    private List<WebElement> individualProductPrices;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/totalAmountLbl")
    private WebElement totalProductsPrice;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/termsButton")
    private WebElement termsAndConditionsButton;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='CLOSE']")
    private WebElement closeBtnTermsAndConditions;

    @AndroidFindBy(xpath = "//android.widget.CheckBox")
    private WebElement checkBox;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/btnProceed")
    private WebElement proceedBtn;


    public double getSumOfAllProducts(){
        double sumOfAllProductPrices = 0;
        for(WebElement price: individualProductPrices){
            double productPriceInDouble = Double.parseDouble(price.getText().substring(1));
            sumOfAllProductPrices+=productPriceInDouble;
        }
        return sumOfAllProductPrices;
    }

    public double getTotalPriceDisplayed(){
        return Double.parseDouble(totalProductsPrice.getText().split(" ")[1]);
    }

    public void validateTermsAndConditions(){
        longPress(termsAndConditionsButton);
        closeBtnTermsAndConditions.click();
    }

    public void clickOnCheckBox(){
        checkBox.click();
    }

    public void submitOrder() throws InterruptedException {
        proceedBtn.click();
        Thread.sleep(5000);
    }


}
