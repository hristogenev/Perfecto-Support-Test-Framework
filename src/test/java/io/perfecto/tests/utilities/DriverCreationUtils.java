package io.perfecto.tests.utilities;

import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.perfecto.utilities.capabilities.CommonCapabilities;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.perfecto.utilities.extendedmobiledriver.ExtendedMobileDriver;

public class DriverCreationUtils {

    public static ExtendedMobileDriver createDriverForPerfectoAndroidDevice() throws Exception {
        CommonCapabilities capabilities = new CommonCapabilities<UiAutomator2Options>("demo");
        capabilities.platformName = MobilePlatform.ANDROID;
        capabilities.openDeviceTimeout = 2;
        capabilities.autoGrantPermissions = true;
        return new ExtendedMobileDriver(capabilities);
    }
    public static ExtendedMobileDriver createDriverForPerfectoIosDevice() throws Exception {
        CommonCapabilities capabilities = new CommonCapabilities<XCUITestOptions>("demo");
        capabilities.platformName = MobilePlatform.ANDROID;
        capabilities.openDeviceTimeout = Integer.parseInt(CommonProperties.getProperty("perfecto.capabilities.openDeviceTimeout"));
        return new ExtendedMobileDriver(capabilities);
    }

    public static ExtendedMobileDriver createDriverForLocalAndroidDevice() throws Exception {
        CommonCapabilities capabilities = new CommonCapabilities<UiAutomator2Options>("local");
        capabilities.platformName = MobilePlatform.ANDROID;
        return new ExtendedMobileDriver(capabilities);
    }
    public static ExtendedMobileDriver createDriverForLocalIosDevice() throws Exception {
        CommonCapabilities capabilities = new CommonCapabilities<XCUITestOptions>("local");
        capabilities.platformName = MobilePlatform.IOS;
        return new ExtendedMobileDriver(capabilities);
    }

    public static ExtendedMobileDriver createDriverForLocalAndroidEmulator() throws Exception {
        CommonCapabilities capabilities = new CommonCapabilities<UiAutomator2Options>("local");
        capabilities.platformName = MobilePlatform.ANDROID;
        capabilities.avd = CommonProperties.getProperty("local.default.avd.name");
        return new ExtendedMobileDriver(capabilities);
    }


    public static ExtendedMobileDriver createDriverForLocalIosSimulator() throws Exception {
        CommonCapabilities capabilities = new CommonCapabilities<XCUITestOptions>("local");
        capabilities.platformName = MobilePlatform.IOS;
//        capabilities.avd = Literals.defaultAvdName;
        return new ExtendedMobileDriver(capabilities);
    }

    public static ExtendedMobileDriver createDriverForPerfectoAndroidEmulator() throws Exception {
        CommonCapabilities capabilities = new CommonCapabilities<UiAutomator2Options>("demo");
        capabilities.platformName = MobilePlatform.ANDROID;
        capabilities.model = CommonProperties.getProperty("perfecto.emulators.default.model");
        capabilities.useVirtualDevice = true;
        return new ExtendedMobileDriver(capabilities);
    }

    public static ExtendedMobileDriver createDriverForPerfectoIosSimulator() throws Exception {
        CommonCapabilities capabilities = new CommonCapabilities<XCUITestOptions>("demo");
        capabilities.platformName = MobilePlatform.IOS;
        capabilities.model = CommonProperties.getProperty("perfecto.simulators.default.model");
        capabilities.useVirtualDevice = true;
        return new ExtendedMobileDriver(capabilities);
    }
}
