package com.flowershop.service.demo;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;
import java.time.LocalDate;

// https://app.sendgrid.com/guide/integrate/langs/java
public class EmailNotificationServiceDemo {

    public static void main(String[] args) throws IOException {

        String apiKey = System.getenv("API_KEY");
        String emailFrom = System.getenv("EMAIL_FROM");
        String emailTo = System.getenv("EMAIL_TO");

        Email from = new Email(emailFrom);
        String subject = "Thank you for placing your order with Daniel's Flower Shop \uD83C\uDF3B";
        Email to = new Email(emailTo);
        Content content = new Content("text/plain", "Good to see you Gray class :) on " + LocalDate.now());
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }
}
