package com.flowershop.service;

import com.flowershop.model.viewmodel.OrderDetails;

/**
 * Interface for an email service to send confirmation emails
 */
public interface EmailService {

    boolean sendEmail(String toEmail, String subject, String message);

    boolean sendOrderConfirmationEmail(String toEmail, OrderDetails order);
}
