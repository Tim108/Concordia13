package coreTests;

import static org.junit.Assert.*;
import java.io.IOException;
import static org.mockito.Mockito.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.*;
import coreServlets.RegisterServlet;

public class RegisterTest {
	HttpServletRequest request;
	HttpServletResponse response;
	
	@Before
    public void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @Test
    public void checkRegister() throws ServletException, IOException {
		request.setAttribute("name", "Name");
		request.setAttribute("surname", "Surname");
		request.setAttribute("address", "Address");
		request.setAttribute("housenumber", "1");
		request.setAttribute("city", "City");
		request.setAttribute("postal", "1111AA");
		request.setAttribute("newsletter", "on");
		request.setAttribute("phone", "0611111111");
    	request.setAttribute("email", "email@gmail.com");
    	request.setAttribute("password", "wachtwoord");

    	System.out.println(request.getAttribute("name"));
    	
        new RegisterServlet().doPost(request, response);
    }
}
