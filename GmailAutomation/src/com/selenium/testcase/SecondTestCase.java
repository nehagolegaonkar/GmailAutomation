package com.selenium.testcase;

import java.util.HashMap;

import junit.framework.Assert;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.selenium.common.CommonFunction;

public class SecondTestCase {

	private boolean shouldRunTest = true;

	@BeforeTest()
	public void beforeTest() throws Exception {

		shouldRunTest = CommonFunction
				.checkWhetherTestShouldBeExcuted(getClass().getSimpleName());
	}

	@Test
	public void loginToGmail() {
		try {
			if (shouldRunTest) {
				System.out.println("Run Test "+getClass().getSimpleName());
				Assert.assertTrue(true);
			} else {
				System.out
						.println("Skipped Test " + getClass().getSimpleName());
			}

		} catch (Throwable e) {
			Assert.assertTrue(true);
			System.out.println("Error");
		}

	}
}
