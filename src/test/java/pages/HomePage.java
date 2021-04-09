package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;

import drivermanager.DriverFactory;

public class HomePage extends PageObject {

    @FindBy(xpath = "//div[@class='dt-header__logo']/a/img")
    public static WebElementFacade DAN_TRI_LOGO;

    public HomePage () {
        super(DriverFactory.getWebDriver());
    }

    @Step
    public void navigateTo(String url){
        openAt(url);
    }


}
