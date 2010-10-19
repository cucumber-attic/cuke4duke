package cuke4duke.selenium;

import org.openqa.selenium.WebDriver;

/**
 * Abstraction for a particular web application. 
 */
public interface Webapp {
    
    /**
     * Navigate somewhere.
     * 
     * @param b the browser to navigate with.
     * @param where where to go.
     */
    void goTo(WebDriver b, String where);

    void goToWithoutValidation(WebDriver webDriver, String path);
}
