import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class n11 {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://www.n11.com";
    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
  }

  @Test
  public void n11TestCase() throws Exception {
		  
	String[] prodCodeParams;
	String 	 prodCode;
	String 	 favProd;
	
	// Ana sayfaya git
	driver.get(baseUrl + "/");
    
    // Ana sayfanın geldiğinin kontrolu. Title kontrolu
    assertEquals(driver.getTitle(),"n11.com - Alışverişin Uğurlu Adresi");
    
    // Login sayfasına giriş
    driver.findElement(By.linkText("Giriş Yap")).click();
    
    // login sayfasının geldiğinin kontrolü
    assertEquals(driver.findElement(By.xpath("(//div[@class='leftBlock']/h2)")).getText(), "Üye Girişi");
  
    //login işlemi
    driver.findElement(By.id("email")).sendKeys("aysenursimsek@gmail.com");
    driver.findElement(By.id("password")).sendKeys("sa4036");
    driver.findElement(By.id("loginButton")).click();

    // login işleminin gerçekleştiğinin kontrolu
    assertTrue(isElementPresent(By.className("username")));
    
    // "samsung" kelimesini ara
    driver.findElement(By.id("searchData")).sendKeys("samsung");
    
    // Ara butonuna tıkla
    driver.findElement(By.cssSelector("span.icon.iconSearch")).click();
    
    // Aranan kelime için sonuç bulunduğunun kontrolu
    assertThat(driver.findElement(By.cssSelector("div.resultText")).getText(), CoreMatchers.containsString("sonuç bulundu"));

    //2. sayfaya git
    driver.findElement(By.linkText("2")).click();

    //2. sayfada olduğunun kontrolu
    assertEquals(driver.findElement(By.xpath("//input[@class='currentPage']")).getAttribute("value"), "2");

    // Üstten 3. ürünün kodu. Tire(-) işaretine göre ayrılıp,  sadece numara kısmı kullanılacak. Favorilerim sayfasındaki, ürünle karşılaştırmak için kullanılacak
    prodCode = driver.findElement(By.xpath("//li[@itemprop='itemListElement'][3]/div")).getAttribute("id");
    prodCodeParams = prodCode.split("-");
    
    // Üstten 3. ürünü Favorilere Ekle
    driver.findElement(By.xpath("(//span[contains(@class,'followBtn')])[3]")).click();
     
    // Hesabım linkine tıkla
    driver.findElement(By.linkText("Hesabım")).click();

    // Hesabım sayfasında olduğunun kontrolu
    assertThat(driver.findElement(By.cssSelector("div.profile > span")).getText(), CoreMatchers.containsString("Merhaba"));
    assertEquals(driver.findElement(By.cssSelector("li.caption")).getText(), "Sipariş Durumu");

    // Favorilerim sayfasına git
    driver.findElement(By.xpath("(//a[contains(text(),'Favorilerim')])[2]")).click();

    // Favorilerim sayfasında olduğunun kontrolu
    assertEquals(driver.findElement(By.id("buyerProductWatchLegend")).getText(), "Favorilerim");
    
    // Daha önceden, Favorilerime eklenen ürünün listede olup olmadığının kontrolu
    favProd = driver.findElement(By.xpath("//td[@class='productTitle']/p/a")).getAttribute("href");
    assertThat(favProd, CoreMatchers.containsString(prodCodeParams[1]));

    // Kaldır linkine tıkla
    driver.findElement(By.linkText("Kaldır")).click();  
    
    // Favorim listesinin boş oldğunun kontrolu
    assertTrue(isElementPresent(By.className("emptyWatchList")));
	  
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
  
  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
    	return false;
    }
  }

}
