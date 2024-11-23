package tech.solutionhut.ecommerce.notification.client;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import tech.solutionhut.ecommerce.notification.record.Product;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tech.solutionhut.ecommerce.notification.constants.EmailTemplates.ORDER_CONFIRMATION;
import static tech.solutionhut.ecommerce.notification.constants.EmailTemplates.PAYMENT_CONFIRMATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailClient {

    private final JavaMailSender mailSender;

    private final SpringTemplateEngine templateEngine;

    @Value("${smtp.sender.mail}")
    private String senderEmail;

    @Async
    public void sendPaymentEmail(String destination, String customerName, BigDecimal amount, String orderReference) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_RELATED,
                StandardCharsets.UTF_8.name());
        message.setFrom(senderEmail);
        final String templateName = PAYMENT_CONFIRMATION.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("amount", amount);
        variables.put("orderReference", orderReference);

        Context context = new Context();
        context.setVariables(variables);
        helper.setSubject(PAYMENT_CONFIRMATION.getSubject());

        try{
            String htmlTemplate = templateEngine.process(templateName, context);
            helper.setText(htmlTemplate, true);
            helper.setTo(destination);
            mailSender.send(message);
            log.info("Email sent to {} with template {}", destination, templateName);
        } catch (MessagingException e) {
            log.warn("Cannot send email to {} with template {}", destination, templateName, e);
        }

    }

    @Async
    public void sendOrderEmail(String destination, String customerName, BigDecimal amount, String orderReference, List<Product> products) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_RELATED,
                StandardCharsets.UTF_8.name());
        message.setFrom(senderEmail);
        final String templateName = ORDER_CONFIRMATION.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("totalAmount", amount);
        variables.put("orderReference", orderReference);
        variables.put("products", products);

        Context context = new Context();
        context.setVariables(variables);
        helper.setSubject(ORDER_CONFIRMATION.getSubject());

        try{
            String htmlTemplate = templateEngine.process(templateName, context);
            helper.setText(htmlTemplate, true);
            helper.setTo(destination);
            mailSender.send(message);
            log.info("Email sent to {} with template {}", destination, templateName);
        } catch (MessagingException e) {
            log.warn("Cannot send email to {} with template {}", destination, templateName, e);
        }

    }
}
