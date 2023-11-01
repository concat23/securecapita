package dev.concat.vab.securecapita.service.implementation;

import dev.concat.vab.securecapita.service.IEmailService;
import dev.concat.vab.securecapita.utils.EmailUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {

    private static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";
    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender javaMailSender;

    @Override
    public void sendSimpleMailMessage(String name, String to, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            message.setFrom(fromEmail);
            message.setTo(to);
            //message.setText("Hey, this is working hahaha");
            message.setText(EmailUtils.getEmailMessageText(name,host,token));
            javaMailSender.send(message);
        }catch (Exception exc){
            System.out.println(exc.getMessage());
            throw new RuntimeException(exc.getMessage());
        }
    }

    @Override
    public void sendMimeMessageWithAttachments(String name, String to, String token) {

    }

    @Override
    public void sendMimeMessageWithEmbeddedImages(String name, String to, String token) {

    }

    @Override
    public void sendMimeMessageWithEmbeddedFiles(String name, String to, String token) {

    }

    @Override
    public void sendHtmlMail(String name, String to, String token) {

    }

    @Override
    public void sendHtmlMailWithEmbeddedFiles(String name, String to, String token) {

    }
}
