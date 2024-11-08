package com.soukaina.book.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import java.util.HashMap;
import java.util.Map;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    // since this method can take time, we don't want to make the user wait for it, so we are going to add the following annotation
    // But, we need to make sure we enable it in the application main class
    @Async
    public void sendEmail(
            String to,
            String username,
            EmailTemplateName emailTemplate,
            String confirmationUrl,
            String activationCode,
            String subject
    ) throws MessagingException {
        String templateName;
        if (emailTemplate == null) { // just extra-checking
            templateName = "confirm-email"; // falling back to the default name
        } else {
//            templateName = emailTemplate.name(); will return the enum name (ACTIVATE ACCOUNT)
            // we want it to return activate_account
            templateName = emailTemplate.getName();
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // this method throws a MessagingException
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED, // the email will use a mixed multipart structure, which is suitable for sending both text and attachments.
                UTF_8.name() //returns the string "UTF-8"
        );
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("confirmationUrl", confirmationUrl);
        properties.put("activation_code", activationCode);

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom("soukainadev2401@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);

        // context is used because we have variables
        // the process function looks in the template folder automatically and it will try to find
        // an activate_account.html template and then it will process it
        String template = templateEngine.process(templateName, context);

        helper.setText(template, true); // the flag is to say if this is html or not

        mailSender.send(mimeMessage);
    }
}
