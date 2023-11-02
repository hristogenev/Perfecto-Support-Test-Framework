package io.perfecto.utilities.automationsession;

import io.appium.java_client.remote.MobilePlatform;
import io.perfecto.utilities.application.Application;
import io.perfecto.utilities.capabilities.AppiumVersion;
import io.perfecto.utilities.capabilities.CommonCapabilities;
import io.perfecto.utilities.capabilities.ScreenResolution;
import io.perfecto.utilities.extendedmobiledriver.ExtendedMobileDriver;
import io.perfecto.utilities.reporting.Report;
import io.perfecto.utilities.reporting.ReportBuilder;
import io.perfecto.utilities.useractions.UserActions;
import org.openqa.selenium.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class TestAutomationSession {

    private final static Logger logger = LoggerFactory.getLogger(TestAutomationSession.class);
    private ExtendedMobileDriver driver;
    private CommonCapabilities commonCapabilities;
    private UserActions userActions;
    private Application app;
    private String appId;
    private Report report = new Report();
    private ReportBuilder reportBuilder;

    public TestAutomationSession() {
        commonCapabilities  = new CommonCapabilities();
        commonCapabilities.cloudName = "local";
        reportBuilder = new ReportBuilder();
    }

    public TestAutomationSession withCloud(String cloudName) {
        commonCapabilities.cloudName = cloudName;
        return this;
    }

    public TestAutomationSession withPlatformNameAndroid() {
        commonCapabilities.platformName = MobilePlatform.ANDROID;
        return this;
    }

    public TestAutomationSession withAndroidVersion(String androidVersion) {
        withPlatformNameAndroid();
        commonCapabilities.platformVersion = androidVersion;
        return this;
    }

    public TestAutomationSession withPlatformNameIOS() {
        commonCapabilities.platformName = MobilePlatform.IOS;
        return this;
    }

    public TestAutomationSession withIOSVersion(String iosVersion) {
        withPlatformNameIOS();
        commonCapabilities.platformVersion = iosVersion;
        return this;
    }

    public TestAutomationSession withPlatformVersion(String platformVersion) {
        commonCapabilities.platformVersion = platformVersion;
        return this;
    }

    public TestAutomationSession withBrowserName(String browserName) {
        commonCapabilities.browserName = browserName;
        return this;
    }

    public TestAutomationSession withVirtualDevice() {
        commonCapabilities.useVirtualDevice = true;
        return this;
    }

    public TestAutomationSession withVirtualDevice(String avdName) {
        commonCapabilities.avd = avdName;
        return this;
    }

    public TestAutomationSession withDeviceName(String deviceName) {
        commonCapabilities.deviceName = deviceName;
        if (commonCapabilities.isLocalExecution() && commonCapabilities.udid == null)
            commonCapabilities.udid = deviceName;
        return this;
    }
    public TestAutomationSession withDeviceModel(String deviceModel) {
        commonCapabilities.model = deviceModel;
        return this;
    }

    public TestAutomationSession withAppPath(String appPath) {
        commonCapabilities.app = appPath;
        return this;
    }

    public TestAutomationSession withAppId(String appId) {
        this.appId = appId;
        if (commonCapabilities.platformName.equals(MobilePlatform.ANDROID))
            commonCapabilities.appPackage = appId;
        else
            commonCapabilities.bundleId = appId;
        return this;
    }

    public TestAutomationSession withAutoLaunch(boolean autoLaunch) {
        commonCapabilities.autoLaunch = autoLaunch;
        return this;
    }

    public TestAutomationSession withAppActivity(String appActivity) {
        commonCapabilities.appActivity = appActivity;
        return this;
    }

    public TestAutomationSession withScriptName(String scriptName) {
        commonCapabilities.scriptName = scriptName;
        return this;
    }

    public TestAutomationSession withScreenshotOnError() {
        commonCapabilities.screenshotOnError = true;
        return this;
    }

    public TestAutomationSession withScreenshotOnError(boolean value) {
        commonCapabilities.screenshotOnError = value;
        return this;
    }

    public TestAutomationSession withTakesScreenshot() {
        commonCapabilities.takesScreenshot = true;
        return this;
    }

    public TestAutomationSession withTakesScreenshot(boolean value) {
        commonCapabilities.takesScreenshot = value;
        return this;
    }

    public TestAutomationSession withEnabledAppiumBehavior() {
        commonCapabilities.enableAppiumBehavior = true;
        return this;
    }

    public TestAutomationSession withEnabledAppiumBehavior(boolean state) {
        commonCapabilities.enableAppiumBehavior = state;
        return this;
    }

    public TestAutomationSession withUseAppiumForWeb() {
        commonCapabilities.useAppiumForWeb = true;
        return this;
    }

    public TestAutomationSession withUseAppiumForWeb(boolean value) {
        commonCapabilities.useAppiumForWeb = value;
        return this;
    }

    public TestAutomationSession withUseAppiumForHybrid() {
        commonCapabilities.useAppiumForHybrid = true;
        return this;
    }

    public TestAutomationSession withUseAppiumForHybrid(boolean value) {
        commonCapabilities.useAppiumForHybrid = value;
        return this;
    }

    public TestAutomationSession withAppiumOption(String optionName, Object optionValue) {
        commonCapabilities.addAppiumOption(optionName, optionValue);
        return this;
    }

    public TestAutomationSession withAppiumOptions(HashMap<String, ?> options) {
        commonCapabilities.addAppiumOptions(options);
        return this;
    }

    public TestAutomationSession withPerfectoOption(String optionName, Object optionValue) {
        commonCapabilities.addPerfectoOption(optionName, optionValue);
        return this;
    }

    public TestAutomationSession withPerfectoOptions(HashMap<String, ?> options) {
        commonCapabilities.addPerfectoOptions(options);
        return this;
    }

    public TestAutomationSession withAppiumServerVersion(String appiumServerVersion) {
        commonCapabilities.appiumVersion = appiumServerVersion;
        return this;
    }

    public TestAutomationSession withAppiumServerVersion_1_18_3() {
        commonCapabilities.appiumVersion = AppiumVersion.v1_18_3;
        return this;
    }

    public TestAutomationSession withAppiumServerVersion_1_20_2() {
        commonCapabilities.appiumVersion = AppiumVersion.v1_20_2;
        return this;
    }

    public TestAutomationSession withAppiumServerVersion_1_22_3() {
        commonCapabilities.appiumVersion = AppiumVersion.v1_22_3;
        return this;
    }

    public TestAutomationSession withAppiumServerVersion_2() {
        commonCapabilities.appiumVersion = AppiumVersion.v2;
        return this;
    }

    public TestAutomationSession withAutomationVersion(String automationVersion) {
        commonCapabilities.automationVersion = automationVersion;
        return this;
    }

    public TestAutomationSession withOpenDeviceTimeout(Integer minutes) {
        commonCapabilities.openDeviceTimeout = minutes;
        return this;
    }

    public TestAutomationSession withAutoInstrumentation() {
        commonCapabilities.autoInstrument = true;
        return this;
    }

    public TestAutomationSession withWebViewInstrumentation() {
        commonCapabilities.autoInstrument = true;
        return this;
    }

    public TestAutomationSession withIosResign() {
        commonCapabilities.iosResign = true;
        return this;
    }

    public TestAutomationSession withSensorInstrument() {
        commonCapabilities.sensorInstrument = true;
        return this;
    }

    public TestAutomationSession withSecureScreenInstrument() {
        commonCapabilities.secureScreenInstrument = true;
        return this;
    }

    public TestAutomationSession withSecurityToken(String token) {
        commonCapabilities.securityToken = token;
        return this;
    }

    public TestAutomationSession withUdid(String udid) {
        commonCapabilities.udid = udid;
        return this;
    }

    public TestAutomationSession withAutoGrantPermissions() {
        return withAutoGrantPermissions(true);
    }

    public TestAutomationSession withAutoGrantPermissions(boolean val) {
        commonCapabilities.autoGrantPermissions = val;
        return this;
    }

    public TestAutomationSession create() throws Exception {
        driver = new ExtendedMobileDriver(commonCapabilities);

        if (!commonCapabilities.isLocalExecution())
        {
            report = reportBuilder
                    .withDriver(driver)
                    .build();
        }
        return this;
    }

    public ExtendedMobileDriver getDriver() {
        return driver;
    }

    public UserActions getUserActions() {
        if (userActions == null)
            userActions = new UserActions(driver);

        return userActions;
    }

    public Application getApplication() {
        if (app == null)
            createApplication();

        return app;
    }

    private void createApplication() {

        app = new Application(driver);

        if (driver.isAndroid) {
            if (commonCapabilities.appPackage != null)
                app.id = commonCapabilities.appPackage;
            if (commonCapabilities.appActivity != null)
                app.activity = commonCapabilities.appActivity;
        } else {
            if (commonCapabilities.bundleId != null)
                app.id = commonCapabilities.bundleId;
        }

    }

    public void takeScreenshot() {
        driver.takeScreenshot();
    }

    public void saveScreenshot(String filePath) {
        try {
            driver.saveScreenshotFile(filePath);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    public TestAutomationSession withReportName(String reportName) {
        reportBuilder.withReportName(reportName);
        return this;
    }

    public TestAutomationSession withJobName(String jobName) {
        reportBuilder.withJobName(jobName);
        return this;
    }

    public TestAutomationSession withJobNumber(Integer jobNumber) {
        reportBuilder.withJobNumber(jobNumber);
        return this;
    }

    public TestAutomationSession withReportTags(String reportTags) {
        reportBuilder.withTags(reportTags);
        return this;
    }

    public Report getReport() {
        return report;
    }

    public void startReportForTest(String testName) {
        report.startTest(testName);
    }

    public void endReport() {
        report.endTest();
    }

    public void startReportStep(String stepName) {
        report.startStep(stepName);
    }

    public void endReportStep() {
        report.endStep();
    }

    public void end (Exception ex) {
        try {
            if (report.currentStepName != null)
                report.endStep();

            if (report.reportStarted)
                report.endTestWithFailure(ex, ex.getMessage());

            if (!driver.isLocal) {
                if (report.getUrl() != null) {
                    report.open();
                }
                else {
                    driver.openReport();
                }
            }
        } catch (Exception ex2) {
            logger.error(ex2.getMessage());
        } finally {
            driver.quit();
        }
    }

    public void end() {
        try {
            if (report.currentStepName != null)
                report.endStep();

            if (report.reportStarted)
                report.endTest();

            if (report.getUrl() != null) {
                report.open();
            }
            else {
                driver.openReport();
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        } finally {
            driver.quit();
        }

    }

    public TestAutomationSession withPlatformNameWindows() {
        commonCapabilities.platformName = Platform.WINDOWS.toString();
        return this;
    }

    public TestAutomationSession withPlatformWindows10() {
        commonCapabilities.platformName = Platform.WINDOWS.toString();
        commonCapabilities.platformVersion = "10";
        return this;
    }

    public TestAutomationSession withPlatformWindows11() {
        commonCapabilities.platformName = Platform.WINDOWS.toString();
        commonCapabilities.platformVersion = "11";
        return this;
    }

    public TestAutomationSession withResolution(String screenResolution) {
        commonCapabilities.resolution = screenResolution;
        return this;
    }

    public TestAutomationSession withResolution2560x1440() {
        commonCapabilities.resolution = "2560x1440";
        return this;
    }

    public TestAutomationSession withResolution1920x1080() {
        commonCapabilities.resolution = "1920x1080";
        return this;
    }

    public TestAutomationSession withPlatformNameMac() {
        commonCapabilities.platformName = Platform.MAC.toString();
        return this;
    }

    public TestAutomationSession withLocation(String location) {
        commonCapabilities.location = location;
        return this;
    }

}
