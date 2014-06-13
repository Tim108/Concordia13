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
		JWebUnit.clickButton("acc");
	}
}
