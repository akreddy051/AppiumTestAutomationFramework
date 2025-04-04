package org.example.pageObjects.ios;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.example.utils.IosActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class AlertPage extends IosActions {
    IOSDriver driver;

    public AlertPage(IOSDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver),this);
    }

    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeStaticText[`name == 'Text Entry'`]")
    private WebElement textEntryBtn;

    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell")
    private List<WebElement> typeCells;

    @iOSXCUITFindBy(accessibility = "OK")
    private WebElement okBtn;

    @iOSXCUITFindBy(iOSNsPredicate = "label = 'Confirm / Cancel'")
    private WebElement confirmAndCancelBtn;

    @iOSXCUITFindBy(iOSNsPredicate = "name ENDSWITH 'sentence.'")
    private WebElement staticMessage;

    @iOSXCUITFindBy(iOSNsPredicate = "name == 'Confirm'")
    private WebElement confirmBtn;

    public void selectTextEntry(){
        textEntryBtn.click();
    }

    public void enterTextInTextField(String text){
        typeCells.get(typeCells.size()-1).sendKeys(text);
    }

    public void clickOKBtn(){
        okBtn.click();
    }

    public void verifyConfirmCancel(){
        confirmAndCancelBtn.click();
        System.out.println(staticMessage.getText());
        confirmBtn.click();
    }



}
