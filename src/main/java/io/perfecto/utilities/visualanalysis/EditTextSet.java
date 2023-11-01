package io.perfecto.utilities.visualanalysis;

import io.perfecto.utilities.extendedmobiledriver.DriverUtils;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class EditTextSet {
    private final static Logger logger = LoggerFactory.getLogger(EditTextSet.class);
    private final RemoteWebDriver driver;
    private final static String script = "mobile:edit-text:set";

    private final Map<String, Object> params = new HashMap<>();

    public EditTextSet(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public EditTextSet onLabel(String label) {
        params.put("label", label);
        return this;
    }

    public EditTextSet setText(String text) {
        params.put("text", text);
        return this;
    }

    public EditTextSet withInterval(int interval) {
        params.put("interval", interval);
        return this;
    }

    public EditTextSet withThreshold(int threshold) {
        params.put("threshold", threshold);
        return this;
    }

    public EditTextSet withIgnoreCase() {
        params.put("ignoreCase", "nocase");
        return this;
    }

    public EditTextSet withIgnoreCase(boolean val) {
        params.put("ignoreCase", val ? "nocase" : "case");
        return this;
    }

    public EditTextSet withIndex(int index) {
        params.put("index", index);
        return this;
    }

    public EditTextSet withLabelDirectionAbove() {
        return setLabelDirection(LabelDirection.ABOVE);
    }

    public EditTextSet withLabelDirectionBelow() {
        return setLabelDirection(LabelDirection.BELOW);
    }

    public EditTextSet withLabelDirectionLeft() {
        return setLabelDirection(LabelDirection.LEFT);
    }

    public EditTextSet withLabelDirectionLeftmost() {
        return setLabelDirection(LabelDirection.LEFTMOST);
    }

    public EditTextSet withLabelDirectionRight() {
        return setLabelDirection(LabelDirection.RIGHT);
    }

    public EditTextSet withLabelDirectionRightmost() {
        return setLabelDirection(LabelDirection.RIGHTMOST);
    }

    public EditTextSet setLabelDirection(String labelDirection) {
        params.put("label.direction", labelDirection);
        return this;
    }

    public EditTextSet withLabelOffsetInPercentages(Integer labelOffset) {
        return setLabelOffset(labelOffset + "%");
    }

    public EditTextSet withLabelOffsetInPixels(Integer labelOffset) {
        return setLabelOffset(labelOffset.toString());
    }

    public EditTextSet setLabelOffset(String labelOffset) {
        params.put("label.offset", labelOffset);
        return this;
    }

    public EditTextSet withLanguage(String language) {
        params.put("language", language);
        return this;
    }

    public EditTextSet withSource(String screenSource) {
        params.put("screen", screenSource);
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
