package com.cloud.testing;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CloudTestCases extends CloudBaseClass
{
	
	public void doLogin()
	{
		driver.get("https://www.saucedemo.com/index.html");
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();
	}
	
	@Test(priority=1)
	public void checkInventoryItemListSizeTest()
	{
		doLogin();
		Assert.assertTrue(driver.findElements(By.cssSelector(".inventory_item_name")).size() == 6);
	}
	
	@Test(priority=2)
	public void checkAddToCartButtonSizeTest()
	{
		doLogin();
		Assert.assertTrue(driver.findElements(By.xpath("//button[text()='ADD TO CART']")).size() == 6);
		
	}
}
