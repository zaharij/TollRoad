/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tollroad.service;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 * something like that
 * @author Zakhar
 */
public class SendEmail {
    private String subjectLine = "TallRoad info";
    private String succInfo = "Sent message successfully....";
    private String text = null;

   public static void main(String [] args) {    
      String to = "abcd@gmail.com";
      String from = "web@gmail.com";
      String host = "localhost";
      Properties properties = System.getProperties();
      properties.setProperty("mail.smtp.host", host);
      Session session = Session.getDefaultInstance(properties);

      try {
         MimeMessage message = new MimeMessage(session);
         message.setFrom(new InternetAddress(from));
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
         message.setSubject(subjectLine);
         message.setText("This is actual message");
         Transport.send(message);
         System.out.println(succInfo);
      }catch (MessagingException mex) {
         mex.printStackTrace();
      }
   }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
}
}
