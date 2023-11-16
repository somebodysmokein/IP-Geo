package com.browserstack.test;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class IPGeolocation {

    public static final String AUTOMATE_USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    public static final String AUTOMATE_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    public static final String URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";

    public WebDriver driver=null;
    @Test
    public void testCosnoleAnnotations() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("browserVersion", "latest");
        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put("os", "OS X");
        browserstackOptions.put("osVersion", "Sonoma");
        browserstackOptions.put("browserVersion", "17.0");
        browserstackOptions.put("local", "false");
        browserstackOptions.put("debug", "true");
        browserstackOptions.put("networkLogs", "true");
        browserstackOptions.put("projectName", "IP Geolocation");
        browserstackOptions.put("buildName", "Test IP Geo");
        browserstackOptions.put("sessionName", "basic geo test");
        browserstackOptions.put("idleTimeout", 200);
        browserstackOptions.put("geoLocation", "ES");
        capabilities.setCapability("bstack:options", browserstackOptions);
        driver = new RemoteWebDriver(new URL(URL), capabilities);


        try {

            driver.get("http://ip-api.com/json");
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.withTimeout(Duration.ofSeconds(15));
            Thread.sleep(3000);
            markTestStatus("passed", "Tests ran succesfully", driver);
        } catch (Exception e) {
            markTestStatus("failed", "Some elements failed to load", driver);
        }
        driver.quit();
    }

    public static void markTestStatus(String status, String reason, WebDriver driver) {
        final JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"" + status + "\", \"reason\": \"" + reason + "\"}}");
    }
}
