import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MetalShop {

    static WebDriver driver = new ChromeDriver();
    String password = "tester123";
    String username = "test12";

    @BeforeAll
    public static void setUp() {
        driver.manage().window().maximize();
        driver.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/");
    }

    //before lub after all zastosować
    @AfterAll
    public static void closeBrowser() {
        driver.quit();
    }

    @BeforeEach
    public void goTohomePage() {
        driver.findElement(By.linkText("Sklep")).click();
    }

    @Test
    public void emptyLogin() {
        driver.findElement(By.linkText("Moje konto")).click();
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();
        String error = driver.findElement(By.cssSelector(".woocommerce-error")).getText();
        Assertions.assertEquals("Błąd: Nazwa użytkownika jest wymagana.", error);
    }

    @Test
    public void emptyPassword() {
        driver.findElement(By.linkText("Moje konto")).click();
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.name("login")).click();
        String error = driver.findElement(By.cssSelector(".woocommerce-error")).getText();
        Assertions.assertEquals("Błąd: pole hasła jest puste.", error);
    }

    @Test
    public void registerSuccess() {
        driver.findElement(By.linkText("register")).click();
        Faker faker = new Faker();
        String registerUsername = faker.name().username();
        String email = registerUsername + faker.random().nextInt(10000) + "@wp.pl";
        driver.findElement(By.cssSelector("#user_login")).sendKeys(registerUsername);
        driver.findElement(By.cssSelector("#user_email")).sendKeys(email);
        driver.findElement(By.cssSelector("#user_pass")).sendKeys(password);
        driver.findElement(By.cssSelector("#user_confirm_password")).sendKeys(password);
        driver.findElement(By.cssSelector(".ur-submit-button")).click();
        Wait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".user-registration-message")));
        WebElement error = driver.findElement(By.cssSelector(".user-registration-message"));
        Assertions.assertEquals("User successfully registered.", error.getText());
    }

    @Test
    public void findLogo() {
        driver.manage().window().maximize();
        driver.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/");
        driver.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/moje-konto/");
        Assertions.assertTrue(driver.findElement(By.linkText("Softie Metal Shop")).isEnabled());
        Assertions.assertTrue(driver.findElement(By.cssSelector(".woocommerce-product-search")).isEnabled());
    }
    @Test
    public void goToKontakt() {
        driver.findElement(By.linkText("Kontakt")).click();
        driver.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/kontakt/");
        Assertions.assertTrue(driver.findElement(By.linkText("Kontakt")).isEnabled());
    }

    @Test
    public void goFromLogInToMain() {
        driver.manage().window().maximize();
        driver.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/moje-konto/");
        driver.findElement(By.cssSelector(".site-branding")).click();
        Assertions.assertTrue(driver.findElement(By.linkText("Sklep")).isEnabled());
    }
    @Test
    public void sendMessage() {
        driver.manage().window().maximize();
        driver.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/kontakt/");
        String username= "Anna";
        String email = "anna1988@wp.pl";
        String subject = "kontakt";
        String mymessage = "proszę o kontakt";

        driver.findElement(By.name("your-name")).sendKeys(username);
        driver.findElement(By.name("your-email")).sendKeys(email);
        driver.findElement(By.name("your-subject")).sendKeys(subject);
        driver.findElement(By.name("your-message")).sendKeys(mymessage);
        driver.findElement(By.xpath("//input[@type='submit']")).click();


        Wait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".wpcf7-response-output")));
        String message = driver.findElement(By.cssSelector(".wpcf7-response-output")).getText();
        Assertions.assertEquals("Twoja wiadomość została wysłana. Dziękujemy!", message);
    }
}



