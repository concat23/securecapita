package dev.concat.vab.securecapita.service.implementation;

import dev.concat.vab.securecapita.service.IEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.io.File;

import static dev.concat.vab.securecapita.utils.EmailUtils.getEmailMessageText;
import static dev.concat.vab.securecapita.utils.EmailUtils.getVerificationUrl;


@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {
    private static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";
    private static final String UTF_8_ENCODING = "UTF-8";
    private static final String EMAIL_TEMPLATE = "email-template";
    private static final String TEXT_HTML_ENCODING = "text/html; charset=UTF-8";
    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Override
    @Async
    public void sendSimpleMailMessage(String name, String to, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            message.setFrom(fromEmail);
            message.setTo(to);
            //message.setText("Hey, this is working hahaha");
            message.setText(getEmailMessageText(name,host,token));
            mailSender.send(message);
        }catch (Exception exc){
            System.out.println(exc.getMessage());
            throw new RuntimeException(exc.getMessage());
        }
    }

    @Override
    @Async
    public void sendMimeMessageWithAttachments(String name, String to, String token) {
        try {
            MimeMessage message = getMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message,true,UTF_8_ENCODING);
            messageHelper.setPriority(1);

            messageHelper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(to);
            //message.setText("Hey, this is working hahaha");
            messageHelper.setText(getEmailMessageText(name,host,token));

            FileSystemResource fort = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/item-empty.png"));
            FileSystemResource dog = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/item-empty.png"));
            FileSystemResource homework = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/item-empty.png"));

            messageHelper.addAttachment(fort.getFilename(), fort);
            messageHelper.addAttachment(dog.getFilename(), dog);
            messageHelper.addAttachment(homework.getFilename(), homework);
            mailSender.send(message);
        }catch (Exception exc){
            System.out.println(exc.getMessage());
            throw new RuntimeException(exc.getMessage());
        }
    }

    private MimeMessage getMimeMessage() {
        return mailSender.createMimeMessage();
    }

    @Override
    @Async
    public void sendMimeMessageWithEmbeddedImages(String name, String to, String token) {
        try {
            MimeMessage message = getMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message,true,UTF_8_ENCODING);
            messageHelper.setPriority(1);

            messageHelper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(to);
            //message.setText("Hey, this is working hahaha");
            messageHelper.setText(getEmailMessageText(name,host,token));

            FileSystemResource fort = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/item-empty.png"));
            FileSystemResource dog = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/item-empty.png"));
            FileSystemResource homework = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/item-empty.png"));

            messageHelper.addInline(getContentId(fort.getFilename()), fort);
            messageHelper.addInline(getContentId(dog.getFilename()), dog);
            messageHelper.addInline(getContentId(homework.getFilename()), homework);
            mailSender.send(message);
        }catch (Exception exc){
            System.out.println(exc.getMessage());
            throw new RuntimeException(exc.getMessage());
        }
    }

    private String getContentId(String fileName) {
        return "<" + fileName + ">";
    }

    @Override
    @Async
    public void sendMimeMessageWithEmbeddedFiles(String name, String to, String token) {

    }

    @Override
    @Async
    public void sendHtmlMail(String name, String to, String token) {
        try{
            MimeMessage message = getMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message,true,UTF_8_ENCODING);
            messageHelper.setPriority(1);

            Context context = new Context();
            context.setVariable("name",name);
            context.setVariable("url",getVerificationUrl(host,token));
            String text = templateEngine.process(EMAIL_TEMPLATE,context);

            messageHelper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(to);
            //message.setText("Hey, this is working hahaha");
            messageHelper.setText(text,true);

            mailSender.send(message);
        }catch(Exception exc){
            System.out.println(exc.getMessage());
            throw new RuntimeException(exc.getMessage());
        }
    }

    @Override
    @Async
    public void sendHtmlMailWithEmbeddedFiles(String name, String to, String token) {
        try{
            Context context = new Context();
            context.setVariable("name",name);
            context.setVariable("url",getVerificationUrl(host,token));
            String text = templateEngine.process(EMAIL_TEMPLATE,context);
            MimeMessage message = getMimeMessage();
            message.setHeader("Content-Type", "text/html; charset=UTF-8");
            MimeMessageHelper messageHelper = new MimeMessageHelper(message,true,UTF_8_ENCODING);
            messageHelper.setPriority(1);

            messageHelper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(to);
            //message.setText("Hey, this is working hahaha");
            messageHelper.setText(text,true);


            MimeMultipart mimeMultipart = new MimeMultipart("related");
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(text, TEXT_HTML_ENCODING);
            mimeMultipart.addBodyPart(bodyPart);

            BodyPart imageBodyPart = new MimeBodyPart();
            // Window
            DataSource dataSource = new FileDataSource(System.getProperty("user.home") + "/Downloads/images/item-empty.png");
            imageBodyPart.setDataHandler(new DataHandler(dataSource));
            imageBodyPart.setHeader("Content-Type", "image/jpeg");
            imageBodyPart.setFileName("image.png"); // Specify the image file name
            mimeMultipart.addBodyPart(imageBodyPart);
            System.out.println("Email sent successfully.");
            log.info("Email sent successfully.");
            message.setContent(mimeMultipart);

            mailSender.send(message);
        }catch(Exception exc){
            System.out.println(exc.getMessage());
            throw new RuntimeException(exc.getMessage());
        }

    }
}
