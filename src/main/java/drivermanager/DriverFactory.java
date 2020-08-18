package drivermanager;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.LoggerFactory;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
    private static WebDriver driver;

    public static WebDriver getWebDriver(){
        if (driver == null || ((RemoteWebDriver) driver).getSessionId() == null) {
            startWebDriver();
        }
        return driver;
    }

    private static WebDriver startWebDriver() {
        String browser = "chrome"; //System.getProperty("webdriver.driver");

        switch (browser.toLowerCase()) {
            case "remote":
                initRemoteDriver(System.getProperty("remote.url"), System.getProperty("remote.driver"));
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            case "ie":
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
                break;
            case "safari":
                driver = new SafariDriver();
                break;
            default:
//            case "chrome":
                WebDriverManager.chromedriver().setup();
                final ChromeOptions chromeOptions = new ChromeOptions();
                //Start Chrome with incognito mode
                chromeOptions.addArguments("incognito");
                //Start Chrome with headless mode
                //chromeOptions.addArguments("headless");
                chromeOptions.addArguments("disable-extensions");
                chromeOptions.addArguments("disable-popup-blocking");
                chromeOptions.addArguments("disable-infobars");
                //Running with virtual devices
//                Map<String, String> mobileEmulation = new HashMap<>();
//                mobileEmulation.put("deviceName", "Nexus 5");
//                chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                driver = new ChromeDriver(chromeOptions);
        }

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().setSize(new Dimension(1280, 1024));
        return driver;
    }

    private static void initRemoteDriver(String url, String browser) {
        try {
            final DesiredCapabilities desiredCapabilities;
            if (browser.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                desiredCapabilities = DesiredCapabilities.chrome();
            } else {
                WebDriverManager.firefoxdriver().setup();
                desiredCapabilities = DesiredCapabilities.firefox();
            }
            driver = new RemoteWebDriver(new URL(url), desiredCapabilities);
        } catch (Exception ex) {
            LoggerFactory.getLogger("DriverFactory").error(ex.getMessage());
        }
    }
}
