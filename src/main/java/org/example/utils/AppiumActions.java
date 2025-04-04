package org.example.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class AppiumActions {

    public AppiumDriverLocalService startAppiumService(){
        return new AppiumServiceBuilder()
                .withAppiumJS(new File("/opt/homebrew/lib/node_modules/appium/build/lib/main.js")) // providing the appium's main.js file path.
                .withIPAddress("127.0.0.1")
                .usingPort(4723).build();
    }

    public void waitUntilElementAppears(AppiumDriver driver, WebElement ele,String attribute,String value){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.attributeContains((ele),attribute,value));
    }

    public List<Map<String, String>> getJsonTestData(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(
                new File(System.getProperty("user.dir")+filePath),
                new TypeReference<List<Map<String, String>>>() {}
        );
    }

    public String captureScreenShot(AppiumDriver driver, String testCaseName) throws IOException {
        File screenshotAs = driver.getScreenshotAs(OutputType.FILE);
        long epochMillis = System.currentTimeMillis();
        String imagePath = "reports/screenshots/"+testCaseName+"-"+epochMillis+".png";
        FileUtils.copyFile(screenshotAs, new File(imagePath));
        return "../screenshots/"+testCaseName+"-"+epochMillis+".png";
    }
}
