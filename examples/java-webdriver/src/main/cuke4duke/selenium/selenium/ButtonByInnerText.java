package cuke4duke.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ButtonByInnerText extends By{

    private final String innerText;

    private ButtonByInnerText(String innerText) {
        this.innerText = innerText;
    }

    public static By buttonByInnerText(String innerText) {
        return new ButtonByInnerText(innerText);
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
        List<WebElement> allButtonElements = context.findElements(By.tagName("button"));
        
        List<WebElement> buttonElementsWithWantedInnertText = new ArrayList<WebElement>();
        for (WebElement button : allButtonElements) {
            if (innerText.toUpperCase().contains(button.getText().toUpperCase())) {
                buttonElementsWithWantedInnertText.add(button);
            }
        }
        return buttonElementsWithWantedInnertText;
    }
    
}
