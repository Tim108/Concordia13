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
		
		assertLabelMatches("U hebt nog geen eigen Expositie!");
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
