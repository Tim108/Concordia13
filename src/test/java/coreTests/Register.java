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
		//assertTitleEquals("Registreren");
		setTextField("name", "Testy");
		setTextField("surname", "Tester");
		setTextField("address", "Teststreet");
		setTextField("housenumber", "8");
		setTextField("city", "Testercity");
		setTextField("postal", "1122AB");
		setTextField("phone", "1122334455");
		setTextField("email", "testy@testserver.com");
		setTextField("pass", "notpassword");
		System.out.println("2");
		submit();
		assertTitleEquals("Populaire werken");
	}
}