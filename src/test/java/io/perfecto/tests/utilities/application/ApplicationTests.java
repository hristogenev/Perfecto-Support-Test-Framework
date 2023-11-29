package io.perfecto.tests.utilities.application;

import io.perfecto.tests.utilities.DriverCreationUtils;
import io.perfecto.utilities.application.Application;
import io.perfecto.utilities.application.ApplicationUtils;
import io.perfecto.utilities.automationsession.TestAutomationSession;
import io.perfecto.utilities.extendedmobiledriver.ExtendedMobileDriver;
import org.junit.Test;

public class ApplicationTests {

    @Test
    public void app_upload_install_and_start() throws Exception {

        TestAutomationSession session = new TestAutomationSession()
            .withPlatformNameAndroid()
            .withCloud("beta")
            .withDeviceName("R3CN206EJ7B")
            .create()
            ;

        try {

            ExtendedMobileDriver driver = session.getDriver();
            Application app = new Application(driver)
                .withLocalPath("//Users/hgenev/Applications/io.perfecto.webviewdemo-setWebContentsDebuggingEnabled.apk")
                .withRepositoryPath("PUBLIC:io.perfecto.webviewdemo-setWebContentsDebuggingEnabled.apk")
                .upload(true)
                .install()
                .open();

            session.end();

        } catch (Exception ex)
        {
            session.end(ex);
            ex.printStackTrace();
            throw ex;
        }
    }

    @Test
    public void app_upload() throws Exception {
        ExtendedMobileDriver driver = DriverCreationUtils.createDriverForPerfectoAndroidDevice();
        boolean result = ApplicationUtils.uploadApp(driver,
    "//Users/hgenev/Applications/io.perfecto.webviewdemo-setWebContentsDebuggingEnabled.apk",
"PUBLIC:io.perfecto.webviewdemo-setWebContentsDebuggingEnabled.apk",
    true);


        driver.quit();
        assert result;

    }

    @Test
    public void app_start_and_stop() throws Exception {
        ExtendedMobileDriver driver = DriverCreationUtils.createDriverForPerfectoAndroidDevice();
        Application app = new Application(driver)
                .withId("com.android.settings")
                .open();
        app.close();
        driver.takeScreenshot();
        driver.quit();
    }
}
