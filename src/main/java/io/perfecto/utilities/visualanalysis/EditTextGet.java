package io.perfecto.utilities.visualanalysis;

import io.perfecto.utilities.extendedmobiledriver.DriverUtils;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class EditTextGet {
    private final static Logger logger = LoggerFactory.getLogger(EditTextGet.class);
    private final RemoteWebDriver driver;
    private final static String script = "mobile:edit-text:get";
    private final Map<String, Object> params = new HashMap<>();

    public EditTextGet(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public EditTextGet setLabel(String label) {
        params.put("label", label);
        return this;
    }

    /**
     * Set the number of text lines to be retrieved from the edit field.
     * @param lines The number of text lines to be retrieved from the edit field.
     * @return
     */
    public EditTextGet setLines(int lines) {
        params.put("lines", lines);
        return this;
    }

    public EditTextGet setThreshold(int threshold) {
        params.put("threshold", threshold);
        return this;
    }

    public EditTextGet setLabelDirection(String labelDirection) {
        params.put("label.direction", labelDirection);
        return this;
    }

    public EditTextGet setScreenSource(String screenSource) {
        params.put("source", screenSource);
        return this;
    }

    public EditTextGet useNativeScreenSource() {
        setScreenSource(ScreenSource.NATIVE);
        return this;
    }

    public EditTextGet usePrimaryScreenSource() {
        setScreenSource(ScreenSource.PRIMARY);
        return this;
    }
    public EditTextGet useCameraScreenSource() {
        setScreenSource(ScreenSource.CAMERA);
        return this;
    }
    public EditTextGet useAutoScreenSource() {
        setScreenSource(ScreenSource.AUTO);
        return this;
    }

    public EditTextGet setLabelOffset(String labelOffset) {
        params.put("label.offset", labelOffset);
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
