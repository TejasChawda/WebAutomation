package my_base_package;

import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import io.github.bonigarcia.wdm.managers.EdgeDriverManager;
import io.github.bonigarcia.wdm.managers.FirefoxDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DriverCreator {

    ChromeOptions chromeOptions;
    WebDriver webDriver;
    public WebDriver create(String browser) {

        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", "Pixel 5");
        chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

        browser = setDefaultBrowser(browser);

        return switch (browser.toLowerCase()) {
            case "firefox" -> new FirefoxDriverManager().create();
            case "edge" -> new EdgeDriverManager().create();
            default -> {
                webDriver = new ChromeDriver(chromeOptions);
                yield webDriver;
            }
        };
    }


    private String setDefaultBrowser(String browser) {
        if(Objects.isNull(browser) || browser.isEmpty()) {
            browser = "chrome";
        }
        return browser;
    }
}
