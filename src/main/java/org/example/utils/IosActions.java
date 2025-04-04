package org.example.utils;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.HashMap;
import java.util.Map;

public class IosActions extends AppiumActions {
    IOSDriver driver;

    public IosActions(IOSDriver driver) {
        this.driver = driver;
    }

    public void scrollElement(WebElement ele,String direction){
        Map<String,Object> params = new HashMap<>();
        params.put("element",((RemoteWebElement)ele).getId());
        params.put("direction",direction);
        driver.executeScript("mobile:scroll", params);
    }

    public void touchAndHold(WebElement e,double duration){
        Map<String,Object> params = new HashMap<>();
        params.put("elementId", ((RemoteWebElement)e).getId());
        params.put("duration", duration);
        driver.executeScript("mobile: touchAndHold", params);
    }

    public void swipe(WebElement ele, String direction){
        RemoteWebElement e = (RemoteWebElement) ele;
        driver.executeScript("mobile:swipe", ImmutableMap.of(
                "direction", "left",
                "elementId", e.getId()));
    }


}
