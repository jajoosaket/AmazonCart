package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static utils.BaseTest.driver;

public class TestUtilities {

    public TestUtilities() {
        PageFactory.initElements(driver, this);
    }

    public void click(String locator){
        driver.findElement(By.xpath(locator)).click();
    }

    public void waitForVisibility(String locator){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
    }

    public void sendText(String locator, String text){
        waitForVisibility(locator);
        driver.findElement(By.xpath(locator)).sendKeys(text);
    }



}
