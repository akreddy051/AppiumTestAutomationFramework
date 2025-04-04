package org.example.testUtils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Properties;

public class BaseTestAndroid extends BaseTestAppium{
    public AppiumDriverLocalService service;
    public AndroidDriver driver;

    @BeforeClass(alwaysRun = true)
    public void configureAppium() throws URISyntaxException, IOException {
        //Creating Properties object for fetching properties from .properties file
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/org/example/resources/data.properties");
        properties.load(fis);
        //Starting the appium server using the code
        String ipAddress = System.getProperty("ipAddress")!=null?System.getProperty("ipAddress"):properties.getProperty("ipAddress");
        service = startAppiumService(ipAddress, Integer.parseInt(properties.getProperty("port")));
        service.start();

        // For providing capabilities like device details, android version, app details etc we use UiAutomator2Options
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("MyPixel9");//emulator
        //ApiDemos-debug.apk path
//        options.setApp("/Users/asingadiwar/Documents/MyCode/Java/LearningAppium/src/test/java/resources/ApiDemos-debug.apk");
        //General-Store.apk path
        options.setApp("/Users/asingadiwar/Documents/MyCode/Java/LearningAppium/src/test/java/resources/General-Store.apk");
        options.setChromedriverExecutable("/Users/asingadiwar/Documents/MyCode/Java/LearningAppium/src/test/java/resources/chromedriver");

        //for Android, we are using AndroidDriver to automate. If we are automating in IOS then we will be using IOSDriver
        driver = new AndroidDriver(new URI(service.getUrl().toString()).toURL(),options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterClass(alwaysRun = true)
    public void tearDownAppium(){
        //quitting the app
        driver.quit();
        //closing the appium server
        service.stop();
    }
}
