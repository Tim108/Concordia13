package coreTests;

import org.junit.Test;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import net.sourceforge.jwebunit.util.TestingEngineRegistry;

import org.junit.Before;


public class Register {

	@Before
	public void prepare() {
		System.out.println("a");
		setBaseUrl("http://localhost:8080/concordia");
		System.out.println("b");
	}

	@Test
	public void testRegister() {
		System.out.println("0");
		beginAt("/register.jsp");
		System.out.println("1");
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
		System.out.println("2");
		submit();
	}
}