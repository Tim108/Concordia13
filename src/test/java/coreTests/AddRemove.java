package coreTests;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import net.sourceforge.jwebunit.junit.JWebUnit;
import org.junit.*;

// We need an admin account for this test.
// Please fill in the variables for username and pass.

public class AddRemove {
	private String username = "tsonderen108@gmail.com";
	private String pass = "timtim";
	
	@Before
	public void prepare() {
		setBaseUrl("http://localhost:8080/concordia");
	}

	@Test
	public void testLogin() {
		beginAt("");
		assertElementPresent("acc");
		clickElementByXPath("//*[@id=\"acc\"]");
		setTextField("email", username);
		setTextField("password", pass);
		submit();
		assertElementPresent("cp");
		clickElementByXPath("//*[@id=\"cp\"]");
		assertTextPresent("Concordia Controlepaneel");
		assertButtonPresent("addWork");
		clickElementByXPath("//*[@id=\"addWork\"]");
		setTextField("title", "TestPainting");
		setTextField("artist", "Tester");
		setTextField("width", "50");
		setTextField("heigth", "60");
		setTextField("technique", "test");
		setTextField("style", "stillTesting");
		setTextField("price", "5");
		JWebUnit.clickRadioOption("orientation", "portret");
	}
}
