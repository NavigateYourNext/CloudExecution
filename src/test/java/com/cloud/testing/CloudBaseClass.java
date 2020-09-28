package com.cloud.testing;

import java.io.FileInputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CloudBaseClass 
{
	public static WebDriver driver;
	public static Properties prop;
	public static ExtentReports extentReports;
	public static ExtentTest extentTest;
	public static String selectedCloud="";
	
	public static final String browserStackUSERNAME = "akshay395";
	public static final String browserStackAUTOMATE_KEY = "3xNo3pgea85nsRbBxaWa";
	public static final String browserStackURL = "https://" + browserStackUSERNAME + ":" + browserStackAUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
	

	static
	{
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		String date = sdf.format(d);

		extentReports = new ExtentReports(System.getProperty("user.dir")+"/Extent_Reports/extent_report_"+date+".html",true);
		extentReports.addSystemInfo("Admin", "Akshay Pradip Shete");
		extentReports.addSystemInfo("Host", "HP Windows 10");
		extentReports.addSystemInfo("Date", LocalDateTime.now().toString());	
	}

	@BeforeMethod
	public void selectCloud()
	{
		selectedCloud = System.getProperty("CloudName");
		if(selectedCloud.equalsIgnoreCase("BrowserStack"))
			driver = setupBrowserStackProperties();
		else if(selectedCloud.equalsIgnoreCase("SauceLabs"))
			driver = setupSauceLabsProperties();
		else
		{
			System.out.println("Invalid Value For Parameter CloudName !!");
			System.exit(0);
		}
	}


	public WebDriver setupBrowserStackProperties()
	{
		
		try
		{
			prop = new Properties();
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/browserStack.properties");
			prop.load(fis);
			
			String os = prop.getProperty("os");
			String os_version = prop.getProperty("os_version");
			String browser = prop.getProperty("browser");
			String browser_version = prop.getProperty("browser_version");
			String name = prop.getProperty("name");
			
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("os", os);
			caps.setCapability("os_version", os_version);
			caps.setCapability("browser_version", browser_version);
			caps.setCapability("name", name);
			
			if(browser.equalsIgnoreCase("Chrome"))
			{
				WebDriverManager.chromedriver().setup();
				caps.setCapability("browser", browser);
			}
			else if(browser.equalsIgnoreCase("Firefox"))
			{
				WebDriverManager.firefoxdriver().setup();
				caps.setCapability("browser", browser);
			}
			
			driver = new RemoteWebDriver(new URL(browserStackURL),caps);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return driver;
	}

	public WebDriver setupSauceLabsProperties()
	{
		return driver;
	}
}
