package com.jorgesoares.cursomc.config;

import com.jorgesoares.cursomc.services.DBService;
import com.jorgesoares.cursomc.services.EmailService;
import com.jorgesoares.cursomc.services.impl.SmtpEmailServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("dev")
public class DevConfig {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public boolean instantiateDatabase(DBService dbService) throws ParseException {

        if (!"create".equals(strategy)){
            return false;
        }
            dbService.instantiateTestDatabase();
        return true;
    }
    @Bean
    public EmailService emailService(){
        return new SmtpEmailServiceImpl();
    }

}
