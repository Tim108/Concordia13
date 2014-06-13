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
	public void testUser() {
		clickButton("acc");
		assertLinkPresentWithExactText("Gegevens");
		assertLinkPresentWithExactText("Reserveringen");
		assertLinkPresentWithExactText("Expositie");
		assertLinkPresentWithExactText("Mijn gehuurde werken");
		assertLinkPresentWithExactText("Abonnement afsluiten");
		assertLinkPresentWithExactText("Log uit");
		
		clickLinkWithExactText("Gegevens");
		
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
}
