# Perfecto-Support-Appium-Framework
Test automation framework for simple and quick creation of Appium tests. It contains multiple facade methods for Perfecto API that simplify the syntax. 

For example, running a test on an iOS mobile device: 

```
TestAutomationSession session = new TestAutomationSession()
    .withCloud("demo")
    .withPlatformNameAndroid()
    .withAppId("com.android.chrome")
    .withAutoGrantPermissions()
    .withAppActivity("com.google.android.apps.chrome.Main")
    .withDeviceName("0A211JEC219604")
    .withOpenDeviceTimeout(2)
    .create();

    UserActions ua = session.getUserActions();
    Application app = session.getApplication();
    Report report = session.getReport();

    report.startTest("Javascript click");

    ua.waitForAndClickOn(By.id("terms_accept"));
    ua.wait(1);
    app.close();

    session.end();
```

To run a local execution, simply use ```.withCloud("demo")```.
