package io.perfecto.utilities.visualanalysis;

import io.perfecto.utilities.extendedmobiledriver.DriverUtils;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TextCheckpoint {
    private final static Logger logger = LoggerFactory.getLogger(TextButtonClick.class);
    private final RemoteWebDriver driver;
    private final static String script = "mobile:checkpoint:text";
    private final Map<String, Object> params;
    private String content;
    private String matchType;


    public TextCheckpoint(RemoteWebDriver driver) {
        this.driver = driver;
        params = new HashMap<>();
    }

    public TextCheckpoint forContent(String content) {
        this.content = content;
        return this;
    }

    public TextCheckpoint withMatchType(String matchType){
        this.matchType = matchType;
        return this;
    }

    public void validateTextIsOnScreen(String text) throws Exception {
        this.content = text;
        if (!((String)execute()).equals("true"))
            throw new Exception("Validation failed! Test '" + text + "' not found on screen!");
    }

    public boolean isTextOnScreen(String content) {
        this.content = content;
        return ((String)execute()).equals("true");
    }

    public Object tryExecute() {
        try {
            return execute();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public Object execute() {
        Objects.requireNonNull(content);
        params.clear();
        params.put("content", content);

        if (matchType != null)
            params.put("matchType", matchType);

        return DriverUtils.executeScript(driver, script, params);
    }
}
