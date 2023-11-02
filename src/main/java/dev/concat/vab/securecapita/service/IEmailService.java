package dev.concat.vab.securecapita.service;

/** 
    @author: Bang Vo Anh 
    @since: 11/01/2023 01:20 AM
    @description:
    @update:
            

**/

public interface IEmailService {
    void sendSimpleMailMessage(String name, String to, String token);
    void sendMimeMessageWithAttachments(String name, String to, String token);
    void sendMimeMessageWithEmbeddedImages(String name, String to, String token);
    void sendMimeMessageWithEmbeddedFiles(String name, String to, String token);
    void sendHtmlMail(String name, String to, String token);
    void sendHtmlMailWithEmbeddedFiles(String name, String to, String token);
}
