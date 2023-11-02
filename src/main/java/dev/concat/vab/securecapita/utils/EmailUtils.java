package dev.concat.vab.securecapita.utils;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
    @author: Bang Vo Anh 
    @since: 11/01/2023 10:00 PM
    @description:
    @update:
            

**/
public class EmailUtils {

        public static String getEmailMessageText(String name, String host, String token) {
           String htmlTemplate =   "- Name: " + name + "\n" +
                                   "- Host: " + host + "\n" +
                                   "- URL Verify: " + getVerificationUrl(host,token) + "\n";
            return htmlTemplate;
        }

    public static String getEmailMessageStringBuilder(String name, String host, String token) {
        StringBuilder htmlTemplate = new StringBuilder();
        htmlTemplate.append("- Name: ").append(name).append("\n");
        htmlTemplate.append("- Host: ").append(host).append("\n");
        htmlTemplate.append("- Verify URL: ").append(getVerificationUrl(host,token)).append("\n");
        return htmlTemplate.toString();
    }

    public static String getVerificationUrl(String host, String token){
        return host + "/api/users?token=" + token;
    }


}
