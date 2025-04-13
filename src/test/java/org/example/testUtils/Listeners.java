package org.example.testUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.appium.java_client.AppiumDriver;
import org.example.utils.AppiumActions;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listeners extends AppiumActions implements ITestListener {
    AppiumDriver driver;
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    ExtentReports extent = ExtentReporterNG.getExtentReporterObject();
    @Override
    public void onTestStart(ITestResult result) {
        extentTest.set(extent.createTest(result.getMethod().getMethodName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        driver = fetchDriver(result);
        extentTest.get().log(Status.PASS,"The test has passed");
        logExecutionLink(driver);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().log(Status.FAIL,result.getThrowable());
        driver = fetchDriver(result);
        try {
            String screenShotPath = captureScreenShot(driver,result.getMethod().getMethodName());
            extentTest.get().addScreenCaptureFromPath(screenShotPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        logExecutionLink(driver);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, "Test skipped: " + result.getThrowable());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        extentTest.get().log(Status.SKIP, "Test Timed out: " + result.getThrowable());
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    public void logExecutionLink(AppiumDriver driver){
        String buildId = BrowserStackAPI.getLatestBuildId();
        String sessionId = driver.getSessionId().toString();
        String link = "<a href='https://app-automate.browserstack.com/dashboard/v2/builds/"+buildId+"/sessions/"+sessionId+"' target='_blank'>Test Execution Link</a>";
        Markup m = MarkupHelper.createLabel(link, ExtentColor.BLACK);
        if(System.getProperty("executionType").equals("local")){
            extentTest.get().log(Status.INFO, "This test got executed locally");
        }else{
            extentTest.get().log(Status.INFO, m);
        }
    }

    public AppiumDriver fetchDriver(ITestResult result){
        AppiumDriver appiumDriver;
        try {
            appiumDriver = (AppiumDriver) result.getInstance()
                    .getClass()
                    .getMethod("getDriver")
                    .invoke(result.getInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return appiumDriver;
    }
}
