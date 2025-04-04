package org.example.pageObjects.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.example.utils.AndroidActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;
import java.util.Objects;

public class FormPage extends AndroidActions{
    AndroidDriver driver;

    public FormPage(AndroidDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver),this);
    }

    @AndroidFindBy(id = "com.androidsample.generalstore:id/nameField")
    private WebElement nameField;

    @AndroidFindBy(xpath = "//android.widget.RadioButton[@text='Female']")
    private WebElement femaleRadioBtn;

    @AndroidFindBy(xpath = "//android.widget.RadioButton[@text='Male']")
    private WebElement maleRadioBtn;

    @AndroidFindBy(id = "android:id/text1")
    private WebElement countryDropdownBtn;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Argentina\"]")
    private WebElement countryElement;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
    private WebElement shopButton;

    @AndroidFindBy(xpath = "(//android.widget.Toast)[1]")
    private WebElement toastMessage;

    @AndroidFindBy(xpath = "(//android.widget.Toast)[1]")
    private List<WebElement> toastMessages;

    public void setNameField(String name){
        nameField.sendKeys(name);
    }

    public void selectGender(String gender){
        if(gender.equals("Male")){
            maleRadioBtn.click();
        }else{
            femaleRadioBtn.click();
        }
    }

    public void selectCountry(String country){
        countryDropdownBtn.click();
        scrollToElement(country);
        driver.findElement(By.xpath("//android.widget.TextView[@text=\""+country+"\"]")).click();
    }

    public void submitForm(){
        shopButton.click();
    }

    public String getToastMessage(){
        return toastMessage.getAttribute("name");
    }

    public int getTotalToastMessagePopups(){
        return toastMessages.size();
    }
}
