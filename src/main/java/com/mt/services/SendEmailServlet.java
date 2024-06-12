package com.yourpackage;

import java.io.IOException;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

@WebServlet("/sendEmail")
public class SendEmailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("Name");
        String email = request.getParameter("Email");
        String subject = request.getParameter("Subject");
        String comment = request.getParameter("Comment");

        String to = "vlonjecha@gmail.com"; // Replace with your email address
        String from = email;
        String host = "smtp.example.com"; // Replace with your SMTP server

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587"); // Replace with your SMTP port

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("vlonjecha@gmail.com", "your_password"); // Replace with your email and password
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText("Name: " + name + "\nEmail: " + email + "\nSubject: " + subject + "\nComment: " + comment);

            Transport.send(message);
            response.getWriter().println("Message sent successfully!");
        } catch (MessagingException mex) {
            mex.printStackTrace();
            response.getWriter().println("Failed to send message.");
        }
    }
}
