package org.example.testUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.example.utils.LocalDeviceConfigReader;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class BaseTestAndroid extends BaseTestAppium {

    private AppiumDriverLocalService service;
    private static final ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();
    private static final AtomicInteger deviceIndex = new AtomicInteger(0); // For safe device assignment

    // === File Paths ===
    private static final String DEVICES_JSON_PATH = "src/main/java/org/example/resources/localDevices.json";
    private static final String PROPERTIES_FILE_PATH = "src/main/java/org/example/resources/data.properties";
    private static final String APP_PATH = "/Users/asingadiwar/Documents/MyCode/Java/LearningAppium/src/test/java/resources/General-Store.apk";
    private static final String CHROMEDRIVER_PATH = "/Users/asingadiwar/Documents/MyCode/Java/LearningAppium/src/test/java/resources/chromedriver";

    private static final ThreadLocal<Integer> threadDeviceIndex = ThreadLocal.withInitial(() -> {
        int index = deviceIndex.getAndIncrement();
        if (index >= LocalDeviceConfigReader.getLocalDeviceConfigs().size()) {
            throw new RuntimeException("More parallel test threads than available devices. Please increase devices or reduce thread count.");
        }
        return index;
    });

    public AppiumDriver getDriver() {
        return driver.get();
    }

    @BeforeClass(alwaysRun = true)
    @Parameters({"deviceName", "udid", "systemPort", "appiumPort"})
    public void configureAppium(
            @Optional("") String deviceName,
            @Optional("") String udid,
            @Optional("") String systemPort,
            @Optional("") String appiumPort
    ) throws URISyntaxException, IOException {

        // If device details are not provided, use devices.json
        if (deviceName.isEmpty() || udid.isEmpty() || systemPort.isEmpty() || appiumPort.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, String>> devices = mapper.readValue(
                    new File(DEVICES_JSON_PATH),
                    new TypeReference<List<Map<String, String>>>() {}
            );

            int index = threadDeviceIndex.get();
            Map<String, String> selectedDevice = devices.get(index);

            deviceName = selectedDevice.get("deviceName");
            udid = selectedDevice.get("udid");
            systemPort = selectedDevice.get("systemPort");
            appiumPort = selectedDevice.get("appiumPort");
        }

        // Load properties
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/" + PROPERTIES_FILE_PATH);
        properties.load(fis);

        // Resolve IP
        String ipAddress = System.getProperty("ipAddress") != null
                ? System.getProperty("ipAddress")
                : properties.getProperty("ipAddress");

        // Start Appium server
        service = startAppiumService(ipAddress, Integer.parseInt(appiumPort));
        service.start();

        // Setup capabilities
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName(deviceName)
                .setUdid(udid)
                .setSystemPort(Integer.parseInt(systemPort))
                .setApp(APP_PATH)
                .setFullReset(true)
                .setChromedriverExecutable(CHROMEDRIVER_PATH);

        AndroidDriver localDriver = new AndroidDriver(new URI(service.getUrl().toString()).toURL(), options);
        localDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.set(localDriver);
    }

    @AfterClass(alwaysRun = true)
    public void tearDownAppium() {
        if (getDriver() != null) {
            getDriver().quit();
        }
        if (service != null && service.isRunning()) {
            service.stop();
        }
        driver.remove();
    }
}
