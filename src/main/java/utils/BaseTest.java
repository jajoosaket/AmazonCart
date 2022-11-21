package utils;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


@Listeners()
public class BaseTest {
    public static WebDriver driver;
    public static ThreadLocal<WebDriver> tdriver = new ThreadLocal<WebDriver>();
    public static Properties prop;

    @Parameters({"chromeProfile", "deviceName"})
    @BeforeSuite(alwaysRun = true)
    public void setUp(Method method, @Optional String profile, @Optional String deviceName, ITestContext ctx) {
        try {
            System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/src/main/resources/chromedriver");
            driver= new ChromeDriver();
            driver.manage().window().maximize();
            tdriver.set(driver);
            prop = new Properties();
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+
                    "/src/main/java/config/config.properties");
            prop.load(ip);

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            System.out.println("Not able to initiate chromedriver instance");
            e.printStackTrace();
        }
    }

    public static synchronized WebDriver getDriver(){
        return tdriver.get();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

}