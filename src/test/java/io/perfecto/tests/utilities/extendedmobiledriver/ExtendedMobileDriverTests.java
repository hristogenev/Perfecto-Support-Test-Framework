package io.perfecto.tests.utilities.extendedmobiledriver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobilePlatform;
import io.perfecto.tests.utilities.CommonProperties;
import io.perfecto.utilities.capabilities.BrowserName;
import io.perfecto.utilities.capabilities.CommonCapabilities;
import io.perfecto.utilities.extendedmobiledriver.ExtendedMobileDriver;
import io.perfecto.utilities.reporting.Report;
import io.perfecto.utilities.reporting.ReportBuilder;
import io.perfecto.utilities.useractions.UserActions;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.junit.Assert.*;

public class ExtendedMobileDriverTests {


    @Test
    public void testCreateLocalAndroidDriver() {
        try {
            CommonCapabilities capabilities = new CommonCapabilities("local");
            capabilities.platformName = MobilePlatform.ANDROID;
            capabilities.avd = CommonProperties.getProperty("local.default.avd.name");
            capabilities.addAppiumOption("avdLaunchTimeout", 120000);
            ExtendedMobileDriver driver = new ExtendedMobileDriver(capabilities);

            driver.quit();
            assertEquals(driver.getDriver().getClass().getSimpleName(), "AndroidDriver");


        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }


    @Test
    public void testCreatePerfectoAndroidDriver() {
        try {
            CommonCapabilities capabilities = new CommonCapabilities<UiAutomator2Options>("demo");
            capabilities.platformName = MobilePlatform.ANDROID;
            capabilities.automationName = AutomationName.APPIUM;

            ExtendedMobileDriver driver = new ExtendedMobileDriver(capabilities);

            assertEquals(driver.getDriver().getClass().getSimpleName(), "AndroidDriver");

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreatePerfectoAndroidDriverAndOpenWebsite() throws Exception {

        CommonCapabilities capabilities = new CommonCapabilities<UiAutomator2Options>("demo");

        capabilities.platformName = MobilePlatform.ANDROID;
        capabilities.automationName = AutomationName.APPIUM;
        capabilities.platformVersion = "12";
        capabilities.enableAppiumBehavior = true;
        capabilities.useAppiumForWeb = true;
        capabilities.browserName = BrowserName.CHROME;

        ExtendedMobileDriver driver = new ExtendedMobileDriver<AndroidDriver>(capabilities);

        Report report = new ReportBuilder(driver)
            .withReportName("Open webpage and switch to Webview context")
            .withJobName("Perfecto.Utilities.Tests")
            .withJobNumber(1)
            .withTags("hristog,perfectoutilities")
            .build();

        report.startTest();
        AndroidDriver driver2 = (AndroidDriver)driver.getDriver();
        driver2.get("https://duckduckgo.com/");
//        driver.goToUrl("https://duckduckgo.com/");
        String title = driver.getDriver().getTitle();

        driver.printContextHandles();
        driver.context("CHROMIUM");

        UserActions ua = new UserActions(driver);
        ua.waitForAndType(By.cssSelector("input[name=q]"), "Perfecto");

        ua.clickOn(By.cssSelector("button[aria-label=Search]"));

        ua.wait(5);

        driver.takeScreenshot();

        report.endTest();
        driver.quit();

        assertTrue(title.contains("DuckDuckGo"));

        report.open();
    }


    @Test
    public void testCreatePerfectoIosDriver() {
        try {
            CommonCapabilities capabilities = new CommonCapabilities<UiAutomator2Options>("demo");
            capabilities.platformName = MobilePlatform.IOS;
//            capabilities.automationName = AutomationName.APPIUM;

            ExtendedMobileDriver driver = new ExtendedMobileDriver(capabilities);

            assertEquals(driver.getDriver().getClass().getSimpleName(), "IOSDriver");

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void test1() {

    }
}
