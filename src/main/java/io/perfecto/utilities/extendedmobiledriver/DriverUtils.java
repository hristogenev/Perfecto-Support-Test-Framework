package io.perfecto.utilities.extendedmobiledriver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.perfecto.utilities.LoggerUtils;
import io.perfecto.utilities.application.Application;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URI;
import java.util.Map;
import java.util.Set;

public class DriverUtils {
    private final static Logger logger = LoggerFactory.getLogger(DriverUtils.class);

    public static Object executeScript(RemoteWebDriver driver, String script, Map<?, ?> params) {
        logger.info("********************************************");
        logger.info("Executing script '{}' with params:", script);
        for (Map.Entry<?,?> param: params.entrySet()) {
            logger.info("  {}: {}", param.getKey().toString(), param.getValue().toString());
        }

        Object result = driver.executeScript(script, params);
        logger.info("Return value: {}", result);
        return result;
    }

    public static void quit(AppiumDriver driver) {
        try {
            logger.info("Quitting driver");
            String reportUrl = (String) driver.getCapabilities().getCapability("testGridReportUrl");
            driver.quit();
            logger.info("Report URL: {}", reportUrl);
            java.awt.Desktop.getDesktop().browse(new URI(reportUrl));
        }
        catch (Exception ex)
        {
            logger.error(ex.toString());
        }
    }

    public static void saveScreenshotFile(IOSDriver driver, String filePath) {
        try {
            logger.info("Saving screenshot to file {}", filePath);
            File screenshotFile = driver.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile , new File(filePath));
        } catch (Exception ex)
        {
            logger.error(ex.getMessage());
        }
    }
}
