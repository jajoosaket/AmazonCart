package pages;
import com.gargoylesoftware.htmlunit.javascript.host.file.File;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import testData.AmazonCartTestData;
import utils.TestUtilities;
import org.json.JSONObject;
import org.openqa.selenium.support.PageFactory;

import static utils.BaseTest.driver;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.*;


public class AmazonCartPage extends TestUtilities {

    final Logger logger =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    LogManager slg = LogManager.getLogManager();
    Logger log = slg.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public AmazonCartPage() {
        PageFactory.initElements(driver, this);

    }

    private String searchDropDownLocator= "//select[@id='searchDropdownBox']";
    private String searchBoxLocator= "//input[@id='twotabsearchtextbox']";
    private String autosuggestionLocator="//div[@class='autocomplete-results-container']";
    private String getResultLocator="//div[@data-component-type='s-search-result']";
    private String fetchNameFromResult="//span[@class='a-size-medium a-color-base a-text-normal']";
    private String fetchStarFromResult="//i[contains(@class,'a-icon a-icon-star-small')]//span[contains(@class,'a-icon-alt')]";
    private String fetchPriceFromResult="//span[contains(@class,'a-price-whole')]";


    public void selectCategoryType(String categoryType) {
        /*Function for choosing the category type from the dropdown*/
        try {
            logger.log(Level.INFO,"Selecting Category type: {0} ",categoryType);
            Select dropdown = new Select(driver.findElement(By.xpath(searchDropDownLocator)));
            dropdown.selectByVisibleText(categoryType);
        }
        catch (Exception e){
            logger.log(Level.SEVERE,"Error in selecting Category type: {0} ",categoryType);
            e.printStackTrace();
        }
    }

    public void searchProduct(String productName, String productFullname){
        /*Function for entering the product name in the search box and selecting the product*/
        try {
            logger.log(Level.INFO,"Entering Product name: {0} ",productName);
            sendText(searchBoxLocator, productName);

            logger.log(Level.INFO,"Entering Product Full Name: {0} ",productFullname);

            WebElement autoSuggest = driver.findElement(By.xpath(autosuggestionLocator));
            Thread.sleep(3000);
            String [] str = autoSuggest.getText().split("\n");

            for (int i=0; i<str.length; i++)
            {
                if (str[i].equals(productFullname));
                {
                    String locator = autosuggestionLocator+"//div[@aria-label='"+productFullname+"']";
                    click(locator);
                    Thread.sleep(3000);
                    break;
                }
            }

        }
        catch (Exception e){
            logger.log(Level.SEVERE,"Error in entering product name: {0} ",productName);
            e.printStackTrace();
        }
    }

    public String fetchInfo() {
        /*Function for fetching price, name and star for a result and storing in a form of json*/
        try{
            logger.log(Level.INFO,"Fetching name, price and star from result screen");

            String nameLocator=getResultLocator+fetchNameFromResult;
            List<WebElement> productNames = driver.findElements(By.xpath(nameLocator));

            String starLocator=getResultLocator+fetchStarFromResult;
            List<WebElement> productStar = driver.findElements(By.xpath(starLocator));

            String priceLocator=getResultLocator+fetchPriceFromResult;
            List<WebElement> productPrice = driver.findElements(By.xpath(priceLocator));

            if(productNames.size() == productStar.size()){
                return null;
            }
            if(productNames.size() != productPrice.size()){
                return null;
            }

            String jsonString= "";
            for(int i=0;i<productNames.size();i++){
                Map<String, String> map = new HashMap<>();
                map.put("name", productNames.get(i).getText());
                map.put("star", productStar.get(i).getAttribute("textContent"));
                map.put("price", productPrice.get(i).getText());

                JSONObject object = new JSONObject(map);
                jsonString = jsonString+"\n"+object.toString();

                if(i!=productNames.size()-1){
                    jsonString=jsonString+",";
                }
            }

            return  jsonString;


        }
        catch (Exception e){
            logger.log(Level.SEVERE,"Error in fetching name, price and star");
            e.printStackTrace();
            return null;
        }
    }

    public boolean writeJSONToFile(String jsonString) {
        boolean bool= false;
        FileWriter file = null;
        try{
            logger.log(Level.INFO,"Writing JSON to a file");
            jsonString= "[\n"+jsonString.trim()+"\n]";
            file = new FileWriter(System.getProperty("user.dir")+"/src/main/resources/jsonFile");
            file.write(jsonString);
            bool= true;
        }
        catch (Exception e){
            logger.log(Level.SEVERE,"Error in writing json to file");
            e.printStackTrace();
        }
        finally {
            try {
                file.close();
            }
            catch (IOException e){
                logger.log(Level.SEVERE,"File not found");
                e.printStackTrace();
            }
        }
        return bool;
    }
}
