package io.perfecto.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class LoggerUtils {
    private final static Logger logger = LoggerFactory.getLogger(LoggerUtils.class);
    public static void logSessionDetails(String executionId) {
        logger.info("Execution ID: {}", executionId);
    }

}
