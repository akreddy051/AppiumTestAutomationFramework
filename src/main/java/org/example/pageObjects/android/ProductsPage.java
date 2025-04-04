package org.example.pageObjects.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.example.utils.AndroidActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductsPage extends AndroidActions {
    AndroidDriver driver;

    public ProductsPage(AndroidDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver),this);
    }

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.androidsample.generalstore:id/productName']")
    private List<WebElement> productNames;

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.androidsample.generalstore:id/productAddCart']")
    private List<WebElement> productAddToCartButton;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/appbar_btn_cart")
    private WebElement addToCartButton;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/toolbar_title")
    private WebElement pageTitle;

    public void addProductsToCart(String[] productsToShop){
        for(String product : productsToShop){
            scrollToElement(product);
            for(int i=0;i<productNames.size();i++){
                String productName = productNames.get(i).getText();
                if(productName.equals(product)){
                    productAddToCartButton.get(i).click();
                }
            }
        }
    }

    public void navigateToCart() throws InterruptedException {
        addToCartButton.click();
        waitUntilElementAppears(driver,pageTitle,"text","Cart");
    }
}
