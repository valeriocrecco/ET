package test.seleniumapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestSeleniumDAlessandro {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		String newEmail = "test1email@gmail.com";
		String actualEmail = "";
		driver.get("http://localhost:8080/EasyTravel/login.jsp");
		driver.findElement(By.xpath("//*[@id=\"usr\"]")).sendKeys("Erik97");
		driver.findElement(By.xpath("//*[@id=\"pwd\"]")).sendKeys("Enrico1997.");
		driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/form/div[3]/input")).click();
		driver.findElement(By.xpath("//*[@id=\"myNavbar\"]/ul[1]/li[4]/a")).click();
		driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/ul/li[4]/a")).click();
		driver.findElement(By.xpath("//*[@id=\"trvls\"]/form[2]/div/input[1]")).sendKeys(newEmail);
		driver.findElement(By.xpath("//*[@id=\"trvls\"]/form[2]/div/input[2]")).click();
		
		WebElement element = driver.findElement(By.xpath("//*[@id=\"usrInf\"]/h4[2]"));
		
		actualEmail = element.getText();
		assertEquals(newEmail, actualEmail);
		
		driver.close(); 
	}

}
