package io.perfecto.utilities.reporting;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import io.appium.java_client.AppiumDriver;
import io.perfecto.utilities.extendedmobiledriver.ExtendedMobileDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class ReportBuilder {

    private final static Logger logger = LoggerFactory.getLogger(ReportBuilder.class);

//    private ExtendedMobileDriver driver;
    private RemoteWebDriver driver;
    private final Report report;
    private PerfectoExecutionContext context;

    public ReportBuilder() {
        logger.info("Building a new reporting client");
        report = new Report();
    }

    public ReportBuilder(ExtendedMobileDriver driver) {
        this();
        this.driver = driver.getDriver();
    }

    public ReportBuilder(RemoteWebDriver driver) {
        this();
        this.driver = driver;
    }

    public Report build() {
        Objects.requireNonNull(driver);

        PerfectoExecutionContext.PerfectoExecutionContextBuilder builder = new PerfectoExecutionContext.PerfectoExecutionContextBuilder();

        if (report.projectName != null) {
            logger.info("Project name: {}, version: {}", report.projectName, report.projectVersion);
            builder.withProject(new Project(report.projectName, report.projectVersion));
        }
        if (report.jobName != null) {
            logger.info("Job name: {}, number {}", report.jobName, report.jobNumber);
            Job job = new Job(report.jobName, report.jobNumber);
            if (report.branchName != null) {
                logger.info("Branch name: {}", report.branchName);
                job.withBranch("branch-name");
            }
            builder.withJob(job);
        }

        if (report.tags != null) {
            logger.info("Tags: {}", report.tags);
            builder.withContextTags(report.tags);
        }
//                .withCustomFields(new CustomField("programmer", "Samson"))
        builder.withWebDriver(driver);
        context = builder.build();
        ReportiumClient client = new ReportiumClientFactory().createPerfectoReportiumClient(context);
        report.addReportiumClient(client);
        logger.info("Reporting client created. Report will be available at: {}", report.getUrl().replace("[", "%5B").replace("]", "%5D"));

        return report;
    }

    public ReportBuilder withDriver(ExtendedMobileDriver driver){
        this.driver = driver.getDriver();
        return this;
    }

    public ReportBuilder withDriver(RemoteWebDriver driver){
        this.driver = driver;
        return this;
    }

    public ReportBuilder withReportName(String reportName) {
        report.title = reportName;
        return this;
    }
    public ReportBuilder withJobName(String jobName) {
        report.jobName = jobName;
        return this;
    }

    public ReportBuilder withJobNumber(int jobNumber) {
        report.jobNumber = jobNumber;
        return this;
    }

    public ReportBuilder withTags(String commaSeparatedTags) {
        report.tags = commaSeparatedTags.split(",");
        return this;
    }
}
