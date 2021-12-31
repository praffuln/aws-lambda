package com.amazonaws.lambda.demo;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

 

/**
 * Most of AuthenticationFieldException Error occur when sign-in attempted
 * prevented, login your gmail first and go to
 * https://www.google.com/settings/security/lesssecureapps 
 * and check turn on. 
 * 
 * https://accounts.google.com/DisplayUnlockCaptcha
 * Allow here to captcha. 
 * 
 */
public class SendMailTLS {

	private static String fromEmail = "prafful.namdev18@gmail.com";

	/**
	 * 
	 * @param toEmail
	 * @param txtMessage
	 * @return
	 */
	public static boolean sendMailTLS(String toEmail,String txtMessage) {

		//change me.
		final String username = "prafful.namdev18@gmail.com";
		final String password = "";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
//		props.put("mail.smtp.host", "outlook.office365.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
         session.setDebug(true);
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail ));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(toEmail));
			message.setSubject("Order From App");
			message.setText(txtMessage);

			Transport.send(message);

			System.out.println("Done");
            return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) {
		sendMailTLS("prafful.namdev18@gmail.com", "hi");
	}
}