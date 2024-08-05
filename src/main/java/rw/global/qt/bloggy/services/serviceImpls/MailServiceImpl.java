package rw.global.qt.bloggy.services.serviceImpls;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import rw.global.qt.bloggy.exceptions.BadRequestException;
import rw.global.qt.bloggy.models.User;
import rw.global.qt.bloggy.utils.Mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MailServiceImpl {


    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String appEmail;


    @Value("${swagger.app_name}")
    private String appName;

    @Value("${client.host}")
    private String clientHost;


    @Value("${admin.email}")
    private String adminEmail;

    @Autowired
    public MailServiceImpl(SpringTemplateEngine templateEngine, JavaMailSender mailSender) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }
    public void sendResetPasswordMail(User user) {
        Mail mail = new Mail(
                appName,
                "Welcome to " + appName + ", You requested to reset your password verify its you to continue",
                user.getUsername() ,
                user.getEmail() ,
                user.getActivationCode(),
                "reset-password"
        );

        sendMail(mail);
    }

    public void sendAccountVerificationEmail(User user) {
        String link =clientHost+"api/v1/auth/verify-account?email=" + user.getEmail() + "&code=" + user.getActivationCode();
        System.out.println(link);
        Mail mail = new Mail(
                appName,
                "Welcome to "+appName+", Verify your email to continue",
                user.getUsername(), user.getEmail(), link, "verify-account");
        sendMail(mail);
    }




    public void sendUserRegistrationEmail(User user) {

        Mail mail = new Mail(

                user.getUsername(), user.getEmail(), "", "user-register");
        sendMail(mail);
    }
    public void sendBankOperationEmail(String bankOperation, double amount, String accountNumber, String accountName,String customerName,String email) {
        Mail mail = new Mail(
                bankOperation,
                String.valueOf(amount),
                customerName,
                email,
                accountNumber,
                accountName,
                "bank-operation",
                LocalDateTime.now().toString()
        );

        sendMail(mail);
    }

    public void sendLogsToAdmin(File file) {
        // Assuming Mail class has been adjusted to include a File field and corresponding setter/getter methods.
        Mail mail = new Mail(
                appName,
                "Logs for the " + appName + " application",
                "Admin DJB",
                adminEmail,
                null, // Assuming this constructor parameter is 'data', and null is acceptable when sending a file.
                "send-logs",
                file
        );
        // Set the file to be attached
        mail.setFile(file); // Ensure your Mail class has this setter method.

        // Send the mail
        sendMail(mail);
    }


    @Async
    public void sendMail(Mail mail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariable("app_name",mail.getAppName());
            context.setVariable("link", mail.getData());
            context.setVariable("name", mail.getFullNames());
            context.setVariable("otherData", mail.getOtherData());
            context.setVariable("subject",mail.getSubject());
            context.setVariable("username",mail.getFullNames());
            context.setVariable("date",mail.getDate());

            String html = templateEngine.process(mail.getTemplate(), context);
            helper.setTo(mail.getToEmail());
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(appEmail);

            // Check if there's a file to attach
            if (mail.getFile() != null && mail.getFile().exists()) {
                helper.addAttachment(mail.getFile().getName(), mail.getFile());
            }

            mailSender.send(message);


        } catch (MessagingException exception) {
            throw new BadRequestException("Failed To Send An Email : z"+  exception.getMessage());
        }
    }

}