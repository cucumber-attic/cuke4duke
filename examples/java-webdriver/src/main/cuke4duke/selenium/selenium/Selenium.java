package cuke4duke.selenium;

import cuke4duke.Table;
import cuke4duke.annotation.After;
import cuke4duke.spring.StepDefinitions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static cuke4duke.selenium.ButtonByInnerText.buttonByInnerText;
import static cuke4duke.selenium.ByLabel.byLabel;
import static cuke4duke.selenium.ByValue.byValue;
import static cuke4duke.selenium.FieldsetByInnerText.fieldsetByInnerText;

/**
 * This class acts as a facade for an instance of {@link WebDriver}, ensuring
 * that every instance of {@link SeleniumSteps} subclasses get a reference to
 * the same {@link WebDriver} instance.
 * 
 * What particular {@link WebDriver} subclass to use can be configured by
 * setting the <tt>cucumber.selenium.driver</tt> system property to the desired
 * class. (If the property is not set, it will default to
 * {@link org.openqa.selenium.htmlunit.HtmlUnitDriver}.
 */
@StepDefinitions
public class Selenium {
    private static Constructor<WebDriver> driverConstructor = getDriverConstructor();

    @SuppressWarnings("unchecked")
    private static Constructor<WebDriver> getDriverConstructor() {
        String driverName = System.getProperty("cucumber.selenium.driver", "org.openqa.selenium.htmlunit.HtmlUnitDriver");
        try {
            return (Constructor<WebDriver>) Thread.currentThread().getContextClassLoader().loadClass(driverName).getConstructor();
        }
        catch (Throwable problem) {
            throw new RuntimeException("Couldn't load " + driverName, problem);
        }
    }
    
    private WebDriver browser;
    
    private Webapp webapp;
    
    @Autowired
    public Selenium(Webapp webapp) {
        this.webapp = webapp;
    }

    public WebDriver getWebDriver() {
        if (browser == null) {
            try {
                browser = driverConstructor.newInstance();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return browser;
    }
    
    @After
    public void closeBrowser() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if (browser != null) {
            browser.close();
            browser.quit();
        }
    }
    
    public void goTo(String path) {
        webapp.goTo(getWebDriver(), path);
    }
    
    public void goToWithoutValidation(String path) {
        webapp.goToWithoutValidation(getWebDriver(), path);
    }
    
    public void fillInFieldsWithinFieldset(String text, Table fields) {
        try {
            WebElement fieldset = getWebDriver().findElement(fieldsetByInnerText(text));
            populateFieldsWithin(fieldset, fields);
        }
        catch (Exception e) {
            throwPageInfoException(e);
        }
    }
    
    public void populateFields(Table fields) {
        try {
            populateFieldsWithin(getRootNode(), fields);
        }
        catch (Exception e) {
            throwPageInfoException(e);
        }
    }
    
    /**
     * Utility method for filling in an entire form.
     * 
     * @param parent
     *            a parent element of the input fields.
     * @param fields
     *            field labels and values
     */
    private void populateFieldsWithin(WebElement parent, Table fields) {
        for (List<String> inputRow : fields.raw()) {
            String labelText = inputRow.get(0);
            String value = inputRow.get(1);
            
            fillInFieldByLabel(parent, value, labelText);
        }
    }
    
    public void selectOrCheckByLabel(String labelText) {
        toggleCheckBoxByLabel(labelText, true);
    }
    
    public void unselectOrUncheckByLabel(String labelText) {
        toggleCheckBoxByLabel(labelText, false);
    }

    public void toggleCheckBoxByLabel(String labelText, boolean expectedState) {
        try {
            WebElement selectableElement = getWebDriver().findElement(byLabel(labelText));

            if(expectedState == selectableElement.isSelected())
                throw new IllegalStateException("element byLabel: <" + labelText + "> already has the selected state: " + expectedState);

            selectableElement.toggle();  
        }
        catch (Exception e) {
            throwPageInfoException(e);
        }
    }
    
    public void fillInFieldByLabel(String value, String labelText) {
        fillInFieldByLabel(getRootNode(), value, labelText);
    }
    
    private WebElement getRootNode() {
        return getWebDriver().findElement(By.tagName("html"));
    }
    
    public void fillInFieldByLabel(WebElement parent, String value, String labelText) {
        try {
            WebElement field = parent.findElement(byLabel(labelText));
            field.clear();
            field.sendKeys(value);
        }
        catch (Exception e) {
            throwPageInfoException(e);
        }
    }
    
    public void pressByName(String buttonText) {
        try {
            WebElement button = getWebDriver().findElement(byValue(buttonText));
            button.click();
        }
        catch (Exception e) {
            throwPageInfoException(e);
        }
    }
    
    public void pressByInnerText(String innerText) {
        try {
            WebElement button = getWebDriver().findElement(buttonByInnerText(innerText));
            button.click();
        }
        catch (Exception e) {
            throwPageInfoException(e);
        }
    }
    
    public void clickLink(String linkText) {
        getWebDriver().findElement(By.linkText(linkText)).click();
    }
    
    public void throwPageInfoException(Throwable e) {
        throw new RuntimeException(e.getMessage() + getPageInfo(), e);
    }
    
    private String getPageInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("\n========== Page Source for ").append(getCurrentPageUrl()).append("\n");
        sb.append(browser.getPageSource()).append("\n");
        return sb.toString();
    }

    public String getCurrentPageUrl() {
        return browser.getCurrentUrl();
    }

}
