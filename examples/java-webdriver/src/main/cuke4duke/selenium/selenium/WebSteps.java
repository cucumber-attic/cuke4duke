package cuke4duke.selenium;

import cuke4duke.Table;
import cuke4duke.annotation.I18n.EN.Then;
import cuke4duke.annotation.I18n.EN.When;
import cuke4duke.spring.StepDefinitions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

/**
 * Generic domain-agnostic step definitions for interacting with a web
 * application.
 */
@StepDefinitions
public final class WebSteps extends SeleniumSteps {
    
    @When("^(?:|I )go to \"([^\"]*)\"$")
    public void gotoPath(String where) {
        getSelenium().goTo(where);
    }
    
    @When("^(?:|I )try to go to \"([^\"]*)\"$")
    public void tryToGotoPath(String where) {
        getSelenium().goToWithoutValidation(where);
    }
    
    @When("^I fill in the following:$")
    public void iFillInTheFollowing(Table table) {
        getSelenium().populateFields(table);
    }
    
    @When("^(?:|I )fill in the following within \"([^\"]*)\":$")
    public void fillInFieldsWithinFieldset(String legend, Table fields) {
        getSelenium().fillInFieldsWithinFieldset(legend, fields);
    }
    
    @When("^(?:|I )fill in \"([^\"]*)\" for \"([^\"]*)\"$")
    public void fillInFieldByLabel(String value, String field) {
        getSelenium().fillInFieldByLabel(field, value);
    }
    
    @When("^(?:|I )press \"([^\"]*)\"$")
    public void pressByButtonText(String buttonText) {
        try {
            getSelenium().pressByName(buttonText);
        }
        catch (RuntimeException e) {
            getSelenium().pressByInnerText(buttonText);
        }
    }
    
    @When("^(?:|I )select \"([^\"]*)\"$")
    public void selectByLabel(String labelText) {
        getSelenium().selectOrCheckByLabel(labelText);
    }
    
    @When("^(?:|I )check \"([^\"]*)\"$")
    public void checkByLabel(String labelText) {
        getSelenium().selectOrCheckByLabel(labelText);
    }
    
    @When ("^I uncheck \"([^\"]*)\"$")
    public void uncheckByLabel(String labelText) {
        getSelenium().unselectOrUncheckByLabel(labelText);
    }
    
    @When("^(?:|I )click \"([^\"]*)\"$")
    public void clickLink(String hyperLinkText) {
        WebElement link = b().findElement(By.linkText(hyperLinkText));
        link.click();
    }
    
    @Then("^(?:|I )should see \"([^\"]*)\"$")
    public void iShouldSee(String expectedText) {
        try {
            assertThat(b().getPageSource(), containsString(expectedText));
        }
        catch (AssertionError e) {
            getSelenium().throwPageInfoException(e);
        }
    }
    
    @Then("^(?:|I )should not see \"([^\"]*)\"$")
    public void iShouldNotSee(String unexpectedText) {
        try {
            assertThat(b().getPageSource(), not(containsString(unexpectedText)));
        }
        catch (AssertionError e) {
            getSelenium().throwPageInfoException(e);
        }
    }

    @Then("^(?:|I )should see the following links:$")
    public void checkLinks(Table expectedLinks) {
        for(Map<String,String> expectedLink: expectedLinks.hashes()) {
            WebElement link = b().findElement(By.linkText(expectedLink.get("Text")));
            assertThat(link.getAttribute("href"), equalTo(expectedLink.get("URL")));
        }
    }


}
