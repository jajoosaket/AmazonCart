package Listener;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.BaseTest;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TestListener extends BaseTest implements ITestListener {

    final Logger logger =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    private static String getTestMethodName(ITestResult iTestResult){
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Attachment(value = "Page Screenshot", type = "image/png")
    public byte[] saveScreenshotPNG(WebDriver driver){
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "{0}", type= "text/plain")
    public static String saveTextLog (String message){
        return message;
    }

    @Override
    public void onTestFailure(ITestResult iTestResult){

        Object testClass = iTestResult.getInstance();
        WebDriver driver= ((BaseTest) testClass).getDriver();

        if(driver instanceof WebDriver){
            logger.log(Level.INFO,"Screenshot captured for test case: {0}", getTestMethodName(iTestResult));
            saveScreenshotPNG(driver);
        }
        saveTextLog(getTestMethodName(iTestResult)+" failed and screenshot taken!");

    }
}
