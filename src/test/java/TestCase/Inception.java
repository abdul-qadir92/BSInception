package TestCase;

import Helper.DriverClass;
import Page.BSLive;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;

public class Inception extends DriverClass {
    @Test
    public void AutomateBS() throws FileNotFoundException, InterruptedException {

        //String email = config.getProperty("BS_email");
        //String pswrd = config.getProperty("BS_pswrd");

        BSLive bs = PageFactory.initElements(driver,BSLive.class);
        //bs.BSlogin(email,pswrd);
        //bs.startLive();
        bs.googleSearch();
    }
}
