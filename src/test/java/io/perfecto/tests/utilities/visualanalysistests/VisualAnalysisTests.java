package io.perfecto.tests.utilities.visualanalysistests;

import io.perfecto.utilities.automationsession.TestAutomationSession;
import io.perfecto.utilities.extendedmobiledriver.ExtendedMobileDriver;
import io.perfecto.utilities.reporting.Report;
import io.perfecto.utilities.visualanalysis.*;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class VisualAnalysisTests {
    TestAutomationSession session;
    ExtendedMobileDriver driver;
    Report report;
    VisualAnalysisStepFactory stepFactory;
    EditTextSet editTextSetStep;
    TextButtonClick textButtonClick;
    TextCheckpoint textCheckpointStep;
    EditImageGet editImageGetStep;
    EditImageSet editImageSetStep;
    EditTextGet editTextGetStep;

    public VisualAnalysisTests() throws Exception {

        session = new TestAutomationSession()
            .withCloud("demo")
            .withPlatformVersion("12")
            .withPlatformNameAndroid()
            .create();

        driver = session.getDriver();
        report = session.getReport();

        stepFactory = new VisualAnalysisStepFactory(driver.getDriver());

        editTextSetStep = stepFactory.getEditTextSetStep();
        textButtonClick = stepFactory.getTextButtonClickStep();
        textCheckpointStep = stepFactory.getTextCheckpointStep();
        editImageSetStep = stepFactory.getEditImageSetStep();
        editImageGetStep = stepFactory.getEditImageGetStep();
        editTextGetStep = stepFactory.getEditTextGetStep();
    }

    @Test
    public void clickOnTextButton() throws Exception {

        report.startTest("Visual Analysis Test: TextButtonClick, EditTextSet");
        driver.goToUrl("http://the-internet.herokuapp.com/basic_auth");

        editTextSetStep
                .onLabel("Username")
                .setText("admin")
                .execute();

        textButtonClick
                .setLabel("Password")
                .setIndex(2)
                .withOcrGeneric("natural-language=true")
                .execute();

        editTextSetStep
            .onLabel("Password")
            .setText("admin")
            .execute();

        textButtonClick
            .setLabel("Sign In")
            .execute();

        Thread.sleep(2000);

        textCheckpointStep
            .forContent("Congratulations!")
            .execute();

        textCheckpointStep.isTextOnScreen("Powered by");

        session.end();
    }


    @Test
    public void test_Image_Get_Function() throws InterruptedException {

        report.startTest("Visual Analysis Test: EditImageSet, EditImageGet");

        driver.goToUrl("https://the-internet.herokuapp.com/inputs");

        Thread.sleep(3000);

        String expected = "1234";

        editImageSetStep
            .setNeedlePath("PUBLIC:Label_Number.png")
            .setText(expected)
            .setLabelDirection(LabelDirection.ABOVE)
            .setLabelOffset("8%")
            .setImageComparisonSize(ImageComparisonSize.BOUNDED)
            .tryExecute();

        // Click outside of the edit field to focus out
        textButtonClick.onLabel("Inputs");

        String actual = editImageGetStep
            .setNeedlePath("PUBLIC:Label_Number.png")
            .setLabelDirection(LabelDirection.ABOVE)
            .setImageComparisonSize(ImageComparisonSize.BOUNDED)
            .tryExecute();

        if (expected.equals(actual)) {
            report.endTest();
        }
        else {
            report.endTestWithFailureMessage(String.format("Expected value: %s is different from actual: %s", expected, actual));
        }

        session.end();
    }

    @Test
    public void test_Get_Special_Character() throws Exception {

        report.startTest("Visual Analysis Test: EditTextSet, EditTextGet");

        driver.goToUrl("https://the-internet.herokuapp.com/forgot_password");
        Thread.sleep(3000);

        String expected = "hristog@perfectomobile.com";

        editTextSetStep.
            onLabel("E-mail")
            .withLabelDirectionAbove()
            .withLabelOffsetInPercentages(1)
            .setText(expected)
            .execute();

        String actual = editTextGetStep
            .setLabel("E-mail")
            .setLabelDirection(LabelDirection.ABOVE)
            .setLabelOffset("5")
//            .setScreenSource(ScreenSource.CAMERA)
            .tryExecute();

        actual = editImageGetStep
            .setNeedlePath("PUBLIC:email.png")
            .setLabelDirection(LabelDirection.ABOVE)
            .setLabelOffset("5")
            .tryExecute();

        if (expected.equals(actual)) {
            report.endTest();
        }
        else {
            report.endTestWithFailureMessage(String.format("Expected value: %s is different from actual: %s", expected, actual));
        }

        session.end();
    }
}
