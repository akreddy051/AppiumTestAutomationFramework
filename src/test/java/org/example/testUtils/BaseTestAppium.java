package org.example.testUtils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.io.File;

public class BaseTestAppium {
    public AppiumDriverLocalService startAppiumService(String ipAddress,int port){
        return new AppiumServiceBuilder()
                .withAppiumJS(new File("/opt/homebrew/lib/node_modules/appium/build/lib/main.js")) // providing the appium's main.js file path.
                .withIPAddress(ipAddress)
                .usingPort(port).build();
    }



}
