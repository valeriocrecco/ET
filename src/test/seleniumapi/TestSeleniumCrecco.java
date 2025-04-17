package test.seleniumapi;

import static org.junit.Assert.assertEquals;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestSeleniumCrecco {

	public static void main(String[] args) {
		
		System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		String travelName = "Safari";
		String actualTravelName = "";
		driver.get("http://localhost:8080/EasyTravel/login.jsp");
		driver.findElement(By.xpath("//*[@id=\"usr\"]")).sendKeys("Valerio23");
		driver.findElement(By.xpath("//*[@id=\"pwd\"]")).sendKeys("Valerio23.");
		driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/form/div[3]/input")).click();
		
		driver.findElement(By.xpath("/html/body/div[2]/div[2]/a")).click();
		driver.findElement(By.xpath("//*[@id=\"name\"]")).sendKeys(travelName);
		driver.findElement(By.xpath("//*[@id=\"date\"]")).sendKeys("25/02/2021");
		driver.findElement(By.xpath("//*[@id=\"date1\"]")).sendKeys("01/03/2021");
		driver.findElement(By.xpath("//*[@id=\"dest\"]")).sendKeys("New York");
		driver.findElement(By.xpath("//*[@id=\"number\"]")).sendKeys("3");
		driver.findElement(By.xpath("//*[@id=\"number1\"]")).sendKeys("1");
		
		driver.findElement(By.xpath("/html/body/div[2]/form/div[3]/div[6]/div/input")).click();
		
		WebElement element = driver.findElement(By.xpath("//*[@id=\"travelName\"]"));
		actualTravelName = element.getAttribute("value");
		
		assertEquals(travelName, actualTravelName);
		
		driver.close();
		
		
	}

}
