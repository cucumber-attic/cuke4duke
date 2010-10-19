package cuke4duke.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

import static java.lang.String.format;

public class FieldsetByInnerText extends By {
    public static By fieldsetByInnerText(String legendValue) {
        return new FieldsetByInnerText(legendValue);
    }

    private final String textValue;
    
    public FieldsetByInnerText(String textValue) {
        this.textValue = textValue;
    }
    
    @Override
    public List<WebElement> findElements(SearchContext context) {
        try {
            String fieldsetXpath = "//fieldset[./descendant::node()[contains(text(), '" + textValue + "')]]";
            return context.findElements(By.xpath(fieldsetXpath));
        }
        catch (NoSuchElementException e) {
            throw new FieldsetInnerTextNotFoundException(format("Could not find legend '%s'", textValue), e);
        }
    }
    
    @Override
    public String toString() {
        return "<fieldset> " + textValue + " ...";
    }
}
