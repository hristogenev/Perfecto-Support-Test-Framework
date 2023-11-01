package io.perfecto.utilities.application;

import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.appmanagement.AndroidTerminateApplicationOptions;
import io.appium.java_client.appmanagement.BaseTerminateApplicationOptions;
import io.appium.java_client.ios.IOSDriver;
import io.perfecto.utilities.extendedmobiledriver.DriverUtils;
import io.perfecto.utilities.extendedmobiledriver.ExtendedMobileDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
//import org.apache.http.entity.mime.MultipartEntityBuilder;
//import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationUtils {
    private final static Logger logger = LoggerFactory.getLogger(ApplicationUtils.class);

    public static void openApp(ExtendedMobileDriver driver, String identifierType, String identifierValue, Duration startTimeout) {

        logger.info("Opening app with {} {}", identifierType, identifierValue);
        Objects.requireNonNull(driver);
        Objects.requireNonNull(identifierType);
        Objects.requireNonNull(identifierValue);

        if (driver.isLocal) {
            ((InteractsWithApps) driver.getDriver()).activateApp(identifierValue);
        }

        Map<String, Object> params = new HashMap<>();
        params.put(identifierType, identifierValue);
        if (startTimeout != null)
            params.put("timeout", startTimeout.toSeconds());

        DriverUtils.executeScript(driver.getDriver(), "mobile:application:open", params);
    }


    public static void closeApp(ExtendedMobileDriver driver, String identifierType, String identifierValue) {

        logger.info("Closing app with {}: {}", identifierType, identifierValue);
        try {
            Objects.requireNonNull(driver);
            Objects.requireNonNull(identifierType);
            Objects.requireNonNull(identifierValue);

            if (driver.isLocal) {
                if (driver.isAndroid) {
                    BaseTerminateApplicationOptions options= new AndroidTerminateApplicationOptions().withTimeout(Duration.ofSeconds(5));
                    ((AndroidDriver) driver.getDriver()).terminateApp(identifierValue, options);
                } else {
                    ((InteractsWithApps) driver.getDriver()).terminateApp(identifierValue);
                }
                return;
            }

            Map<String, Object> params = new HashMap<>();
            params.put(identifierType, identifierValue);

            DriverUtils.executeScript(driver.getDriver(), "mobile:application:close", params);
        } catch (Exception ex)
        {
            logger.error(ex.toString());
        }
    }

    public static String installApp(ExtendedMobileDriver driver, String path) {

        logger.info("Installing app from path: {}", path);
        Objects.requireNonNull(driver);
        Objects.requireNonNull(path);

        Map<String, Object> params = new HashMap<>();
        if (driver.isLocal) {
            ((InteractsWithApps) driver.getDriver()).installApp(path);
            return null;
        }

        return (String) DriverUtils.executeScript(driver.getDriver(), "mobile:application:install", params);
    }

    public static boolean uploadApp(ExtendedMobileDriver driver, String localPath, String repositoryPath, boolean overwrite) throws IOException {

        Objects.requireNonNull(localPath);
        Objects.requireNonNull(repositoryPath);
        logger.debug("Uploading local application: {} to {}, overwrite={}", localPath, repositoryPath, overwrite);

        String endpointUrl = "https://" + driver.capabilities.cloudName + ".app.perfectomobile.com/repository/api/v1/artifacts";
        logger.debug("Using endpoint: {}", endpointUrl);

//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        File file = new File(localPath);
//        FileBody filebody = new FileBody(file, ContentType.DEFAULT_BINARY);
//        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
//        multipartEntityBuilder.addBinaryBody("inputStream", new File(localPath));
//        multipartEntityBuilder.addTextBody("requestPart",
//                "{\"artifactLocator\":\""+repositoryPath+"\", \"mimeType\":\"multipart/form-data\", \"override\": "+overwrite+", \"artifactType\": \""+driver.capabilities.platformName+"\"}");
//        HttpEntity mutiPartHttpEntity = multipartEntityBuilder.build();

//        RequestBuilder requestbuilder = RequestBuilder.post(endpointUrl);
//        requestbuilder.addHeader("Content-Type", "multipart/form-data");
//        requestbuilder.addHeader("Perfecto-Authorization", driver.capabilities.securityToken);
//        requestbuilder.setEntity(mutiPartHttpEntity);
//
//        HttpUriRequest multipartRequest = requestbuilder.build();
//        HttpResponse httpresponse = httpclient.execute(multipartRequest);

//        int code = httpresponse.getStatusLine().getStatusCode();
//        logger.debug(httpresponse.getStatusLine().toString());
//        logger.debug(EntityUtils.toString(httpresponse.getEntity()));

//        return code == 200;
        return true;
    }

    public static Object uninstallApp(ExtendedMobileDriver driver, String identifierType, String identifierValue) {

        logger.info("Uninstalling app with {} {}", identifierType, identifierValue);
        Objects.requireNonNull(driver);
        Objects.requireNonNull(identifierType);
        Objects.requireNonNull(identifierValue);

        if (driver.isLocal) {
            ((InteractsWithApps) driver.getDriver()).removeApp(identifierValue);
        }

        Map<String, Object> params = new HashMap<>();
        params.put(identifierType, identifierValue);

        return (String) driver.executeScript("mobile:application:uninstall", params);
    }

    public static boolean isInstalled(ExtendedMobileDriver driver, String appId) {
        Objects.requireNonNull(driver);
        Objects.requireNonNull(appId);
        return ((InteractsWithApps)driver.getDriver()).isAppInstalled(appId);
    }

    public static boolean removeApp(ExtendedMobileDriver driver, String appId) {
        Objects.requireNonNull(driver);
        Objects.requireNonNull(appId);
        return ((InteractsWithApps)driver.getDriver()).removeApp(appId);
    }


    public static void activateApp(ExtendedMobileDriver driver, String appId) {
        Objects.requireNonNull(driver);
        Objects.requireNonNull(appId);
        ((InteractsWithApps) driver.getDriver()).activateApp(appId);
    }
}
