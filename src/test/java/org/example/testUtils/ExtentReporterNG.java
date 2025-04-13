package org.example.testUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {

    private static ExtentReports extentReports; // Singleton instance

    public static ExtentReports getExtentReporterObject() {
        if (extentReports == null) {
            String path = System.getProperty("user.dir") + "/reports/extentReports/index.html";
            ExtentSparkReporter reporter = new ExtentSparkReporter(path);
            reporter.config().setReportName("BHIM Automation Results");
            reporter.config().setDocumentTitle("BHIM Test Results");

            extentReports = new ExtentReports();
            extentReports.attachReporter(reporter);
            extentReports.setSystemInfo("Tester", "Akshay Reddy");
        }
        return extentReports;
    }
}
