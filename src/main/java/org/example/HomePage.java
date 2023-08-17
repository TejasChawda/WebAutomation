package org.example;

import org.example.models.Item;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.ArrayList;

public class HomePage{

    WebDriver webDriver;
    WebElement searchIcon, searchBar;
    List<Item> items;

    By productName = By.cssSelector("ul#predictive-search-results-list > li.predictive-search__list-item");

    public HomePage(WebDriver webDriver) {
       this.webDriver = webDriver;
    }

    public void search(String searchItem) {
        searchIcon = webDriver.findElement(By.cssSelector("#shopify-section-header > sticky-header > header > div > details-modal > details > summary > span > svg.modal__toggle-open.icon.icon-search"));
        searchIcon.click();

        searchBar = webDriver.findElement(By.cssSelector("#Search-In-Modal"));
        searchBar.sendKeys(searchItem);
    }

    public List<Item> getSearchItems() {
        List<WebElement> searchList = webDriver.findElements(productName);
        items = new ArrayList<>();

        for (WebElement item : searchList) {
            String name = item.getText();

            if (!name.contains("Search for")) {
                Item it = new Item();
                it.setName(name);
                items.add(it);
            }
        }

        for (Item value : items) {
            System.out.println(value.getName());
        }
        return items;
    }

    public int getItemCount() {
        if (!items.isEmpty()) {
            return items.size();
        }
        return 0;
    }
}

