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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public boolean isPerfectExecution() {
        cloudName = cloudName.toLowerCase();
        return cloudName != null && !cloudName.equals("local") && !cloudName.equals("localhost") && !cloudName.equals("127.0.0.1") && !cloudName.startsWith("http");
    }

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
    @AppiumOption
    public Boolean showChromedriverLog;
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
    private Map<String, Object> appiumOptionsMap;
    private Map<String, Object> perfectoOptionsMap;
    private final List<Field> perfectoFields = new ArrayList<>() {};
    private final List<Field> appiumFields = new ArrayList<>() {};

    public CommonCapabilities() {
        buildFieldsMap();
    }

    public CommonCapabilities(String cloudName) {
        this();
        if (!cloudName.isBlank())
            this.cloudName = cloudName;
    }

    private void buildFieldsMap() {
        logger.debug("Building fields maps");
        for (Field field : getClass().getDeclaredFields()) {
            try {
                Annotation[] annotations = field.getDeclaredAnnotations();
                if(annotations.length == 0)
                    continue;

                for (Annotation annotation : annotations) {
                    Class<? extends Annotation> type = annotation.annotationType();

                    if (type == AppiumOption.class){
                        appiumFields.add(field);
                    } else if (type == PerfectoOption.class) {
                        perfectoFields.add(field);
                    }
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    public Map<String, ?> getAppiumOptionsMap() {
        buildAppiumOptionsMap();
        printOptions("appium", appiumOptionsMap);
        return appiumOptionsMap;
    }

    private void printOptions(String optionsTypeName, Map<String, Object> options) {
        if (options == null)
            return;

        for (Map.Entry<String, ?> option : options.entrySet()) {
            logger.info("{}:{} = {}", optionsTypeName, option.getKey(), option.getValue());
        }
    }

    public Map<String, ?> getPerfectoOptionsMap() {
        buildPerfectoOptionsMap();
//        printOptions("perfecto", perfectoOptionsMap);
        return perfectoOptionsMap;
    }

    private void setPerfectoCapabilities() {
        if (isLocalExecution())
            return;
        
        if (uiAutomator2Options != null) {
            uiAutomator2Options.setCapability("perfecto:options", getPerfectoOptionsMap());
            uiAutomator2Options.setAutomationName(automationName);
        }

        if(xcuiTestOptions != null) {
            xcuiTestOptions.setCapability("perfecto:options", getPerfectoOptionsMap());
        }

        if(appiumOptionsMap != null) {
            appiumOptionsMap.put("perfecto:options", getPerfectoOptionsMap());
        }
    }
    private void buildAppiumOptionsMap() {

        if (appiumOptionsMap == null)
            appiumOptionsMap = new HashMap<>();


        if (isLocalExecution() && platformName.equals(MobilePlatform.ANDROID)) {
            if (chromedriverExecutableDir == null && appiumOptionsMap.getOrDefault("chromedriverExecutableDir", null) == null ) {
                chromedriverExecutableDir = CommonProperties.getProperty("local.chromedriverExecutableDir");
            }
            if (chromedriverExecutable == null && appiumOptionsMap.getOrDefault("chromedriverExecutable", null) == null ) {
                chromedriverExecutable = CommonProperties.getProperty("local.chromedriverExecutable");
            }
        }

        for (Field field : appiumFields) {
            try {
                String appiumOptionName = field.getName();
                Object appiumOptionValue = field.get(this);

                if (appiumOptionValue == null || appiumOptionsMap.getOrDefault(appiumOptionName, null) != null)
                    continue;

                appiumOptionsMap.put(appiumOptionName, appiumOptionValue);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void buildPerfectoOptionsMap() {

        if (!isPerfectExecution())
            return;

        if (perfectoOptionsMap == null)
            perfectoOptionsMap = new HashMap<>();

        try {
            if (securityToken == null)
                securityToken = new PerfectoTokenStorage().getTokenForCloud(cloudName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        for (Field field : perfectoFields) {
            try {
                String name = field.getName();
                Object value = field.get(this);
                if (value == null || perfectoOptionsMap.getOrDefault(name, null) != null)
                    continue;;
                perfectoOptionsMap.put(name, value);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    public void addAppiumOption(String optionName, Object optionValue) {

        if (appiumOptionsMap == null)
            appiumOptionsMap = new HashMap<>();

        appiumOptionsMap.put(optionName, optionValue);
    }

    public void addAppiumOptions(HashMap<String, ?> options) {

        if (appiumOptionsMap == null)
            appiumOptionsMap = new HashMap<>();

        for (Map.Entry<String, ?> option: options.entrySet())
            appiumOptionsMap.put(option.getKey(), option.getValue());
    }


    public void addPerfectoOption(String optionName, Object value) {

        if (isLocalExecution())
            return;

        if (perfectoOptionsMap == null)
            perfectoOptionsMap = new HashMap<>();

        perfectoOptionsMap.put(optionName, value);

    }

    public void addPerfectoOptions(HashMap<String, ?> options) {

        if (isLocalExecution())
            return;

        for (Map.Entry<String, ?> option: options.entrySet())
            perfectoOptionsMap.put(option.getKey(), option.getValue());
    }


    public T toOptions()  {

        switch (platformName) {
            case MobilePlatform.IOS -> {
                xcuiTestOptions = new XCUITestOptions(getAppiumOptionsMap());
                setPerfectoCapabilities();
                return (T) xcuiTestOptions;
            }
            case MobilePlatform.ANDROID -> {
                uiAutomator2Options = new UiAutomator2Options(getAppiumOptionsMap());
                setPerfectoCapabilities();
                return (T) uiAutomator2Options;
            }
            default -> {
                throw new RuntimeException("Not supported platformName " + platformName);
            }
        }
    }
}
