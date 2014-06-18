package coreTests;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import net.sourceforge.jwebunit.junit.JWebUnit;
import org.junit.*;

public class UserInfo {
	
	@Before
	public void prepare() {
		setBaseUrl("http://localhost:8080/concordia");
		beginAt("");
		clickElementByXPath("//*[@id=\"acc\"]");
		setTextField("email", "testy@testserver.com");
		setTextField("password", "notpassword");
		submit();
	}

	@Test
	public void testUserInfo() {	
		clickElementByXPath("//*[@id=\"acc\"]");		
		assertLinkPresent("userinfo");
		clickLink("userinfo");
		
		assertTextPresent("Gegevens");
		assertTextPresent("Gegevens");
		assertTextPresent("Contactgegevens");
		assertTextPresent("Testy");
		assertTextPresent("Tester");
		assertTextPresent("Teststreet 8");
		assertTextPresent("Testercity");
		assertTextPresent("1122AB");
		assertTextPresent("1122334455");
		assertTextPresent("testy@testserver.com");
		assertTextPresent("€0");
	}
	
	@Test
	public void testReservation() {
		clickElementByXPath("//*[@id=\"acc\"]");
		assertLinkPresent("reservations");
		clickLink("reservations");
	}
	
	@Test
	public void testExposition() {
		clickElementByXPath("//*[@id=\"acc\"]");
		assertLinkPresent("exposition");
		clickLink("exposition");
		
		assertTextPresent("U hebt nog geen eigen Expositie! Maak hem hier!");
	}
	
	/*@Test
	public void testRented() {
		clickElementByXPath("//*[@id=\"acc\"]");
		assertLinkPresent("rented");
		clickLink("rented");
	}*/
	
	@Test
	public void testSubscription() {
		clickElementByXPath("//*[@id=\"acc\"]");
		assertLinkPresent("subscription");
		clickLink("subscription");
		
		assertButtonPresent("regulier");
		assertButtonPresent("spaar");
		
		clickButton("regulier");
		assertTextPresent("Weet u zeker dat u een regulier abonnement wilt afsluiten?");
		assertElementPresentByXPath("//*[@id=\"ideal\"]");
		assertSubmitButtonPresent();
		
		clickButton("spaar");
		assertTextPresent("Weet u zeker dat u een spaarabonnement wilt afsluiten?");
		assertElementPresentByXPath("//*[@id=\"ideal\"]");
		assertSubmitButtonPresent();
		
		clickButton("regulier");
		assertTextPresent("Weet u zeker dat u een regulier abonnement wilt afsluiten?");
		assertElementPresentByXPath("//*[@id=\"ideal\"]");
		
		clickElementByXPath("//*[@id=\"ideal\"]");
		assertTextPresent("U heeft succesvol een regulier abonnement afgesloten.");
		
		clickElementByXPath("//*[@id=\"acc\"]");
		clickLink("userinfo");
		
		assertTextPresent("Abonnement 1");
		assertTextPresent("Nee");
	}
	
	@Test
	public void testLogOut() {
		clickElementByXPath("//*[@id=\"acc\"]");
		assertLinkPresent("logout");
		clickLink("logout");
		
		assertButtonPresentWithText("Terug");
		clickButtonWithText("Terug");
		
		clickElementByXPath("//*[@id=\"acc\"]");
		JWebUnit.assertFormPresent("loginform");
	}
}
