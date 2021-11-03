package Page;

import Helper.DriverClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class BSLive {

    public WebDriver driver;
    public WebDriverWait wait;

    public BSLive(WebDriver ldriver){
        this.driver = ldriver;
        wait = new WebDriverWait(driver,30);
    }

    @FindBy(xpath="(//a[text()='Sign in'])[1]")
    WebElement lnksign;
    @FindBy(xpath = "//input[@id='user_email_login']")
    WebElement txtemail;
    @FindBy(xpath = "//input[@id='user_password']")
    WebElement txtpwrd;
    @FindBy(id = "user_submit")
    WebElement btnSignIn;
    @FindBy(xpath = "//*[text()='windows']")
    WebElement lblWindow;
    @FindBy(xpath = "//*[text()='10']")
    WebElement lblOs;
    @FindBy(xpath = "//*[@data-test-browser='edge']//div[text()='94']")
    WebElement lnkbrowser;
    @FindBy(xpath="//*[text()='Switch browser']")
    WebElement tlbLive;
    @FindBy(xpath = "//input[@name='q']")
    WebElement txtgoogle;
    @FindBy(id="skip-local-installation")
    WebElement alertlocalClose;

    public void BSlogin(String email, String psswrd){
        driver.get("http://www.browserstack.com/");
        lnksign.click();
        txtemail.sendKeys(email);
        txtpwrd.sendKeys(psswrd);
        btnSignIn.click();
    }

    public void startLive(){
        wait.until(ExpectedConditions.visibilityOf(lblOs));
        lblOs.click();
        lnkbrowser.click();
        wait.until(ExpectedConditions.visibilityOf(tlbLive));
        try{
            alertlocalClose.isDisplayed();
            alertlocalClose.click();
        }catch(Exception e){}
    }

    public void googleSearch() throws InterruptedException {
        driver.navigate().to("https://www.Google.com");
        txtgoogle.sendKeys("BrowserStack");
        txtgoogle.submit();
        Thread.sleep(5000);
        if(driver.getTitle().contains("BrowserStack")){
            DriverClass.jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"Title matched!\"}}");
        }else{
            DriverClass.jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"failed\", \"reason\": \"Title dont match!\"}}");
        }
    }



}
