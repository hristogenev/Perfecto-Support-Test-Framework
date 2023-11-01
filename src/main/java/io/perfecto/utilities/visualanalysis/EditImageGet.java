package io.perfecto.utilities.visualanalysis;

import io.perfecto.utilities.extendedmobiledriver.DriverUtils;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Identifies an edit field, based on an image label, and retrieves its text value.
 * The value is returned as a string.
 */
public class EditImageGet {
    private final static Logger logger = LoggerFactory.getLogger(EditImageGet.class);
    private final RemoteWebDriver driver;
    private final static String script = "mobile:edit-image:get";
    private final Map<String, Object> params = new HashMap<>();

    public EditImageGet(RemoteWebDriver driver) {
        this.driver = driver;
    }

    /**
     * Set the image that appears on, or related to, the edit field.
     * Image should be in JPEG, PNG or BMP format.
     * @param needlePath the image
     * @return
     */
    public EditImageGet setNeedlePath(String needlePath) {
        Objects.requireNonNull(needlePath);
        params.put("label", needlePath);
        return this;
    }

    /**
     * Set the number of text lines to be retrieved from the edit field.
     * @param lines The number of text lines to be retrieved from the edit field.
     * @return
     */
    public EditImageGet setLines(int lines) {
        params.put("lines", lines);
        return this;
    }

    public String textFromInputWithLabel(String label) {
        setNeedlePath(label);
        return execute();
    }

    public String textFromInputWithLabel(String label, int lines) {
        setLines(lines);
        return textFromInputWithLabel(label);
    }

    public EditImageGet setThreshold(int threshold) {
        params.put("threshold", threshold);
        return this;
    }

    public EditImageGet setLabelDirection(String labelDirection) {
        params.put("label.direction", labelDirection);
        return this;
    }

    public EditImageGet setImageComparisonSize(String comparisonSize) {
        params.put("match", comparisonSize);
        return this;
    }

    public EditImageGet setLabelOffset(String offset) {
        params.put("label.offset", offset);
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
        String res = (String)DriverUtils.executeScript(driver, script, params);
        return res;
    }
}
