package com.jmaaix.testttttt;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 // Your email password
public class EmailSender {

         public static void sendEmail(String recipientEmail,String username) throws Exception {
             // Email sending code
             Properties props = new Properties();
             props.put("mail.smtp.auth", "true");
             props.put("mail.smtp.starttls.enable", "true");
             props.put("mail.smtp.host", "smtp.gmail.com");
             props.put("mail.smtp.port", "587");

             Session session = Session.getInstance(props, new Authenticator() {
                 protected PasswordAuthentication getPasswordAuthentication() {
                     return new PasswordAuthentication("mohamed.jmai@esprit.tn", "kpzvxtjtrtnvkuaz");
                 }
             });

             try {
                 Message message = new MimeMessage(session);
                 message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail)); // Set the recipient's email address
                 message.setSubject("Welcome to JetSetGo");

                 // Set the email content
                 message.setText("Hi "+username+"\n welcome to JetSetGo!");
                 Transport.send(message);
             } catch (Exception e) {
                 throw new Exception("Error sending email: " + e.getMessage(), e);
             }
         }

     }



