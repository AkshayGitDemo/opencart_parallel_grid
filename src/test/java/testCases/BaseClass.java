package testCases;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;



public class BaseClass {
	
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	public Logger logger;
	Properties p;
	
	@BeforeClass(groups= {"regression","sanity","master"})
	@Parameters({"os","browser"})
	public void setup(String os,String br) throws IOException {
	    // Load properties
	    FileReader fis = new FileReader(".//src//test//resources//config.properties");
	    p = new Properties();
	    p.load(fis);

	    // Initialize WebDriver
	    if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
	        DesiredCapabilities dc = new DesiredCapabilities();
	        if (os.equalsIgnoreCase("linux")) {
	            dc.setPlatform(Platform.LINUX);
	        } else if (os.equalsIgnoreCase("windows")) {
	            dc.setPlatform(Platform.WIN11);
	        } else {
	            throw new IllegalArgumentException("Invalid OS Name");
	        }
	        switch (br.toLowerCase()) {
	            case "chrome": dc.setBrowserName("chrome"); break;
	            case "edge": dc.setBrowserName("MicrosoftEdge"); break;
	            case "firefox": dc.setBrowserName("firefox"); break;
	            default: throw new IllegalArgumentException("No Such browser found");
	        }
	        driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc));
	    } else if (p.getProperty("execution_env").equals("local")) {
	        switch (br.toLowerCase()) {
	            case "chrome": driver.set(new ChromeDriver()); break;
	            case "edge": driver.set(new EdgeDriver()); break;
	            case "firefox": driver.set(new FirefoxDriver()); break;
	            default: throw new IllegalArgumentException("No Such browser found");
	        }
	    }

	    // Additional setup
	    logger = LogManager.getLogger(this.getClass());
	    WebDriver wd = driver.get();
	    wd.manage().deleteAllCookies();
	    wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	    wd.get(p.getProperty("appurl3"));
	    wd.manage().window().maximize();
	}

	@AfterMethod(groups= {"regression","sanity","master"})
	public void tearDown() {
	    WebDriver wd = getDriver();
	    if (wd != null) {
	        wd.quit();
	        driver.remove();
	    }
	}
	
	
    public WebDriver getDriver() {
        return driver.get();
    }

	public String randomeString()
	{
		String generatedString=RandomStringUtils.randomAlphabetic(5);
		return generatedString;
	}
	public String randomeNumeric()
	{
		String generatedString=RandomStringUtils.randomNumeric(10);
		return generatedString;
	}
	public String randomeAlphaNumeric()
	{
		String generatedString1=RandomStringUtils.randomNumeric(3);
		String generatedString2=RandomStringUtils.randomNumeric(3);
		return generatedString1+"@"+generatedString2;
	}
	

	public String captureScreen(String tname) {
	    WebDriver wd = getDriver();
	    if (wd == null) {
	        logger.error("WebDriver instance is not available.");
	        return null;
	    }
	    
	    TakesScreenshot takesScreenshot = (TakesScreenshot) wd;
	    File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
	    
	    String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	    String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
	    File targetFile = new File(targetFilePath);
	    
	    try {
	        FileUtils.copyFile(sourceFile, targetFile);
	    } catch (IOException e) {
	        logger.error("Error while saving screenshot", e);
	        return null;
	    }
	    
	    return targetFilePath;
	}

}
