package util;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	private String username = "evnCanTho@gmail.com";
	private String password = "evnCanTho2015";
	private Properties props;
	
	/**
	 */
	public SendMail() {
		this.username = "evnCanTho@gmail.com";
		this.password = "evnCanTho2015";
		this.props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
	}
	
	/**
	 * @param username
	 * @param password
	 */
	public SendMail(String username, String password) {
		this.username = username;
		this.password = password;
		this.props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
	}
	
	/**
	 * @param username
	 * @param password
	 * @param props
	 */
	public SendMail(String username, String password, Properties props) {
		this.username = username;
		this.password = password;
		this.props = props;
	  props.put("mail.smtp.host", "smtp.gmail.com");  
	  props.put("mail.smtp.socketFactory.port", "465");  
	  props.put("mail.smtp.socketFactory.class",  
	            "javax.net.ssl.SSLSocketFactory");  
	  props.put("mail.smtp.auth", "true");  
	  props.put("mail.smtp.port", "465");
	  
	props.put("mail.smtp.starttls.enable", "true");
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}
	
	public void send(Mail mail) {
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mail.getFrom()));
			message.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse(mail.getTo()));
			message.setSubject(mail.getSubject());
			StandardCharsets.UTF_8.displayName();
			message.setText(mail.getContent());
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	public static void main(String[] args) {
		SendMail s = new SendMail();
		Mail m = new Mail("Công việc được chia sẻ", "Bạn đã được chia sẻ công việc. Vui lòng vào hệ thống làm việc để kiểm tra", "evnCanTho@gmail.com", "tienb1208736@student.ctu.edu.vn");
		s.send(m);
	}
}
