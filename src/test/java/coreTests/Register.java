package coreTests;

import org.junit.Test;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

import org.junit.Before;


public class Register {

	@Before
	public void prepare() {
		setBaseUrl("http://localhost:8080/concordia");
	}

	@Test
	public void testRegister() {
		beginAt("/register.jsp");
		assertFormPresent();
		assertFormElementPresent("name");
		setTextField("name", "Testy");
		assertFormElementPresent("surname");
		setTextField("surname", "Tester");
		assertFormElementPresent("address");
		setTextField("address", "Teststreet");
		assertFormElementPresent("housenumber");
		setTextField("housenumber", "8");
		assertFormElementPresent("city");
		setTextField("city", "Testercity");
		assertFormElementPresent("postal");
		setTextField("postal", "1122AB");
		assertFormElementPresent("phone");
		setTextField("phone", "1122334455");
		assertFormElementPresent("email");
		setTextField("email", "testy@testserver.com");
		assertFormElementPresent("pass");
		setTextField("pass", "notpassword");
		assertFormElementPresent("newsletter");
		assertCheckboxSelected("newsletter");
		uncheckCheckbox("newsletter");
		assertCheckboxNotSelected("newsletter");
		submit();
	}
}