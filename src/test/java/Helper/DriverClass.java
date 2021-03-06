package Helper;

import com.browserstack.local.Local;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class DriverClass {
    public WebDriver driver;
    public Properties config;
    BufferedReader reader;
    private Local l;
    String browser;
    public static JavascriptExecutor jse;

    @Parameters(value = {"config","environment"})
    @BeforeMethod
    public void launch(String config_file, String environment) throws Exception {
        /*reader = new BufferedReader(new FileReader("./config/config.properties"));
        config = new Properties();
        try {
            config.load(reader);
            reader.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
        browser = config.getProperty("browser");

        if(browser.contains("Chrome")){
            //For Chrome Browser
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }else if(browser.contains("Firefox")){
            //For Firefox Browser
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }else if(browser.contains("Safari")){
            //For Safari Browser
            WebDriverManager.safaridriver().setup();
            driver = new SafariDriver();
        }*/
        //WebDriverWait wait = new WebDriverWait(driver,30);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resources/com.browserstack/" + config_file));
        JSONObject envs = (JSONObject) config.get("environments");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        Map<String, String> envCapabilities = (Map<String, String>) envs.get(environment);
        Iterator it = envCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
        }

        Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
        it = commonCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (capabilities.getCapability(pair.getKey().toString()) == null) {
                capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
            }
        }

        String username = System.getenv("BROWSERSTACK_USERNAME");
        if (username == null) {
            username = (String) config.get("user");
        }

        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        if (accessKey == null) {
            accessKey = (String) config.get("key");
        }

        if (capabilities.getCapability("browserstack.local") != null
                && capabilities.getCapability("browserstack.local") == "true") {
            l = new Local();
            Map<String, String> options = new HashMap<String, String>();
            options.put("key", accessKey);
            l.start(options);
        }

        driver = new RemoteWebDriver(
                new URL("http://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub"), capabilities);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        jse = (JavascriptExecutor) driver;

    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }



}
