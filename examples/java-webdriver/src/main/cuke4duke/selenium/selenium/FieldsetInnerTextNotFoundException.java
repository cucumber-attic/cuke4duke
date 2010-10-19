package cuke4duke.selenium;

import org.openqa.selenium.NoSuchElementException;

@SuppressWarnings("serial")
public class FieldsetInnerTextNotFoundException extends NoSuchElementException {
    
    public FieldsetInnerTextNotFoundException(String reason, Throwable cause) {
        super(reason, cause);
    }
    
}
