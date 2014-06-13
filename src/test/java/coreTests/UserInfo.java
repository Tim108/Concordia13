package coreTests;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import net.sourceforge.jwebunit.junit.JWebUnit;
import org.junit.*;

public class UserInfo {
	
	@Before
	public void prepare() {
		setBaseUrl("http://localhost:8080/concordia");
	}

	@Test
	public void testUserInfo() {
		beginAt("");
		clickElementByXPath("//*[@id=\"acc\"]");
		assertLinkPresent("userinfo");
		clickLink("userinfo");
		
		assertLabelMatches("Gegevens");
		assertLabelMatches("Contactgegevens");
		assertLabelMatches("Testy");
		assertLabelMatches("Tester");
		assertLabelMatches("Teststreet 8");
		assertLabelMatches("Testercity");
		assertLabelMatches("1122AB");
		assertLabelMatches("1122334455");
		assertLabelMatches("testy@testserver.com");
		assertLabelMatches("€0");
	}
	
	@Test
	public void testReservation() {
		beginAt("");
		clickElementByXPath("//*[@id=\"acc\"]");
		assertLinkPresent("reservations");
		clickLink("reservations");
	}
	
	@Test
	public void testExposition() {
		beginAt("");
		clickElementByXPath("//*[@id=\"acc\"]");
		assertLinkPresent("exposition");
		clickLink("exposition");
		
		assertLabelMatches("U hebt nog geen eigen Expositie!");
	}
	
	@Test
	public void testRented() {
		beginAt("");
		clickElementByXPath("//*[@id=\"acc\"]");
		assertLinkPresent("rented");
		clickLink("rented");
	}
	
	@Test
	public void testSubscription() {
		beginAt("");
		clickElementByXPath("//*[@id=\"acc\"]");
		assertLinkPresent("subscription");
		clickLink("subscription");
		
		assertButtonPresentWithText("Sluit regulier abonnement af");
		assertButtonPresentWithText("Sluit spaarabonnement af");
	}
	
	@Test
	public void testLogOut() {
		beginAt("");
		clickElementByXPath("//*[@id=\"acc\"]");
		assertLinkPresent("logout");
		clickLink("logout");
		
		assertButtonPresentWithText("Terug");
		clickButtonWithText("Terug");
		
		clickElementByXPath("//*[@id=\"acc\"]");
		JWebUnit.assertFormPresent("loginform");
	}
}
