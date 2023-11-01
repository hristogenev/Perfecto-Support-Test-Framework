package io.perfecto.utilities.visualanalysis;

import io.perfecto.utilities.extendedmobiledriver.DriverUtils;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Identifies an edit field, based on an image label, and inserts the specified text in the value parameter into the field.
 * The field is in relation to the found label and is defined by the label position and offset parameters.
 */
public class FindImage {
    private final static Logger logger = LoggerFactory.getLogger(FindImage.class);
    private final RemoteWebDriver driver;
    private final static String script = "mobile:image:find";
    private final Map<String, Object> params = new HashMap<>();

    public FindImage(RemoteWebDriver driver) {
        this.driver = driver;
    }


    public FindImage setNeedlePath(String needlePath) {
        Objects.requireNonNull(needlePath);
        params.put("content", needlePath);
        return this;
    }

    public FindImage setSearchContext(String searchContext) {
        Objects.requireNonNull(searchContext);
        params.put("context", searchContext);
        return this;
    }

    public FindImage setMeasurement(String measurement) {
      Objects.requireNonNull(measurement);
      params.put("measurement", measurement);
      return this;
    }

    public FindImage setThreshold(int threshold) {
        params.put("threshold", threshold);
        return this;
    }

    public FindImage setNeedleComparisonSize(String comparisonSize) {
        params.put("match", comparisonSize);
        return this;
    }

    public String tryExecute() {
        try {
            return execute();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public String execute() {
        return (String)DriverUtils.executeScript(driver, script, params);
    }
}
