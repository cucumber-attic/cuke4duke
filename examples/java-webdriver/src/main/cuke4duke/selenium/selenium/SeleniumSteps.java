package cuke4duke.selenium;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Base class for step definition classes that use Selenium.
 */
public abstract class SeleniumSteps {
    
    @Autowired
    private Selenium selenium;

    protected Selenium getSelenium() {
        return selenium;
    }
    
    /**
     * @return The current Selenium browser object.
     * The returned instance is shared among all subclasses
     * of this class.
     */
    protected WebDriver b() {
        return selenium.getWebDriver();
    }
}
