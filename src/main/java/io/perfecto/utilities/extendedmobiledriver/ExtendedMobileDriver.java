package io.perfecto.utilities.extendedmobiledriver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.remote.SupportsContextSwitching;
import io.perfecto.utilities.CommonProperties;
import io.perfecto.utilities.UrlUtils;
import io.perfecto.utilities.capabilities.CommonCapabilities;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.http.ClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


public class ExtendedMobileDriver<T extends AppiumDriver> {

    private final static Logger logger = LoggerFactory.getLogger(ExtendedMobileDriver.class);

    public CommonCapabilities capabilities;
    public final boolean isAndroid;
    public final boolean isIos;
    public boolean preferAppiumBehavior = true;
    public final boolean isLocal;
    private String urlString;
    private T driver;
    private Class _class;
    private JavascriptExecutor javascriptExecutor;
    public T getDriver() {
        return driver;
    }

    public ExtendedMobileDriver(CommonCapabilities capabilities) throws Exception {

        Objects.requireNonNull(capabilities);
        Objects.requireNonNull(capabilities.platformName);

        this.capabilities = capabilities;

        String url = UrlUtils.getUrl(capabilities.cloudName);
        isLocal = capabilities.isLocalExecution();

        Duration readTimeout = Duration.ofSeconds(
            Integer.valueOf((String) CommonProperties.getProperty("selenium.readTimeout.duration.seconds", "300")));

        ClientConfig clientConfig = ClientConfig.defaultConfig()
            .baseUri(URI.create(url))
            .readTimeout(readTimeout);

        switch (capabilities.platformName) {
            case MobilePlatform.ANDROID -> {
                logger.info("Creating AndroidDriver with URL: {}", url);
                _class = AndroidDriver.class;
                logger.info("************* CAPABILITIES *************");
                isAndroid = true;
                isIos = false;
                driver = (T) new AndroidDriver(clientConfig, (UiAutomator2Options) capabilities.toOptions());
            }
            case MobilePlatform.IOS -> {
                logger.info("Creating IOSDriver with URL: {}", url);
                _class = IOSDriver.class;
                isAndroid = false;
                isIos = true;
                logger.info("************* CAPABILITIES *************");
                driver = (T) new IOSDriver(clientConfig, (XCUITestOptions) capabilities.toOptions());
                logSessionDetails();
            }
            default -> {
                throw new Exception(String.format("Unsupported driver exception %s", capabilities.platformName.toString()));
            }
        }
        logSessionDetails();
    }

    private void logSessionDetails() {
        logger.info("Execution ID: {}", driver.getCapabilities().getCapability("executionId"));
    }

    public Object executeScript(String script, Map<?, ?> params) {
        return DriverUtils.executeScript(driver, script, params);
    }

    public void quit() {
        try {
            logger.info("Quitting driver. Please wait!");
            driver.quit();
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
    }


    public void openReport() {
        try {
            String reportUrl = (String) driver.getCapabilities().getCapability("testGridReportUrl");
            if (reportUrl == null) {
                logger.error("Report URL is not available");
                return;
            }
            logger.info("Report URL: {}", reportUrl.replace("[", "%5B").replace("]", "%5D"));
            java.awt.Desktop.getDesktop().browse(new URI(reportUrl));
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
    }

    public void takeScreenshot() {
        logger.info("Taking screenshot");
        driver.getScreenshotAs(OutputType.BASE64);
    }

    public void saveScreenshotFile(String filePath) throws IOException {
        logger.info("Saving screenshot to file {}", filePath);
        File screenshotFile = driver.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File(filePath));
    }

    public String tryGetPageSource() {
        String source = null;
        try {
            logger.info("Getting page source");
            source = driver.getPageSource();
            logger.info(source);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return source;
    }

    public Set<String> getContextHandles() {
        return ((SupportsContextSwitching)driver).getContextHandles();
    }

    public void printContextHandles() {
        logger.info("Available context handles:");
        for (String context : getContextHandles()) {
            logger.info(context);
        }
    }

    public void switchToNativeContext() {
        logger.info("Switching to NATIVE_APP context");
        ((SupportsContextSwitching) driver).context("NATIVE_APP");
    }

    /**
     * Switches to the first available WEBVIEW context
     * @throws Exception
     */
    public void switchToWebviewContext() throws Exception {
        for (String context : getContextHandles()) {
            if (context.toUpperCase().startsWith("WEBVIEW")) {
                logger.info("Switching to {} context", context);
                ((SupportsContextSwitching) driver).context(context);
                return;
            }
        }
        throw new Exception("Unable to switch to WEBVIEW context. There is none!");

    }

    /**
     * Switches to the first available WEBVIEW_chrome context
     * @throws Exception
     */
    public void switchToWebviewChromeContext() {
        logger.info("Switching to WEBVIEW_chrome context");
        ((SupportsContextSwitching) driver).context("WEBVIEW_chrome");
    }

    /**
     * Switches to the first available Chromium context
     * @throws Exception
     */
    public void switchToChromiumContext() {
        logger.info("Switching to Chromium context");
        ((SupportsContextSwitching) driver).context("CHROMIUM");
    }

    public void context(String context) {
        logger.info("Switching to {} context", context);
        ((SupportsContextSwitching) driver).context(context);
    }

    public void goToUrl(String urlString) {
        logger.info("Navigating to URL: {}", urlString);
        driver.get(urlString);
    }

    public String getWindowHandle() {
        logger.debug("Getting current window handle");
        String currentHandle = driver.getWindowHandle();
        logger.info("Current window handle: {}", currentHandle);
        return currentHandle;
    }

    public Set<String> getWindowHandles() {
        logger.debug("Getting all window handle");
        Set<String> handles = driver.getWindowHandles();
        logger.info("Got {} handles: {}", handles.size(), String.join(",", handles));
        return handles;
    }

    public void printWindowHandles() {
        logger.debug("Getting all window handle");
        Set<String> handles = driver.getWindowHandles();
        logger.info("Got {} handles: {}", handles.size(), String.join(",", handles));
    }

    public boolean switchToNextTab() {
        String currentHandle = getWindowHandle();
        Set<String> handles = getWindowHandles();
        for (String actual : handles) {
            if (!actual.equalsIgnoreCase(currentHandle)) {
                logger.info("Switching to next tab {}", actual);
                driver.switchTo().window(actual);
                return true;
            }
        }
        return false;
    }


    public boolean switchToTabByIndex(int index) {
        int currentIndex = 0;
        for (String actual : getWindowHandles()) {
            if (currentIndex == index) {
                logger.info("Switching to next tab {}", actual);
                driver.switchTo().window(actual);
                return true;
            }
            currentIndex++;
        }
        return false;
    }

    public JavascriptExecutor getJavascriptExecutor(){
        if (javascriptExecutor == null)
            javascriptExecutor = (JavascriptExecutor) driver;
        return javascriptExecutor;
    }

    public void printPageSource() {
        try {
            logger.info("Page source:\n" + driver.getPageSource());
        } catch (Exception ex) {
            logger.error("Error while extracting page source! " + ex.getMessage());
        }
    }
}