package pages;

import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.Step;

import drivermanager.DriverFactory;

public class HomePage extends PageObject {

    public HomePage () {
        super(DriverFactory.getWebDriver());
    }

    @Step
    public void navigateTo(String url){
        openAt(url);
    }

}
