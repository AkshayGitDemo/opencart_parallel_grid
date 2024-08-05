package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;


public class TC_002_LoginValidationTest extends BaseClass {


	@Test(groups= {"regression","master"})
	public void loginValidation() {
		try {
		logger.info("***Starting Login Validation Test***");
		HomePage hp = new HomePage(getDriver());
		logger.info("Clicked My Account");
		hp.clickMyAccount();
		logger.info("Clicked Login");
		LoginPage lp = new LoginPage(getDriver());
		lp.clickOnLogin();
		logger.info("Entered Username and Password");
		lp.setUsername(p.getProperty("username2"));
		lp.setPassword(p.getProperty("password2"));
		logger.info("Clicked LoginIN button");
		lp.clickLoginButton();
		AccountPage ap = new AccountPage(getDriver());
		if (ap.myAccountMsg() == true) {
			logger.info("Account Successfully Logged in");
			Assert.assertEquals(true, true);
			
		}

		else {
			logger.info("Unable to Log in");
			Assert.fail();
		}
		}
		catch(Exception e)
		{
			System.out.println(e.getStackTrace());
		}

	}

}
