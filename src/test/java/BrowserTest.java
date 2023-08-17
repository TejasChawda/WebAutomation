import org.example.HomePage;
import org.example.LauncherPage;
import org.example.models.Item;
import org.example.pages.PageWaits;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import my_base_package.DriverCreator;

public class BrowserTest {


    WebDriver webDriver;


    @BeforeTest
    public void ready() {
        webDriver = new DriverCreator().create("chrome");
    }

    @Test
    public void verifyIfSearchTermShowsRelevantResults() {
        //Arrange
        String searchItem = "Jeans";
        String searchKey = "Jean";
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        LauncherPage launcherPage = new LauncherPage(webDriver);
        launcherPage.navigateTo("https://web-playground.ultralesson.com/");

        //Act
        HomePage homepage = new HomePage(webDriver);
        homepage.search(searchItem);
        List<Item> searchItems = homepage.getSearchItems();

        //Assert
        Assert.assertEquals(4, searchItems.size());
        Assert.assertTrue(searchItems.stream().allMatch(item -> item.getName().contains(searchKey)));
    }

    @Test
    public void verifySearchUnavailableProduct() {
        // Arrange
        String unavailableProduct = "unavailable widget";
        //webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        LauncherPage launcherPage = new LauncherPage(webDriver);
        launcherPage.navigateTo("https://web-playground.ultralesson.com/");

        // Act
        HomePage homepage = new HomePage(webDriver);
        homepage.search(unavailableProduct);
        List<Item> searchItems = homepage.getSearchItems();


        // Assert
        Assert.assertTrue(searchItems.isEmpty());
    }

    @Test
    public void verifySearchResultCountMatchesDisplayedItems() {
        // Arrange
        String searchItem = "Shoes";
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));


        LauncherPage launcherPage = new LauncherPage(webDriver);
        launcherPage.navigateTo("https://web-playground.ultralesson.com/");

        // Act
        HomePage homepage = new HomePage(webDriver);
        homepage.search(searchItem);
        List<Item> searchItems = homepage.getSearchItems();
        int itemCountDisplayed = homepage.getItemCount();
        System.out.println("total item count : "+itemCountDisplayed);

        // Assert
        Assert.assertEquals(searchItems.size(), itemCountDisplayed);
    }


    @Test
    public void verifyBrandSearchListsOnlyBrandItems() {
        // Arrange
        String brandName = "Vapen";
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        LauncherPage launcherPage = new LauncherPage(webDriver);
        launcherPage.navigateTo("https://web-playground.ultralesson.com/");

        // Act
        HomePage homepage = new HomePage(webDriver);
        homepage.search(brandName);
        List<Item> searchItems = homepage.getSearchItems();

        // Assert
        Assert.assertTrue(searchItems.stream().allMatch(item -> item.getName().contains(brandName)));
    }

    @AfterTest
    public void bye(){
        webDriver.quit();
    }
}
