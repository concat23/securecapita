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
        String htmlTemplate = "<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "   body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; }\n" +
                "   .container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #fff; border-radius: 5px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }\n" +
                "   .header { background-color: #007BFF; color: #fff; padding: 20px; text-align: center; border-top-left-radius: 5px; border-top-right-radius: 5px; }\n" +
                "   .host { padding: 20px; color: #333; }\n" +
                "   .footer { background-color: #f0f0f0; padding: 10px; text-align: center; border-bottom-left-radius: 5px; border-bottom-right-radius: 5px; }\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"container\">\n" +
                "   <div class=\"header\">\n" +
                "       <h1>Welcome, " + name + "!</h1>\n" +
                "   </div>\n" +
                "   <div class=\"host\">\n" +
                "       <p>" + host + "</p>\n" +
                "   </div>\n" +
                "   <div class=\"footer\">\n" +
                "       <p>This is an automated email, please do not reply.</p>\n" +
                "   </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

        return htmlTemplate;
    }

    public static String getEmailMessageStringBuilder(String name, String host, String token) {
        StringBuilder htmlTemplate = new StringBuilder();
        htmlTemplate.append("<html>\n");
        htmlTemplate.append("<head>\n");
        htmlTemplate.append("<style>\n");
        htmlTemplate.append("   body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; }\n");
        htmlTemplate.append("   .container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #fff; border-radius: 5px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }\n");
        htmlTemplate.append("   .header { background-color: #007BFF; color: #fff; padding: 20px; text-align: center; border-top-left-radius: 5px; border-top-right-radius: 5px; }\n");
        htmlTemplate.append("   .host { padding: 20px; color: #333; }\n");
        htmlTemplate.append("   .footer { background-color: #f0f0f0; padding: 10px; text-align: center; border-bottom-left-radius: 5px; border-bottom-right-radius: 5px; }\n");
        htmlTemplate.append("</style>\n");
        htmlTemplate.append("</head>\n");
        htmlTemplate.append("<body>\n");
        htmlTemplate.append("<div class=\"container\">\n");
        htmlTemplate.append("   <div class=\"header\">\n");
        htmlTemplate.append("       <h1>Welcome, ").append(name).append("!</h1>\n");
        htmlTemplate.append("   </div>\n");
        htmlTemplate.append("   <div class=\"host\">\n");
        htmlTemplate.append("       <p>").append(host).append("</p>\n");
        htmlTemplate.append("   </div>\n");
        htmlTemplate.append("   <div class=\"footer\">\n");
        htmlTemplate.append("       <p>This is an automated email, please do not reply.</p>\n");
        htmlTemplate.append("   </div>\n");
        htmlTemplate.append("</div>\n");
        htmlTemplate.append("</body>\n");
        htmlTemplate.append("</html>");

        return htmlTemplate.toString();
    }

    public static String getEmailMessageHtml(String name, String host, String token) {
        TemplateEngine templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/mail/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("host", host);
        context.setVariable("verificationUrl", getVerificationUrl(host,token));
        return templateEngine.process("email-template", context);
    }


    public static String getVerificationUrl(String host, String token){
        return host + "/api/users?token=" + token;
    }

}
