package com.example;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ExerciseSteps {
    private WebDriver driver = null;
    private WebElement searchInputField = null;
    
    private void waitForVisibilityOf(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
    
    @After
    public void tearDown() {
        driver.quit();
    }
    
    @Given("^user has opened \"([^\"]*)\"$")
    public void openWebPage(String url) throws Throwable {
        driver.navigate().to(url);
        WebElement logo = driver.findElement(By.id("nav-logo"));
        waitForVisibilityOf(logo);
    }
    
    @When("^user see search input field$")
    public void seeSearchInputField() throws Throwable {
        searchInputField = driver.findElement(By.cssSelector("#twotabsearchtextbox"));
        Assert.assertTrue("Search field not found", searchInputField.isDisplayed());
    }
    
    @Then("^user search product \"([^\"]*)\"$")
    public void searchProduct(String product) throws Throwable {
        searchInputField.click();
        searchInputField.sendKeys(product);
        WebElement goButton = driver.findElement(By.cssSelector(".nav-search-submit > input:nth-child(2)"));
        goButton.click();
    }
    
    @When("^user sort results$")
    public void sortResults() throws Throwable {
        WebElement sortSelect = driver.findElement(By.cssSelector("#sort"));
        waitForVisibilityOf(sortSelect);
        sortSelect.click();
        sleep(1000);
        WebElement highToLow = driver.findElement(By.cssSelector("#sort > option:nth-child(4)"));
        highToLow.click();
        sleep(5000);
    }
    
    @Then("^user selects the second item and click it open$")
    public void selectItemForDetails() throws Throwable {
        WebElement sortedResultElement = driver.findElement(By.cssSelector("#s-results-list-atf"));
        
        List<WebElement> h2Elements = sortedResultElement.findElements(By.tagName("h2"));
        if (h2Elements.size() != 0) {
            WebElement secondElement = h2Elements.get(1);
            secondElement.click();
        } else {
            Assert.fail("Could not find any <h2> elements");
        }
        
        WebElement topicElement = driver.findElement(By.cssSelector("#productTitle"));
        waitForVisibilityOf(topicElement);
        
        Assert.assertTrue("Topic does not contain text 'Nikon D3X'",
                topicElement.getText().contains("Nikon D3X"));
    }
}