package com.selenium.firstpackage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class SignInPage extends Page {

	public SignInPage(WebDriver webDriver) {
		super(webDriver);
		PageFactory.initElements(webDriver, this);
	}

	@FindBy(how = How.XPATH, using = "//*[@id='Email']")
	private WebElement email;

	@FindBy(how = How.XPATH, using = "//*[@id='Passwd']")
	private WebElement password;

	@FindBy(how = How.XPATH, using = "//*[@id='signIn']")
	private WebElement login;

	public void signIn(String userName, String pass) {

		email.sendKeys(userName);
		password.sendKeys(pass);
		login.click();
	}
}
