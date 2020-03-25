package com.jorgesoares.cursomc.config;

import com.jorgesoares.cursomc.services.DBService;
import com.jorgesoares.cursomc.services.EmailService;
import com.jorgesoares.cursomc.services.impl.MockEmailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

    @Bean
    public boolean instantiateDatabase(DBService dbService) throws ParseException {
            dbService.instantiateTestDatabase();
        return true;
    }
    @Bean
    public EmailService emailService() {
        return new MockEmailServiceImpl();
    }
}
