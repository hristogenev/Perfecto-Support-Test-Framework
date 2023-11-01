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
public class EditImageSet {
    private final static Logger logger = LoggerFactory.getLogger(EditImageSet.class);
    private final RemoteWebDriver driver;
    private final static String script = "mobile:edit-image:set";
    private final Map<String, Object> params = new HashMap<>();

    public EditImageSet(RemoteWebDriver driver) {
        this.driver = driver;
    }

    /**
     * Set the image that appears on, or related to, the edit field.
     * Image should be in JPEG, PNG or BMP format.
     * @param imageNeedlePath the image
     * @return
     */
    public EditImageSet setNeedlePath(String imageNeedlePath) {
        Objects.requireNonNull(imageNeedlePath);
        params.put("label", imageNeedlePath);
        return this;
    }

    /**
     * Set the text
     * String - The text to insert in the edit field.
     * Secured String - The encoded text to insert in the edit field.
     * @param text The text can be in any language.
     * @return
     */
    public EditImageSet setText(String text) {
        Objects.requireNonNull(text);
        params.put("text", text);
        return this;
    }


//    public String textFromInputWithLabel(String label) {
//        withLabel(label);
//        return execute();
//    }
//
//    public String textFromInputWithLabel(String label, int lines) {
//        withLines(lines);
//        return textFromInputWithLabel(label);
//    }

    public EditImageSet setThreshold(int threshold) {
        params.put("threshold", threshold);
        return this;
    }

    public EditImageSet setLabelDirection(String labelDirection) {
        params.put("label.direction", labelDirection);
        return this;
    }

    public EditImageSet setLabelOffset(String labelOffset) {
        params.put("label.offset", labelOffset);
        return this;
    }

    public EditImageSet setImageComparisonSize(String comparisonSize) {
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
