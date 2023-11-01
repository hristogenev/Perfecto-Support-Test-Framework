package io.perfecto.utilities.capabilities;

import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.MobilePlatform;
import io.perfecto.utilities.CommonProperties;
import io.perfecto.utilities.tokenstorage.PerfectoTokenStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class CommonCapabilities<T> {

    private final static Logger logger = LoggerFactory.getLogger(CommonCapabilities.class);
    @AppiumOption
    @PerfectoOption
    public String app;
    @AppiumOption
    public String appActivity;
    @AppiumOption
    public String appPackage;
    @AppiumOption
    public String appWaitActivity;
    @AppiumOption
    public String appWaitPackage;
    @PerfectoOption
    public String appiumVersion;
    @PerfectoOption
    public Boolean audioPlayback;
    @AppiumOption
    public Boolean autoGrantPermissions;
    @AppiumOption
    public Boolean autoLaunch;
    @AppiumOption
    public String automationName;
    @PerfectoOption
    public String automationVersion;
    @PerfectoOption
    public Boolean autoInstrument;
    @AppiumOption
    public String avd;
    @PerfectoOption
    public Boolean baseAppiumBehavior;
    @AppiumOption
    public String browserName;
    @AppiumOption
    public String bundleId;
    public String cloudName = "local";
    @PerfectoOption
    public Boolean enableAppiumBehavior;
    @PerfectoOption
    @AppiumOption
    public String deviceName;
    @PerfectoOption
    public String deviceType;
    @PerfectoOption
    public String description;
    @AppiumOption
    public Boolean fullReset;
    @AppiumOption
    public String intentAction;
    @AppiumOption
    public String intentCategory;
    @AppiumOption
    public String intentFlags;
    @PerfectoOption
    public Boolean iosResign;
    public boolean isLocalExecution() {
        cloudName = cloudName.toLowerCase();
        return cloudName == null || cloudName.equals("local") || cloudName.equals("localhost") || cloudName.equals("127.0.0.1");
    }
    @AppiumOption
    public String language;
    @AppiumOption
    public String locale;
    @PerfectoOption
    public String location;
    @AppiumOption
    public String chromedriverExecutable;
    @AppiumOption
    public String chromedriverExecutableDir;
    @AppiumOption
    public String chromedriverChromeMappingFile;
    @PerfectoOption
    public String manifacturer;
    @PerfectoOption
    public String model;
    @PerfectoOption
    public String network;
    @PerfectoOption
    public Integer openDeviceTimeout;
    @AppiumOption
    public String optionalIntentArguments;
    @AppiumOption
    public String platformName;
    @AppiumOption
    public String platformVersion;
    @AppiumOption
    public Boolean printPageSourceOnFindFailure;
    public String processArguments;
    @PerfectoOption
    public String resolution;
    @AppiumOption
    public Integer newCommandTimeout;
    @AppiumOption
    public Boolean noReset;
    @AppiumOption
    public String orientation;

    /**
     * Whether to use Appium functions rather than Perfecto.
     * For example if set to true driver.activateApp() wil be called
     * instead of Perfecto's driver.executeScript("mobile:application:start");
     */
    public boolean preferAppiumBehavior;
    @PerfectoOption
    public Boolean sensorInstrument;
    @PerfectoOption
    public String securityToken;
    @PerfectoOption
    public String screenshotFormat;
    @PerfectoOption
    public String scriptName;
    @PerfectoOption
    public Boolean screenshotOnError;
    @PerfectoOption
    public Boolean secureScreenInstrument;
    @PerfectoOption
    public Boolean takesScreenshot;
    @AppiumOption
    public String udid;
    @PerfectoOption
    public Boolean useAppiumForHybrid;
    @PerfectoOption
    public Boolean useAppiumForWeb;
    @PerfectoOption
    public Boolean useVirtualDevice;
    @PerfectoOption
    public Boolean waitForPageLoad;
    private XCUITestOptions xcuiTestOptions;
    private UiAutomator2Options uiAutomator2Options;
    private Map<String, Object> appiumOptions;
    private Map<String, Object> perfectoOptions;
    private final List<Field> perfectoFields = new ArrayList<>() {};
    private final List<Field> appiumFields = new ArrayList<>() {};

    public CommonCapabilities() {
        buildFieldsMap();
    }

    public CommonCapabilities(String cloudName) throws Exception {
        this();
        if (!cloudName.isBlank())
            this.cloudName = cloudName;
    }

    private void buildFieldsMap() {
        logger.debug("Building fields map");
        for (Field field : getClass().getDeclaredFields()) {
            try {
                Annotation[] annotations = field.getDeclaredAnnotations();
                if(annotations.length == 0)
                    continue;

                for (Annotation annotation : annotations) {
                    Class<? extends Annotation> type = annotation.annotationType();

                    if (type == AppiumOption.class){
                        appiumFields.add(field);
                    }

                    if (type == PerfectoOption.class) {
                        perfectoFields.add(field);
                    }
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    public T toOptions() throws Exception {

        if (platformName.equals(MobilePlatform.IOS)) {
            xcuiTestOptions = new XCUITestOptions(getAppiumOptions());
            setPerfectoCapabilities();
            return (T) xcuiTestOptions;
        }

        if (platformName.equals(MobilePlatform.ANDROID)) {
            uiAutomator2Options = new UiAutomator2Options(getAppiumOptions());
            setPerfectoCapabilities();
            return (T) uiAutomator2Options;
        }

        throw new Exception("Not supported platformName " + platformName);
    }

    public Map<String, ?> getAppiumOptions() throws Exception {
        buildAppiumOptionsMap();
        return appiumOptions;
    }

    public Map<String, ?> getPerfectoOptions() throws Exception {
        buildPerfectoOptionsMap();
        return perfectoOptions;
    }

    private void setPerfectoCapabilities() throws Exception {
        if (isLocalExecution())
            return;
        
        if (uiAutomator2Options != null) {
            uiAutomator2Options.setCapability("perfecto:options", getPerfectoOptions());
            uiAutomator2Options.setAutomationName(automationName);
        }

        if(xcuiTestOptions != null) {
            xcuiTestOptions.setCapability("perfecto:options", getPerfectoOptions());
        }
    }
    private void buildAppiumOptionsMap() throws Exception {

        if (appiumOptions == null)
            appiumOptions = new HashMap<>();

        if (isLocalExecution() && platformName.equals(MobilePlatform.ANDROID) && chromedriverExecutableDir == null) {
            chromedriverExecutableDir = CommonProperties.getProperty("local.chromedriverExecutableDir");
        }

        for (Field field : appiumFields) {
            try {
                String name = field.getName();
                Object value = field.get(this);
                if (value == null)
                    continue;
                appiumOptions.put(name, value);
                logger.info("appium:{} = {}", name, value);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void buildPerfectoOptionsMap() throws Exception {

        if (isLocalExecution())
            return;

        if (perfectoOptions == null)
            perfectoOptions = new HashMap<>();

        if (securityToken == null) {
            securityToken = new PerfectoTokenStorage().getTokenForCloud(cloudName);
        }

        for (Field field : perfectoFields) {
            try {
                String name = field.getName();
                Object value = field.get(this);
                if (value == null)
                    continue;;
                perfectoOptions.put(name, value);
                logger.info("perfecto:{} = {}", name, value);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    public void addAppiumOption(String optionName, Object optionValue) {

        if (appiumOptions == null)
            appiumOptions = new HashMap<>();

        appiumOptions.put(optionName, optionValue);

    }

    public void addAppiumOptions(HashMap<String, ?> options) {

        if (appiumOptions == null)
            appiumOptions = new HashMap<>();

        for (Map.Entry<String, ?> option: options.entrySet())
            appiumOptions.put(option.getKey(), option.getValue());
    }


    public void addPerfectoOption(String optionName, Object value) {

        if (isLocalExecution())
            return;

        if (perfectoOptions == null)
            perfectoOptions = new HashMap<>();

        perfectoOptions.put(optionName, value);

    }

    public void addPerfectoOptions(HashMap<String, ?> options) {

        if (isLocalExecution())
            return;

        for (Map.Entry<String, ?> option: options.entrySet())
            perfectoOptions.put(option.getKey(), option.getValue());
    }
}
