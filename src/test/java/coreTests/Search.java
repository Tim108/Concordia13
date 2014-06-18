package coreTests;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import org.junit.*;

public class Search {
	
	@Before
	public void prepare() {
		setBaseUrl("http://localhost:8080/concordia");
		beginAt("");
		clickElementByXPath("//*[@id=\"bs-example-navbar-collapse-1\"]/ul[1]/li[2]/a");		// Click on 'Collectie'
	}

	@Test
	public void testSearch() {	
		assertFormPresent("search");														// Check if the search bar is present
		setTextField("srch-term","Hangen");													// Fill the search bar with 'Hangen' 
		clickElementByXPath("//*[@id=\"search\"]/div[1]/div/div[1]/button");				// Click on the search icon
		
		assertTextPresent("Hangen");														// Check if 'Hangen' is present
		assertTextNotPresent("Kleurtjes");													// Check if 'Kleurtjes' isn't present
		
		clickElementByXPath("//*[@id=\"search\"]/div[1]/div/div[1]/button");				// Click on the search icon to reset the results
		clickElementByXPath("//*[@id=\"search\"]/div[1]/div/div[2]/button");				// Click on the advanced search icon
		
		assertElementPresentByXPath("//*[@id=\"minPrice\"]");								// Check if the min. price text field is present
		assertCheckboxPresent("Klaas Sikkel");												// Check if you can select the artist
		assertElementPresentByXPath("//*[@id=\"minBred\"]");								// Check if the min. width text field is present
		assertCheckboxPresent("Post-Modernistisch");										// Check if you can select a style
		assertCheckboxPresent("Rondjes");													// Check if you can select a technique
		assertCheckboxPresent("Landschap");													// Check if you can select a orientation
		assertElementPresentByXPath("//*[@id=\"minRat\"]");									// Check if the min. rating text field is present
		
		setTextField("minPrice","300");
		checkCheckbox("Klaas Sikkel");
		setTextField("minBred","10");
		checkCheckbox("Post-Modernistisch");
		checkCheckbox("Rondjes");
		checkCheckbox("Landschap");
		setTextField("minRat","4");
		
		clickElementByXPath("//*[@id=\"search\"]/div[1]/div/div[1]/button");
		
		assertTextPresent("Kleurtjes");
		assertTextNotPresent("Hangen");
		
		clickElementByXPath("//*[@id=\"search\"]/div[1]/div/div[1]/button");
		clickElementByXPath("//*[@id=\"search\"]/div[1]/div/div[2]/button");
		
		assertElementPresentByXPath("//*[@id=\"minPrice\"]");
		assertCheckboxPresent("Klaas Sikkel");
		assertElementPresentByXPath("//*[@id=\"minBred\"]");
		assertCheckboxPresent("Post-Modernistisch");
		assertCheckboxPresent("Rondjes");
		assertCheckboxPresent("Landschap");
		assertElementPresentByXPath("//*[@id=\"minRat\"]");
		
		setTextField("minPrice","300");
		checkCheckbox("Klaas Sikkel");
		setTextField("minBred","10");
		checkCheckbox("Illusie");
		checkCheckbox("Rondjes");
		checkCheckbox("Landschap");
		setTextField("minRat","4");
		
		clickElementByXPath("//*[@id=\"search\"]/div[1]/div/div[1]/button");
		
		assertTextPresent("Kleurtjes");
		assertTextPresent("Hangen");
		assertTextPresent("Niks gevonden!");
		
		clickElementByXPath("//*[@id=\"search\"]/div[1]/div/div[1]/button");
		clickElementByXPath("//*[@id=\"search\"]/div[1]/div/div[2]/button");
		
		setTextField("minPrice","abc");
		checkCheckbox("Klaas Sikkel");
		setTextField("minBred","abc");
		checkCheckbox("Illusie");
		checkCheckbox("Rondjes");
		checkCheckbox("Landschap");
		setTextField("minRat","abc");
		
		clickElementByXPath("//*[@id=\"search\"]/div[1]/div/div[1]/button");
		
		assertTextPresent("Kleurtjes");
		assertTextPresent("Hangen");
	}
}
