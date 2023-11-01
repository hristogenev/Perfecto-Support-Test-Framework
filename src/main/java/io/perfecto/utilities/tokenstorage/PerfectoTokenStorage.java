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

    public PerfectoTokenStorage() throws Exception {
        System.out.println(String.format("Initiated token storage from file: %s", tokensFilePath));
        createTokensMap();
    }

    public PerfectoTokenStorage(String overrideTokensFilePath) throws Exception {
        System.out.println(String.format("Initiated token storage from file: %s", tokensFilePath));
        tokensFilePath = overrideTokensFilePath;
        createTokensMap();
    }

    private static void createTokensMap() throws Exception {
        InputStream tokensFileContent = new FileInputStream(getTokenFilePath());
        tokens = new Properties();
        tokens.load(tokensFileContent);
    }

    private static String getTokenFilePath() {
        if (tokensFilePath != null)
            return tokensFilePath;

        tokensFilePath = CommonProperties.getProperty("perfecto.tokenstorage.filepath");
        if (tokensFilePath == null)
            tokensFilePath = System.getenv("PERFECTO_TOKENS_STORAGE");
        if (tokensFilePath == null)
            tokensFilePath = tokensFileName;

        return tokensFilePath;
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

    public static String getTokenFromFile(String overrideTokensFilePath, String cloudName) throws Exception {
        tokensFilePath = overrideTokensFilePath;
        createTokensMap();
        return getTokenForCloud(cloudName);
    }
}


