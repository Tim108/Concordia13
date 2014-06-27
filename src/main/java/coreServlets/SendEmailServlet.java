package coreServlets;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

public class SendEmailServlet {
	public void sendMail(String email, String type, String user, String userpass, String activation, int id) {
			String to =email;
			String subject = getSubject("register");
			String content = getMailText(type, user, email, userpass, activation, id);
			final String username = "con13cordia@gmail.com";
			final String password = "concon13";

			String host = "smtp.gmail.com";
			Properties props = new Properties();
			// set any needed mail.smtps.* properties here
			Session session = Session.getInstance(props);
			MimeMessage msg = new MimeMessage(session);

			// set the message content here
			Transport t = null;
			try {
				msg.setSubject(subject);
				msg.setContent(content, "text/html");
				t = session.getTransport("smtps");
				t.connect(host, username, password);
				t.sendMessage(msg, InternetAddress.parse(to));
				System.out.println("message sent");
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					t.close();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	
	public String getMailText(String type, String user, String usermail, String userpass, String activation, int id) {
		String email = "";
		if(type.equals("register")){
			email = 
					"Hallo " + user + ",<br>"
					+ "<br>"
					+ "U heeft zich succesvol geregistreerd bij de kunstuitleen van Concordia.<br>"
					+ "Uw inloggegevens zijn:<br>"
					+ "Email: " + usermail + "<br>"
					+ "Wachtwoord: " + userpass + "<br>"
					+ "<br>"
					+ "Uw account moet eerst geactiveerd worden. Dit kunt u doen door <a href=\"http://localhost:8080/di13_concordia/activateAccount?link="+activation+"&id="+id+"\">hier</a> te klikken.<br>"
					+ "Tevens is het mogelijk om uw account te activeren op de volgende manier te activeren:<br>"
					+ "Login op http://localhost:8080/di13_concordia/ en ga vervolgens naar http://localhost:8080/di13_concordia/activation.jsp<br>"
					+ "Daar kunt u uw account bevestigen met de volgende code: <b>" + activation + "</b> <br>"
					+ "<br>"
					+ "Met vriendelijke groet, <br>"
					+ "Concordia";
		}
		return email;
	}
	
	public String getSubject(String type) {
		String subject = "";
		if(type.equals("register")) subject = "Bedankt voor uw registratie bij Concordia Kunstuitleen!";
		return subject;
	}
}
