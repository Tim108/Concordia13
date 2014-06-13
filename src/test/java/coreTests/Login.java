package coreTests;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import net.sourceforge.jwebunit.junit.JWebUnit;
import org.junit.*;

public class Login {
	
	@Before
	public void prepare() {
		setBaseUrl("http://localhost:8080/concordia");
	}

	@Test
	public void testLogin() {
		System.out.println("0");
		beginAt("");
		System.out.println("a");
		assertElementPresent("acc");
		clickElementByXPath("//*[@id=\"acc\"]");
		setTextField("email", "testy@testserver.com");
		setTextField("password", "notpassword");
		submit();
		JWebUnit.assertLinkPresent("Testy Tester");
	}
}
