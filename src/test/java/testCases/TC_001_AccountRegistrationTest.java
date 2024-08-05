package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;


public class TC_001_AccountRegistrationTest extends BaseClass {

	
  

	@Test(groups= {"sanity","master"})
    public void verify_account_registration() {
        try {
            logger.info("****Account Registration Test Case Started.****");
        
            // Initialize a new instance of the HomePage class
            HomePage hp = new HomePage(getDriver());
        
            // Click on the "My Account" link
            hp.clickMyAccount();
        
            // Click on the "Account Registration" link
            hp.clickRegister();
        
            // Initialize a new instance of the AccountRegistrationPage class
            AccountRegistrationPage reg = new AccountRegistrationPage(getDriver());
            logger.info("Getting in Random Details");
            // Fill out the registration form with random data
            String firstName = randomeString();
            String lastName = randomeString();
            String email = randomeString() + "@gmail.com";
            String telephone = randomeNumeric();
            String password = randomeAlphaNumeric();
            logger.info("Filling in Registration Info");
            reg.setFirstName(firstName);
            reg.setLastName(lastName);
            reg.setEmail(email);
            reg.setTelephone(telephone);
            reg.setPassword(password);
            reg.setConfirmPassword(password);
            reg.setPrivacyPolicy();
            logger.info("Clicking Submit Button");
            // Click on the "Continue" button
            reg.submitButton();
        
            // Check if the account creation confirmation message is displayed
            String confirmationMsg = reg.getConfirmationMsg();
            if (confirmationMsg.equals("Your Account Has Been Created!")) {
                logger.info("Account Created.");
            } else {
                logger.error("Account not Created");
                Assert.fail();
            }
        } catch (Exception e) {
            logger.error("Test Case Failed");
        }
    
        logger.info("****Account Registration Test Case Ended.****");
    }

	
	
	
	
}
