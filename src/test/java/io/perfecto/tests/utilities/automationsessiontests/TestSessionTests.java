package io.perfecto.tests.utilities.automationsessiontests;

import io.perfecto.tests.utilities.CommonProperties;
import io.perfecto.utilities.application.*;
import io.perfecto.utilities.reporting.Report;
import io.perfecto.utilities.automationsession.TestAutomationSession;
import io.perfecto.utilities.useractions.UserActions;
import org.junit.Test;
import org.openqa.selenium.By;

public class TestSessionTests {

    @Test
    public void testSessionCreationOnPerfecto() throws Exception {
        TestAutomationSession session = new TestAutomationSession()
                .withCloud("demo")
                .withPlatformNameAndroid()
                .withPlatformVersion("14")
                .withVirtualDevice()
                .withDeviceModel("pixel 6")
                .withAppId("com.android.settings")
                .withAutoLaunch(false)
                .create();

        UserActions user = session.getUserActions();
        Application app = session.getApplication();
        Report report = session.getReport();

        report.startTest("Test session tests");
        report.startStep("Open app");
        app.open();
        report.endStep();

        report.startStep("Click and type");
        user.waitForAndClickOn(By.id("search_action_bar_title"));
        user.waitForAndType(By.id("com.google.android.settings.intelligence:id/open_search_view_edit_text"), "wifi");
        report.endStep();

        report.startStep("Close app");
        app.close();
        report.endStep();

        report.endTest();

        session.end();
    }


    @Test
    public void testSessionCreationOnLocalEmulator() throws Exception {
        TestAutomationSession session = new TestAutomationSession()
                .withPlatformNameAndroid()
                .withVirtualDevice(CommonProperties.getProperty("local.default.avd.name"))
                .withDeviceModel("pixel 6")
                .withAppId("com.android.settings")
                .withAutoLaunch(false)
                .create();

        UserActions user = session.getUserActions();
        Application app = session.getApplication();
        Report report = session.getReport();

        report.startTest("Test session tests");
        report.startStep("Open app");
        app.open();
        report.endStep();

        report.startStep("Click and type");
        user.waitForAndClickOn(By.id("search_action_bar_title"));
        user.waitForAndType(By.id("com.google.android.settings.intelligence:id/open_search_view_edit_text"), "wifi");
        report.endStep();

        report.startStep("Close app");
        app.close();
        report.endStep();

        report.endTest();

        session.end();
    }
}
