package demo;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;


import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class TestCases {
    ChromeDriver driver;
    public TestCases(){
        System.out.println("Constructor: TestCases");
        WebDriverManager.chromedriver().timeout(30).setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public void endTest(){
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();
    }

    
    public  void testCase01() throws InterruptedException{
        driver.get("https://forms.gle/wjPkzeSEk1CM7KgGA");

        //enter name
        writeText(By.xpath("(//input[@jsname='YPqjbf'])[1]"), "Jack Jones");

        //enter Why are you practicing Automation?
        long epoch = System.currentTimeMillis()/1000;
        writeText(By.xpath("//textarea[@jsname='YPqjbf']"), "I want to be the best QA Engineer! "+epoch);

        //enter Automation Experience
        selectExperience(By.xpath("//div[@class='SG0AAe']//label"), 6);

        //select skills
        List<String> skills = Arrays.asList("Java", "Selenium", "TestNG");
        selectSkills(By.className("eBFwI"), skills);

        //select salutation mr/mrs/miss/others
        selectSalutation(By.xpath("//span[text()='Choose']"),"Mr");

        //select date, seven days ago
        writeText(By.xpath("//input[@type='date']"), date7daysAgo());

        //enter time
        enterTime();
       
        //navigate to amazom.in
        driver.navigate().to("https://www.amazon.in");
        Thread.sleep(2000);

        //dismiss the alert
        driver.switchTo().alert().dismiss();;

        //submit the form
        WebElement submitBtn = driver.findElement(By.xpath("//span[text()='Submit']"));
        submitBtn.click();

        //Print success message
        WebElement successMsg = driver.findElement(By.xpath("//div[@class='vHW8K']"));
        System.out.println(successMsg.getText());
    }

    public void writeText(By locator, String textToEnter){
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement textBox = driver.findElement(locator);

            textBox.clear();
            textBox.sendKeys(textToEnter);
        }catch (ElementNotInteractableException e) {
            System.out.println("Exception Occured! " + e.getMessage());
        }
    }

    public void selectExperience(By locator, int n){
        int i;
        if(n>=0 && n<=2){
            i=1;
        }
        else if (n>=3 && n<=5){
            i=2;
        }
        else if (n>=6 && n<=10){
            i=3;
        }
        else{
            i=4;
        }
        List<WebElement> radioBtns = driver.findElements(By.xpath("//div[@class='SG0AAe']//label"));
        if(i<=radioBtns.size()){
            radioBtns.get(i-1).click();
        }
        else{
            throw new RuntimeException("Unexpected number of radio buttons found");
        }

    }

    public void selectSkills(By locator, List<String> skills){
        List<WebElement> skillsToSelect = driver.findElements(locator);
        for(WebElement skill : skillsToSelect){
            if(skills.contains(skill.getText())){
                skill.click();
            }
        }
    }

    public void selectSalutation(By dropdownLocator, String salutation){
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownLocator));
            WebElement dropDown = driver.findElement(dropdownLocator);
            dropDown.click();

            WebElement salutationCheckBox = wait.until(ExpectedConditions.visibilityOfElementLocated( By.xpath("(//span[text()='"+salutation+"'])[2]")));
            salutationCheckBox.click();   
        }catch (Exception e) {
            System.out.println("Exception Occured! " + e.getMessage());
        }
    }

    public String date7daysAgo(){
        LocalDate today = LocalDate.now();
        LocalDate date7DaysAgo = today.minusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
        String formattedDate = date7DaysAgo.format(formatter);
        return formattedDate;
    }

    public void enterTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("hh");
        String hours = hourFormatter.format(now);
        DateTimeFormatter minuteFormatter = DateTimeFormatter.ofPattern("mm");
        String minutes = minuteFormatter.format(now);
        String ampm = now.getHour() < 12 ? "AM" : "PM";
      
        // Enter hours
        writeText(By.xpath("(//input[@type='number'])[1]"), hours);
      
        // Enter minutes
        writeText(By.xpath("(//input[@type='number'])[2]"), minutes);
      
        /*
        Commented due to aut issue, the input field disappears when we enter the time(hours and mins) 
        on certain browsers
        */
        // Enter AM/PM 
        //selectAmPm(ampm);
      }
      
    private void selectAmPm(String ampm) {
        try{
            WebElement ampmElement = driver.findElement(By.xpath("(//div[@jsname='wQNmvb']//span[@class='vRMGwf oJeWuf'])[7]"));
            ampmElement.click();
            driver.findElement(By.xpath("//div[@data-value='"+ampm+"']")).click();
        }catch(NoSuchElementException e){
            System.out.println("Exception Occured! " + e.getMessage());
        }
        
      }
}
    
    

