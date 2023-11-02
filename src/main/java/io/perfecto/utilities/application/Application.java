package io.perfecto.utilities.application;
import io.perfecto.utilities.extendedmobiledriver.ExtendedMobileDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Utility class to ease the operations with Applications
 * https://help.perfecto.io/perfecto-help/content/perfecto/automation-testing/application_functions.htm
 */
public class Application {
    private final ExtendedMobileDriver driver;
    private final static Logger logger = LoggerFactory.getLogger(Application.class);

    public String id;
    public String name;
    public String localPath;
    public String repositoryPath;
    public Duration startTimeout;
    public String activity;
    private Boolean resign;
    private Boolean webViewInstrumentation;
    private Boolean sensorInstrumentation;
    private Boolean secureScreenInstrumentation;
    private String certificateRepositoryPath;
    private String certificateUser;
    private String certificatePassword;
    private String certificateParams;

    public Application(ExtendedMobileDriver driver) {
        this.driver = driver;
    }
    public Application withId(String id) {
        this.id = id;
        return this;
    }

    public Application withName(String name) {
        this.name = name;
        return this;
    }

    public Application withLocalPath(String localPath) {
        this.localPath = localPath;
        return this;
    }

    public Application withRepositoryPath(String repositoryPath) {
        this.repositoryPath = repositoryPath;
        return this;
    }

    public Application withResign(){
        this.resign = true;
        return this;
    }

    public Application withWebViewInstrumentation(){
        this.webViewInstrumentation = true;
        return this;
    }

    public Application withSensorInstrumentation(){
        this.sensorInstrumentation = true;
        return this;
    }

    public Application withSecureScreenInstrumentation(){
        this.secureScreenInstrumentation = true;
        return this;
    }

    public Application withCertificate(String certificateRepositoryPath, String certeficateUser, String certificatPassword, String certificatParams){
        this.certificateRepositoryPath = certificateRepositoryPath;
        this.certificateUser = certeficateUser;
        this.certificatePassword = certificatPassword;
        this.certificateParams = certificatParams;
        return this;
    }

    public Application install() {

        Objects.requireNonNull(repositoryPath);

        Map<String, Object> params = new HashMap<>();
        params.put("file", repositoryPath);

        if (resign != null && resign)
            params.put("resign", "true");
        if (webViewInstrumentation != null && webViewInstrumentation)
            params.put("instrument", "instrument");
        if (sensorInstrumentation != null && sensorInstrumentation)
            params.put("sensorInstrument", "sensor");
        if (secureScreenInstrumentation != null && secureScreenInstrumentation)
            params.put("securedScreenInstrument", "true");
        if (certificateRepositoryPath != null)
            params.put("certificate.file", certificateRepositoryPath);
        if (certificateUser != null)
            params.put("certificate.user", certificateUser);
        if (certificateParams != null)
            params.put("certificate.params", certificateParams);
        if (certificatePassword != null)
            params.put("certificate.password", certificatePassword);

        String id = (String) driver.executeScript("mobile:application:install", params);
        if (this.id == null && id != null)
            this.id = id;

        return this;
    }

    public boolean install(String repositoryPath) {
        return install(repositoryPath, true);
    }

