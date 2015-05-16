package com.selenium.firstpackage;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.selenium.common.CommonFunction;
import com.selenium.firstpackage.page.SignInPage;

public class FirstClass {

	private WebDriver webDriver;
	
	@Before
	public void setUp(){
		webDriver = new FirefoxDriver();
		
		//webDriver.get("http://www.makemytrip.com");
	}
	
	@Test
	public void openUrl(){
		webDriver.get("http://www.gmail.com");
		webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		try {
			CommonFunction.readGivenFile("file");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//webDriver.findElement(By.cssSelector(selector))
		//webDriver.findElement(By.cssSelector(("div[id^='infant_count']span.infantCount form-control")));
		//System.out.print(webDriver.findElement(By.xpath("//div[@id='infant_count']")).getText());
		SignInPage signInPage = new SignInPage(webDriver);
		//signInPage.signIn(webDriver);
	}
	
	
}
