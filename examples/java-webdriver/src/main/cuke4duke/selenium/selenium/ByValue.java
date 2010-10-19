package cuke4duke.selenium;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class ByValue extends By {
    public static By byValue(String value) {
        return new ByValue(value);
    }
    
    private final String value;
    
    public ByValue(String value) {
        this.value = value;
    }
    
    @Override
    public List<WebElement> findElements(SearchContext context) {
        List<WebElement> allInputElements = context.findElements(By.tagName("input"));
        
        List<WebElement> inputElementsWithWantedValue = new ArrayList<WebElement>();
        for (WebElement input : allInputElements) {
            if (value.equals(input.getAttribute("value"))) {
                inputElementsWithWantedValue.add(input);
            }
        }
        return inputElementsWithWantedValue;
    }
    
    @Override
    public String toString() {
        return "ByValue: " + value;
    }
}
