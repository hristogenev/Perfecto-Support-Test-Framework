package io.perfecto.utilities.reporting;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class Report {
    public String title = "Test";
    public Integer jobNumber;
    public String jobName;
    public String projectName;
    public String projectVersion;
    public String branchName;
    public String[] tags;
    public Boolean done;
    private ReportiumClient reportiumClient;
    private final static Logger logger = LoggerFactory.getLogger(Report.class);

    private int stepCount = 0;
    public String currentStepName;
    private boolean isPerfectoReporting = false;

    public Report() {
    }

    public void startTest(String testName){
        logger.info("*******************************************************************");
        logger.info("*******************************************************************");
        logger.info("Starting test '{}'", testName);
        logger.info("*******************************************************************");
        logger.info("*******************************************************************");

        if (reportiumClient == null)
            return;

        reportiumClient.testStart(testName, new TestContext.Builder()
//                .withTestExecutionTags("Sanity", "Nightly")
//                .withCustomFields(new CustomField("version", "OS11"))
                .build());
        done = false;
    }

    public void startTest() {
        startTest(title);
    }

    /**
     * End test with success
     */
    public void endTest() {
        logger.info("*******************************************************************");
        logger.info("*******************************************************************");
        logger.info("Ending test '{}'", title);
        logger.info("*******************************************************************");
        logger.info("*******************************************************************");

        if (reportiumClient == null)
            return;

        reportiumClient.testStop(TestResultFactory.createSuccess());
        done = true;
    }

    /**
     * End test with failure
     */
    public void endTestWithFailure() {
        logger.info("*******************************************************************");
        logger.info("*******************************************************************");
        logger.info("Ending test '{}'", title);
        logger.info("*******************************************************************");
        logger.info("*******************************************************************");

        if (reportiumClient == null)
            return;

        reportiumClient.testStop(TestResultFactory.createFailure("Test failed!"));
        done = true;
    }

    /**
     * End test with custom error message
     * @param errorMessage
     */
    public void endTestWithFailureMessage(String errorMessage) {
        logger.info("*******************************************************************");
        logger.info("*******************************************************************");
        logger.info("Ending test '{}' with error message: {}", title, errorMessage);
        logger.info("*******************************************************************");
        logger.info("*******************************************************************");

        if (reportiumClient == null)
            return;

        reportiumClient.testStop(TestResultFactory.createFailure(errorMessage));
        done = true;
    }

    /**
     * End test with exception and failure reason
     * @param t
     * @param failureReason
     */
    public void endTestWithFailure(Throwable t, String failureReason) {
        logger.info("*******************************************************************");
        logger.info("*******************************************************************");
        logger.info("Ending test '{}' with failure reason '{}'", title, failureReason);
        logger.info("*******************************************************************");
        logger.info("*******************************************************************");
        if (reportiumClient == null)
            return;
        reportiumClient.testStop(TestResultFactory.createFailure(t.getMessage(), t, failureReason));
        done = true;
    }

    /**
     * Start step
     * @param stepName
     */
    public void startStep(String stepName) {
        currentStepName = stepName;
        logger.info("*******************************************************************");
        logger.info("Starting step with name '{}'", stepName);
        logger.info("*******************************************************************");
        stepCount++;
        if (reportiumClient == null)
            return;
        reportiumClient.stepStart(stepName);
    }

    /**
     * Start step with dummy name
     */
    public void startStep() {
        String stepName = String.format("Test step #{}", stepCount);
        currentStepName = stepName;
        logger.info("*******************************************************************");
        logger.info("*********** Starting step '{}' ***********", currentStepName);
        logger.info("*******************************************************************");
        stepCount++;
        logger.info("Starting step with name '{}'", stepName);
        if (reportiumClient == null)
            return;
        reportiumClient.stepStart(stepName);
    }

    public void endStep() {
        logger.info("*******************************************************************");
        logger.info("*********** Ending step '{}' ***********", currentStepName);
        logger.info("*******************************************************************");
        if (reportiumClient == null)
            return;
        reportiumClient.stepEnd();
        currentStepName = null;
    }

    public void addReportiumClient(ReportiumClient perfectoReportiumClient) {
        reportiumClient = perfectoReportiumClient;
        isPerfectoReporting = true;
    }

    public void open() {

        if (reportiumClient == null)
            return;

        try {
            String reportUrl = getUrl();
            logger.info("*******************************************************************");
            logger.info("Opening report URL {}", reportUrl.replace("[", "%5B").replace("]", "%5D"));
            logger.info("*******************************************************************");
            java.awt.Desktop.getDesktop().browse(new URI(reportUrl));
        } catch (Exception ex)
        {
            logger.error("Unable to open report in browser. {}", ex.getMessage());
        }
    }

    public String getUrl() {
        if (reportiumClient == null)
            return null;
        return reportiumClient.getReportUrl();
    }
}
