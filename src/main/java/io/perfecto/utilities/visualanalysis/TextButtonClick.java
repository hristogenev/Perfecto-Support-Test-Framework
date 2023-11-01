package io.perfecto.utilities.visualanalysis;

import io.perfecto.utilities.extendedmobiledriver.DriverUtils;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TextButtonClick {
    private final static Logger logger = LoggerFactory.getLogger(TextButtonClick.class);
    private final static String script = "mobile:button-text:click";
    private final RemoteWebDriver driver;
    private final Map<String, Object> params = new HashMap<>();

    public TextButtonClick(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public TextButtonClick setLabel(String label) {
        Objects.requireNonNull(label);
        params.put("label", label);
        return this;
    }

    public void onLabel(String label) {
        setLabel(label);
        execute();
    }

    public TextButtonClick setInterval(int interval) {
        params.put("interval", interval);
        return this;
    }

    public TextButtonClick setThreshold(int threshold) {
        params.put("threshold", threshold);
        return this;
    }

    public TextButtonClick setIgnoreCase(boolean ignoreCase) {
        String caseVal = ignoreCase ? "nocase" : "case";
        params.put("ignorecase", caseVal);
        return this;
    }

    public TextButtonClick setIndex(int index) {
        params.put("index", index);
        return this;
    }

    public TextButtonClick setLabelDirection(String labelDirection) {
        params.put("label.direction", labelDirection);
        return this;
    }

    public TextButtonClick setLabelOffset(String labelOffset) {
        params.put("label.offset", labelOffset);
        return this;
    }

    public TextButtonClick setLanguage(String language) {
        params.put("language", language);
        return this;
    }

    public TextButtonClick setSource(String screenSource) {
        params.put("source", screenSource);
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
        return (String) DriverUtils.executeScript(driver, script, params);
    }

}
