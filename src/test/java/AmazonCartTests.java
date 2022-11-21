import Listener.TestListener;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.AmazonCartPage;
import testData.AmazonCartTestData;
import utils.BaseTest;

@Listeners({TestListener.class})
public class AmazonCartTests extends BaseTest {

    AmazonCartPage amazonCartPage = new AmazonCartPage();

    @Test(description = "Fetch All Products Name, Price and Star in the first page and store them in a json file.")
    @Severity(SeverityLevel.BLOCKER)
    public void addToCartTest(){
        driver.navigate().to(prop.getProperty("url"));
        amazonCartPage.selectCategoryType(String.valueOf(AmazonCartTestData.CATEGORY_TYPE));
        amazonCartPage.searchProduct(String.valueOf(AmazonCartTestData.TEXT_TO_ENTER),String.valueOf(AmazonCartTestData.TEXT_TO_SELECT));
        String jsonString = amazonCartPage.fetchInfo();
        Assert.assertFalse(jsonString==null,"Error in fetching price, name and star");
        boolean bool = amazonCartPage.writeJSONToFile(jsonString);
        Assert.assertTrue(bool,"Failed to write JSON in to the file");
    }

}
