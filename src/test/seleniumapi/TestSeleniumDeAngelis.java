package test.seleniumapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestSeleniumDeAngelis {

	public static void main(String[] args) {
		
		System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		String username = "Zanetti4";
		String email = "zanetti4@gmail.com";
		String password = "Zanetti4Ever.";
		String actualUsername = "";
		String actualEmail = "";
		Boolean ret = false;
		
		driver.get("http://localhost:8080/EasyTravel/signup.jsp");
		driver.findElement(By.xpath("//*[@id=\"contact_form\"]/div[2]/div/div/input")).sendKeys("Javier");
		driver.findElement(By.xpath("//*[@id=\"contact_form\"]/div[3]/div/div/input")).sendKeys("Zanetti");
		driver.findElement(By.xpath("//*[@id=\"contact_form\"]/div[4]/div/div/input")).sendKeys(email);
		driver.findElement(By.xpath("//*[@id=\"contact_form\"]/div[5]/div/div/input")).sendKeys(username);
		driver.findElement(By.xpath("//*[@id=\"contact_form\"]/div[6]/div/div/input")).sendKeys(password);
		driver.findElement(By.xpath("//*[@id=\"contact_form\"]/div[7]/input")).click();
		driver.findElement(By.xpath("//*[@id=\"myNavbar\"]/ul[1]/li[4]/a")).click();
		
		WebElement elemUsername = driver.findElement(By.xpath("//*[@id=\"usrInf\"]/h4[1]"));
		WebElement elemEmail = driver.findElement(By.xpath("//*[@id=\"usrInf\"]/h4[2]"));
		
		actualUsername = elemUsername.getText();
		actualEmail = elemEmail.getText();
	
		if(actualUsername.equals(username) && actualEmail.equals(email)) {
			ret = true;
		}
		
		assertEquals(true, ret);
		
		driver.close();

	}

}
