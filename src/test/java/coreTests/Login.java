package coreTests;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import net.sourceforge.jwebunit.junit.JWebUnit;
import org.junit.*;

// IMPORTANT!
// First run the Register test, so the account we use in here is created.

public class Login {
	
	@Before
	public void prepare() {
		setBaseUrl("http://localhost:8080/concordia");
	}

	@Test
	public void testLogin() {
		beginAt("");
		assertElementPresent("acc");
		clickElementByXPath("//*[@id=\"acc\"]");
		setTextField("email", "testy@testserver.com");
		setTextField("password", "notpassword");
		submit();
		JWebUnit.assertTextPresent("Populaire werken");
	}
}
