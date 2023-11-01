package io.perfecto.utilities.useractions;

import io.perfecto.utilities.extendedmobiledriver.ExtendedMobileDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@SuppressWarnings("unchecked")
public class UserActions {
    private static final String COMMAND_MOBILE_TOUCH_SWIPE = "mobile:touch:swipe";
    private static final String COMMAND_MOBILE_TAP = "mobile:touch:tap";
    private final ExtendedMobileDriver driver;
    private final static Logger logger = LoggerFactory.getLogger(UserActions.class);
    private TimeUnit timeUnit;
    private WebDriverWait wait;
    private int defaultWaitDurationInSeconds = 15;
    private final Map<String, Object> params = new HashMap<>();

    public UserActions(ExtendedMobileDriver driver) {
        this.driver = driver;
        timeUnit = TimeUnit.SECONDS;
        wait = new WebDriverWait(driver.getDriver(), Duration.ofSeconds(defaultWaitDurationInSeconds));
    }

    public void setWait(Duration duration) {
        wait = new WebDriverWait(driver.getDriver(), duration);
    }

    public WebElement findElement(By by) {
        logger.info("Getting element {}", by);
        return driver.getDriver().findElement(by);
    }

    public WebElement waitForElement(By by, int seconds) {
        if (seconds != defaultWaitDurationInSeconds)
            setWait(Duration.ofSeconds(seconds));
        logger.info("Waiting {} {} for element {}", defaultWaitDurationInSeconds, timeUnit, by);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public WebElement waitForElement(By by) {
        logger.info("Waiting {} {} for element {}", defaultWaitDurationInSeconds, timeUnit, by);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public WebElement tryWaitForElement(By by) {
        try {
            return waitForElement(by);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public WebElement tryWaitForElement(By by, int seconds) {
        try {
            return waitForElement(by, seconds);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }


    public boolean waitForAndClickOn(By by) {
        WebElement element = waitForElement(by);
        clickOnElement(element);
        return true;
    }

    public boolean waitForAndClickOn(By by, int seconds) {
        if (seconds != defaultWaitDurationInSeconds)
            setWait(Duration.ofSeconds(seconds));
        WebElement element = waitForElement(by);
        clickOnElement(element);
        return true;
    }

    public boolean tryWaitForAndClickOn(By by) {
        try {
            return waitForAndClickOn(by);
        } catch (Exception ex)  {
            logger.error(ex.getMessage());
            return false;
        }
    }

    public boolean tryWaitForAndClickOn(By by, int seconds) {
        try {
            return waitForAndClickOn(by, seconds);
        } catch (Exception ex)  {
            logger.error(ex.getMessage());
            return false;
        }
    }


    public WebElement waitForAndType(By selector, String text) {
        WebElement element = waitForElement(selector);
        typeInElement(element, text);
        return element;
    }

    public WebElement waitForAndType(By selector, String text, boolean clear) {
        WebElement element = waitForElement(selector);
        if (clear)
            clearElement(element);
        typeInElement(element, text);
        return element;
    }

    private void clearElement(WebElement element) {
        logger.info("Clearing element");
        element.clear();
        logger.debug("Element cleared");
    }

    public WebElement waitForAndType(By selector, String text, int seconds) {
        WebElement element = waitForElement(selector, seconds);
        typeInElement(element, text);
        return element;
    }

    public WebElement tryWaitForAndType(By selector, String text) {
        try
        {
            return waitForAndType(selector, text);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public WebElement tryWaitForAndType(By selector, String text, int seconds) {
        try {
            return waitForAndType(selector, text, seconds);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public boolean findElementAndType(By by, String text) {
        logger.info("Typing '{}' in element found by {}", text, by);
        typeInElement(driver.getDriver().findElement(by), text);
        return true;
    }

    public boolean typeInElement(WebElement element, String text) {
        logger.info("Typing '{}' in element {}", text, element);
        element.sendKeys(text);
        return true;
    }

    public boolean tryTypeInElement(WebElement element, String text) {
        try {
            return typeInElement(element, text);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    public boolean clickOn(By selector) {
        WebElement element = (WebElement) driver.getDriver().findElement(selector);
        logger.info("Clicking on element {}", element);
        element.click();
        return true;
    }

    public boolean tryClickOn(By selector) {
        try {
            return clickOn(selector);
        } catch (Exception ex){
            logger.error(ex.getMessage());
            return false;
        }
    }

    public boolean clickOnElement(WebElement element) {
        logger.info("Clicking on element {}", element);
        element.click();
        return true;
    }

    public boolean tryClickOnElement(WebElement element) {
        try {
            return clickOnElement(element);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    public boolean waitForAndClickOnTextContaining(String text) {
        String xpath = "//*[contains(@text,'" + text + "')]";
        return tryWaitForAndClickOn(By.xpath(xpath));
    }
    public boolean waitForAndClickOnText(String text) {
        String xpath = "//*[@text='" + text + "']|//*[@label='"+text+"']";
        return waitForAndClickOn(By.xpath(xpath));
    }


    public WebElement waitForElementWithText(String text) {
        String xpath = "//*[@text='" + text + "']|//*[@label='"+text+"']";
        return waitForElement(By.xpath(xpath));
    }

    public boolean clickOnLabel(String label) {
        String xpathString = "//*[@label='" + label + "']";
        logger.info("Clicking on element by text -> trying xpath {}", xpathString);
        WebElement element = driver.getDriver().findElement(By.xpath(xpathString));
        return true;
    }

    public boolean clickOnText(String text) {
        String xpathString = "//*[@text='" + text + "']|//*[@label='" + text + "']";
        logger.info("Clicking on element by label -> trying xpath {}", xpathString);
        WebElement element = driver.getDriver().findElement(By.xpath(xpathString));
        return true;
    }


    public boolean tryCickOnText(String text) {
        try {
            return clickOnText(text);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    public boolean tryWaitForAndClickOnText(String text) {
        String xpathString = "//*[@text = '" + text + "']";
        return tryWaitForAndClickOn(By.xpath(xpathString));
    }

    public boolean tryWaitForAndClickOnTextContaining(String text) {
        try {
            return waitForAndClickOnTextContaining(text);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    public boolean tryWaitForAndClickOnText(String text, int seconds) {
        if (seconds != defaultWaitDurationInSeconds)
            setWait(Duration.ofSeconds(seconds));
        return tryWaitForAndClickOnText(text);
    }


    public void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (Exception ex) {
        }
    }


    /**
     *
     * @param start The start, touch down, event coordinates. Format - "x,y" or "x%,y%"
     * @param end The end, touch up, event coordinates. Format - "x,y" or "x%,y%"
     * @param durationInSeconds The duration, in seconds (1-10), for performing the operation.
     */
    public void swipe(String start, String end, int durationInSeconds) {
        params.clear();
        params.put("start", start);
        params.put("end", end);
        params.put("duration", durationInSeconds);
        driver.executeScript(COMMAND_MOBILE_TOUCH_SWIPE, params);
    }

    public void swipeLeft() {
        swipe("80%,50%", "20%,50%", 1);
    }

    public void swipeRight() {
        swipe("20%,50%", "80%,50%", 1);
    }

    public void swipeUp() {
        swipe("50%,80%", "50%,20%", 1);
    }

    public void swipeDown() {
        swipe("50%,20%", "50%,80%", 1);
    }

    /**
     *
     * @param location The touch event coordinates. Format - either "x,y" or "x%,y%"
     * @param durationInSeconds The duration, in seconds, for performing the touch operation. Use this to perform a "long-press".
     */
    public void touch(String location, int durationInSeconds) {
        params.clear();
        params.put("start", location);
        params.put("duration", durationInSeconds);
        driver.executeScript(COMMAND_MOBILE_TAP, params);
    }

    public void home() {
        params.clear();
        driver.executeScript("mobile:handset:ready", params);
    }

    public boolean tryVisitUrl(String url) {
        try {
            driver.goToUrl(url);
            return true;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }
}
