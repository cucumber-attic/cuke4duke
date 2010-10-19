package cuke4duke.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class ByLabel extends By {
    public static By byLabel(String label) {
        return new ByLabel(label);
    }
    
    private final String label;
    
    public ByLabel(String label) {
        this.label = label;
    }
    
    @Override
    public List<WebElement> findElements(SearchContext context) {
        List<WebElement> findElements = context.findElements(By.xpath("id(./descendant::label[normalize-space(text()) = '" + label + "']/@for)"));
        return findElements;
    }
    
    @Override
    public String toString() {
        return "ByLabel: " + label;
    }
}
