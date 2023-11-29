//package io.perfecto.tests.utilities.application;
//
//import io.perfecto.utilities.application.Application;
//import io.perfecto.utilities.automationsession.TestAutomationSession;
//import io.perfecto.utilities.extendedmobiledriver.ExtendedMobileDriver;
//import io.perfecto.utilities.reporting.Report;
//import org.testng.ITestResult;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.Test;
//
//public class ApplicationTestsAndroid {
//
//  private TestAutomationSession session;
//  private Report report;
//
//  public void setUp() throws Exception {
//    TestAutomationSession session = new TestAutomationSession()
//        .withPlatformNameAndroid()
//        .withCloud("demo")
//        .create()
//        ;
//
//    report = session.getReport();
//
//  }
//
//  @AfterClass
//  public void tearDown() {
//    session.end();
//  }
//
//  @AfterMethod
//  public void testEnded(ITestResult result) {
//    if (result.getStatus() == ITestResult.FAILURE) {
//      report.endTestWithFailureMessage(result.getThrowable().getMessage());
//      return;
//    }
//
//    report.endTest();
//  }
//
//  @Test
//  public void app_upload_install_and_start() throws Exception {
//
//      ExtendedMobileDriver driver = session.getDriver();
//      Application app = new Application(driver)
//          .withLocalPath("//Users/hgenev/Applications/io.perfecto.webviewdemo-setWebContentsDebuggingEnabled.apk")
//          .withRepositoryPath("PUBLIC:io.perfecto.webviewdemo-setWebContentsDebuggingEnabled.apk")
//          .upload(true)
//          .install()
//          .open();
//  }
//}
