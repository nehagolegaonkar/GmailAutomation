package com.selenium.testcase;

import com.selenium.common.CommonFunction;
import com.selenium.firstpackage.page.LandingPage;
import com.selenium.firstpackage.page.SignInPage;
import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class TestCase {

	private WebDriver webDriver;
	private boolean shouldRunTest = true;

	@DataProvider(name = "Data-Provider-Function")
	public Iterator<Object[]> readExcelForLogin() throws Exception {
		return CommonFunction.readGivenFile("LoginDetails.xlsx");
	}

	@BeforeTest()
	public void beforeTest() throws Exception {

		shouldRunTest = CommonFunction
				.checkWhetherTestShouldBeExcuted(getClass().getSimpleName());
	}

	@Test(dataProvider = "Data-Provider-Function")
	public void loginToGmail(HashMap<String, String> map) {
		try {
			if (shouldRunTest) {
				System.out.println("Run Test " + getClass().getSimpleName());
				webDriver = new FirefoxDriver();
				webDriver.get("http://www.gmail.com");
				webDriver.manage().timeouts()
						.implicitlyWait(5, TimeUnit.SECONDS);
				SignInPage signIn = new SignInPage(webDriver);
				signIn.signIn(map.get("username"), map.get("password"));
				LandingPage landingPage = new LandingPage(webDriver);
				webDriver.manage().timeouts()
						.implicitlyWait(300, TimeUnit.SECONDS);
				
				File scrFile = ((TakesScreenshot) webDriver)
						.getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(
						"c:\\shrawan\\screenshot.png"));

				Assert.assertTrue(landingPage.getCompose().isDisplayed());
			} else {
				System.out
						.println("Skipped Test " + getClass().getSimpleName());
			}

		} catch (Throwable e) {
			Assert.assertTrue(false);
			System.out.println("Error");
		}

	}
}
