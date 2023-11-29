package io.perfecto.utilities.tokenstorage;

import io.perfecto.utilities.CommonProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class PerfectoTokenStorage {
    public static String tokensFilePath;
    private static String tokensFileName = "tokens.ini";
    private static Properties tokens;
    private final static Logger logger = LoggerFactory.getLogger(PerfectoTokenStorage.class);
    public PerfectoTokenStorage() throws Exception {
        createTokensMap();
    }

    public PerfectoTokenStorage(String overrideTokensFilePath) throws Exception {
        tokensFilePath = overrideTokensFilePath;
        createTokensMap();
    }

    public static String getTokenForCloud(String host) throws Exception {
        String cloudName = host.split("\\.")[0];
        String token = null;

        if (tokens == null)
            createTokensMap();

        token = tokens.getProperty(cloudName);
        if (token != null)
            return token;

        throw new Exception(String.format("Token for cloud %s NOT found in %s", cloudName, tokensFilePath));
    }

    private static void createTokensMap() throws Exception {
        InputStream tokensFileContent = getInputStream();
        tokens = new Properties();
        tokens.load(tokensFileContent);
    }

    private static InputStream getInputStream() throws FileNotFoundException {
        if (tokensFilePath != null)
        {
            logger.debug("Loading Perfecto tokens from file: " + tokensFilePath);
            return new FileInputStream(tokensFilePath);
        }

        tokensFilePath = CommonProperties.getProperty("perfecto.tokenstorage.filepath");
        if (tokensFilePath != null) {
            logger.debug("Using Perfecto tokens file as per perfecto.tokenstorage.filepath property in common.properties {}", tokensFilePath);
            return new FileInputStream(tokensFilePath);
        }

        tokensFilePath = System.getenv("PERFECTO_TOKENS_STORAGE");
        if (tokensFilePath != null) {
            logger.debug("Using Perfecto tokens file as per PERFECTO_TOKENS_STORAGE environment variable", tokensFilePath);
            return new FileInputStream(tokensFilePath);
        }

        tokensFilePath = tokensFileName;
        logger.debug("Using default Perfecto tokens.ini file in project resources folder" + tokensFilePath);
        return PerfectoTokenStorage.class.getClassLoader().getResourceAsStream("tokens.ini");
    }
}


