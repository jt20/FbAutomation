package com.Automation.FrameworkSetup;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.IReporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

@Listeners(com.Automation.FrameworkSetup.CustomListener.class)
public class AutomationSetup {
	
	public static Logger log;	
	ExcelSheet_Setup excelSheet_Setup;
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest test;
	
	WebDriver driver;
	
	@BeforeMethod
	public void setup()
	{
	
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		
		log= LogManager.getLogger(AutomationSetup.class);
		
		
		 htmlReporter = new ExtentHtmlReporter("extent.html");
        // create ExtentReports and attach reporter(s)
         extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        test = extent.createTest("Start Test");
        
	}
	
	@AfterMethod
	public void teardown()
	{
		driver.quit();
		extent.flush();
	}
	
	@Test
	public void test() throws IOException, InterruptedException
	{
		this.excelSheet_Setup = new ExcelSheet_Setup();
		String firstname= excelSheet_Setup.getFirstName(1);
		String lastname= excelSheet_Setup.getSurName(1);
		String Mobile= String.valueOf(excelSheet_Setup.getMobile(1)) ;
		String NewPassword= excelSheet_Setup.getNewPassword(1);
		String Gender= excelSheet_Setup.getGender(1);
		
		
		test.log(Status.INFO,"Test Started");
		log.info(excelSheet_Setup.getFirstName(1));
		log.info(excelSheet_Setup.getGender(1));
		log.info(excelSheet_Setup.getMobile(1));
		log.info(excelSheet_Setup.getNewPassword(1));
		log.info(excelSheet_Setup.getSurName(1));
		test.pass("passed the string value");
		driver.get("https://www.facebook.com/");
		test.pass("passed the URL");
		
		driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();
	Thread.sleep(4000);
		driver.findElement(By.xpath("//input[@aria-label='First name']")).sendKeys(firstname);
	
	driver.findElement(By.xpath("//input[@aria-label='Surname']")).sendKeys(lastname);//input[@aria-label='']
	driver.findElement(By.xpath("//input[@aria-label='Mobile number or email address']")).sendKeys(Mobile);
	driver.findElement(By.xpath("//input[@aria-label='New password']")).sendKeys(NewPassword);
	String genderxpath=	"//label[text()='$']";
	driver.findElement(By.xpath(genderxpath.replace("$", Gender))).click();

	}

}
