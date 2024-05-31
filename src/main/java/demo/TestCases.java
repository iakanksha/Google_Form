package demo;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class TestCases {
    ChromeDriver driver;
    public TestCases()
    {
        System.out.println("Constructor: TestCases");
        WebDriverManager.chromedriver().timeout(30).setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    }

    public void endTest()
    {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    
    public  void testCase01() throws InterruptedException{
    
        driver.get("https://forms.gle/wjPkzeSEk1CM7KgGA");
        Thread.sleep(5000);

        //enter name
        WebElement nameBox = driver.findElement(By.xpath("(//input[@jsname='YPqjbf'])[1]"));
        nameBox.click();
        nameBox.sendKeys("John Doe");

        //enter Why are you practicing Automation?
        WebElement whyAutomationBox = driver.findElement(By.xpath("//textarea[@jsname='YPqjbf']"));
        whyAutomationBox.click();
        long epoch = System.currentTimeMillis()/1000;
        whyAutomationBox.sendKeys("I want to be the best QA Engineer! "+epoch);

        //enter Automation Experience
        int n=50;
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


        //select skills
        List<String> skills = Arrays.asList("Java", "Selenium", "TestNG");
        List<WebElement> skillsToSelect = driver.findElements(By.className("eBFwI"));
            for(WebElement skill : skillsToSelect){
            if(skills.contains(skill.getText())){
                skill.click();
            }
        }

        //select salutation mr/mrs/miss/others
        String salutation="Dr";
        WebElement dropDown = driver.findElement(By.xpath("//span[text()='Choose']"));
        dropDown.click();
        if(salutation.equals("Mr")){
            WebElement mr = driver.findElement(By.xpath("(//span[text()='Mr'])[2]"));
            mr.click();
        }
        if(salutation.equals("Mrs")){
            WebElement mrs = driver.findElement(By.xpath("(//span[text()='Mrs'])[2]"));
            mrs.click();
        }
        if(salutation.equals("Dr")){
            WebElement dr = driver.findElement(By.xpath("(//span[text()='Dr'])[2]"));
            dr.click();
        }
        if(salutation.equals("Ms")){
            WebElement ms = driver.findElement(By.xpath("(//span[text()='Ms'])[2]"));
            ms.click();
        }
        if(salutation.equals("Rather not say")){
            WebElement notSay = driver.findElement(By.xpath("(//span[text()='Rather not say'])[2]"));
            notSay.click();
        }

        //select date, seven days ago
        LocalDate today = LocalDate.now();
        LocalDate date7DaysAgo = today.minusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
        String formattedDate = date7DaysAgo.format(formatter);

        WebElement dateCell = driver.findElement(By.xpath("//input[@type='date']"));
        dateCell.sendKeys(formattedDate);


        //enter time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("hh");
        String hours = hourFormatter.format(now);
        DateTimeFormatter minuteFormatter = DateTimeFormatter.ofPattern("mm");
        String minutes = minuteFormatter.format(now);
        String ampm = now.getHour() < 12 ? "AM" : "PM";

        // Enter hours
        WebElement hourElement = driver.findElement(By.xpath("(//input[@type='number'])[1]"));
        hourElement.sendKeys(hours);

        // Enter minutes
        WebElement minuteElement = driver.findElement(By.xpath("(//input[@type='number'])[2]"));
        minuteElement.sendKeys(minutes);

        // Enter AM/PM
        // WebElement ampmElement = driver.findElement(By.xpath("(//div[@jsname='wQNmvb']//span[@class='vRMGwf oJeWuf'])[7]"));
        // ampmElement.click();

        // if(ampm.equals("AM"))
        //     driver.findElement(By.xpath("//div[@data-value='AM']")).click();
        // else 
        // driver.findElement(By.xpath("//div[@data-value='PM']")).click();
        

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
}

    
    