    public boolean install(String repositoryPath, boolean resign) {
        Objects.requireNonNull(repositoryPath);

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("file", repositoryPath);
            params.put("resign", resign);

            String id = (String) driver.executeScript("mobile:application:install", params);
            return id == null;
        } catch (Exception ex){
            logger.error(ex.getMessage());
            return false;
        }
    }

    public Application upload(boolean overwrite) throws IOException {
        ApplicationUtils.uploadApp(driver, localPath, repositoryPath, overwrite);
        return this;
    }


    public static boolean upload(ExtendedMobileDriver driver, String localPath, String repositoryPath, boolean overwrite) {
        return true;
    }

    public Application open() {
        String identifierType = "identifier";
        String identifierValue = id;
        if (name != null && !name.isEmpty()) {
            identifierType = "name";
            identifierValue = name;
        }
        ApplicationUtils.openApp(driver, identifierType, identifierValue, startTimeout);

        return this;
    }

    public Application tryOpen() {
        try {
            return open();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public void openAppById(String id){
        ApplicationUtils.openApp(driver, "identifier", id, startTimeout);
    }

    public void openAppByName(String name){
        ApplicationUtils.openApp(driver, "name", name, startTimeout);
    }

    public Application installAndOpen() {
        install();
        open();
        return this;
    }

    public Application restart() {
        open();
        close();
        return this;
    }

    public void restartAppWithId(String appId) {
        openAppById(appId);
        closeAppWithId(appId);
    }

    public void close() {

        String identifierType = "identifier";
        String identifierValue = id;

        if (name != null && !name.isEmpty()) {
            identifierType = "name";
            identifierValue = name;
        }

        ApplicationUtils.closeApp(driver, identifierType, identifierValue);
    }

    public void closeAppWithId(String id){
        ApplicationUtils.closeApp(driver, "identifier", id);
    }

    public static void closeAppWithId(ExtendedMobileDriver driver, String id) {
        ApplicationUtils.closeApp(driver, "identifier", id);
    }

    public void closeAppWithName(String name){
        ApplicationUtils.closeApp(driver, "name", name);
    }

    public static void closeAppWithName(ExtendedMobileDriver driver, String name) {
        ApplicationUtils.closeApp(driver, "name", name);
    }

    public boolean uninstall() {
        String identifierType = "identifier";
        String identifierValue = id;

        if (name != null && !name.isEmpty()) {
            identifierType = "name";
            identifierValue = name;
        }

        String result = (String) ApplicationUtils.uninstallApp(driver, identifierType, identifierValue);
        return result.equals("OK");
    }

    public Application activateApp(String appId) {
        id = appId;
        ApplicationUtils.activateApp(driver, appId);
        return this;
    }

    public Application activateApp() {
        ApplicationUtils.activateApp(driver, id);
        return this;
    }

    public boolean removeApp(String appId) {
        return ApplicationUtils.removeApp(driver, appId);
    }

    public boolean removeApp() {
        return ApplicationUtils.removeApp(driver, id);
    }

    public boolean isInstalled(String appId) {
        return ApplicationUtils.isInstalled(driver, appId);
    }

    public boolean isInstalled()  {
        return isInstalled(id);
    }

    public boolean tryUninstall() {
        try {
            String identifierType = "identifier";
            String identifierValue = id;

            if (name != null && !name.isEmpty()) {
                identifierType = "name";
                identifierValue = name;
            }

            String result = (String) ApplicationUtils.uninstallApp(driver, identifierType, identifierValue);
            return result.equals("OK");

        } catch (Exception ex)
        {
            logger.error(ex.getMessage());
        }
        return false;
    }

    public boolean uninstallAppWithId(String id) {
        String result = (String) ApplicationUtils.uninstallApp(driver, "identifier", id);
        return result.equals("OK");
    }

    public static boolean uninstallAppWithId(ExtendedMobileDriver driver, String id) {
        String result = (String) ApplicationUtils.uninstallApp(driver, "identifier", id);
        return result.equals("OK");
    }

    public static boolean uninstallAppWithName(ExtendedMobileDriver driver, String name) {
        String result = (String) ApplicationUtils.uninstallApp(driver, "name", name);
        return result.equals("OK");
    }

    public boolean uninstallAppWithName(String name) {
        String result = (String) ApplicationUtils.uninstallApp(driver, "name", name);
        return result.equals("OK");
    }

    /**
     * @param tag - The tag that is appended to the name of the audit report to help match it to the application screen.
     */
    public void checkAccessibilityAudit(String tag) {
        driver.executeScript("mobile:checkAccessibility:audit", Map.of("tag", tag));
    }
}
