package com.flowershop.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.flowershop.exception.EmailServiceException;
import com.flowershop.model.viewmodel.OrderDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Implementation of the {@link EmailService} using Twillio's API
 *
 * @See https://www.twilio.com/en-us/sendgrid/email-api
 * @see https://sendgrid.com/en-us
 */
@Service
public class TwillioEmailService implements EmailService {

    private final String ORDER_CONFIRMATION_SUBJECT = "Thank you for placing your order with Daniel's Flower Shop \uD83C\uDF3B";
    private final String twillioApiKey;

    public TwillioEmailService(@Value("${TWILLIO_EMAIL_API_KEY}") String apiKey){
        this.twillioApiKey = apiKey;
    }

    /**
     *
     * @param toEmail
     * @param subject
     * @param message
     * @return
     */
    @Override
    public boolean sendEmail(String toEmail, String subject, String message) {

        //String apiKey = System.getenv("API_KEY");
        String emailFrom = System.getenv("EMAIL_FROM");
        String emailTo = System.getenv("EMAIL_TO");

        /*
         * TODO: Skip sending the confirmation email, but don't
         *  throw an exception because the Email confirmation is optional.
         */
        if(isEmailSendInfoInvalid(twillioApiKey, emailFrom, emailTo)){
            return false;
        }

        Email from = new Email(emailFrom);
        Email to = new Email(emailTo);
        Content content = new Content("text/plain", message);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(this.twillioApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
            return true;

        } catch (IOException e) {
            throw new EmailServiceException("ERROR: unable to send email to: " + toEmail, e);
        }
    }

    @Override
    public boolean sendOrderConfirmationEmail(String toEmail, OrderDetails order) {

        String orderConfirmationMessage = "Thank you for your order on " + LocalDateTime.now() + " for " + order;
        return sendEmail(toEmail, ORDER_CONFIRMATION_SUBJECT, orderConfirmationMessage);
    }

    private boolean isEmailSendInfoInvalid(String apiKey, String emailFrom, String emailTo){

//        if(apiKey == null){
//            throw new EmailServiceException("ERROR: Email service not reachable");
//        } else if(emailFrom == null){
//            throw new EmailServiceException("ERROR: Unable to acquire sender address");
//        } else if(emailTo == null ){
//            throw new EmailServiceException("ERROR: Unable to acquire destination address");
//        }

        return apiKey == null || emailFrom == null || emailTo == null;
    }
}
