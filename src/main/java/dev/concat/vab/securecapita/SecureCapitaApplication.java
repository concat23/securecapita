package dev.concat.vab.securecapita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "dev.concat.vab.securecapita")
public class SecureCapitaApplication extends SpringBootServletInitializer {

        @Override
        protected SpringApplicationBuilder configure(
                SpringApplicationBuilder builder) {
            return builder.sources(SecureCapitaApplication.class);
        }

        public static void main(String[]args){
            SpringApplication.run(SecureCapitaApplication.class, args);
        }
}
