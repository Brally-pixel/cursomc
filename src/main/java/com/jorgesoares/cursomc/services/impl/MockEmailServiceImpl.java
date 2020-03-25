package com.jorgesoares.cursomc.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;


public class MockEmailServiceImpl extends AbstractEmailServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(MockEmailServiceImpl.class);

    @Override
    public void sendEmail(SimpleMailMessage msg) {

        LOG.info("Simulando envio de email...");
        LOG.info(msg.toString());
        LOG.info("Email enviado");

    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        LOG.info("Simulando envio de email HTML...");
        LOG.info(msg.toString());
        LOG.info("Email enviado");
    }
}
