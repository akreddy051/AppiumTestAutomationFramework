package org.example.testUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {

    public static ExtentReports getExtentReporterObject(){
        String path = System.getProperty("user.dir")+"/reports/extentReports/index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("BHIM Automation Results");
        reporter.config().setDocumentTitle("BHIM Test Results");

        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("tester","Akshay Reddy");
        return extentReports;
    }

}
