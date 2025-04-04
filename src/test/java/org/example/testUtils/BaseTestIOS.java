package org.example.testUtils;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
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

public class BaseTestIOS extends BaseTestAppium{
    public AppiumDriverLocalService service;
    public IOSDriver driver;

    @BeforeClass(alwaysRun = true)
    public void configureAppium() throws URISyntaxException, IOException {
        //Creating Properties object for fetching properties from .properties file
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/org/example/resources/data.properties");
        properties.load(fis);
        //Starting the appium server using the code
        service = startAppiumService(properties.getProperty("ipAddress"), Integer.parseInt(properties.getProperty("port")));
        service.start();

        // For providing capabilities like device details, IOS version, app details etc we use XCUITestOptions
        XCUITestOptions options = new XCUITestOptions();
        options.setDeviceName("iPhone 16");
        options.setApp("/Users/asingadiwar/Desktop/UIKitCatalog.app");
//        options.setApp("/Users/asingadiwar/Documents/MyCode/Java/LearningAppium/src/test/java/resources/TestApp 3.app");
        options.setPlatformVersion("18.3");
        options.setWdaLaunchTimeout(Duration.ofSeconds(20));

        //for IOS, we are using IOSDriver to automate. If we are automating in IOS then we will be using IOSDriver
        driver = new IOSDriver(new URI("http://127.0.0.1:4723/").toURL(),options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterClass(alwaysRun = true)
    public void tearDownAppium(){
        //quitting the app
        driver.quit();
        System.out.println("driver quitted");
        //closing the appium server
        service.stop();
        System.out.println("driver stopped");

    }
}
