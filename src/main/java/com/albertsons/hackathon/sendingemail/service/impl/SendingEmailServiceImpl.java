package com.albertsons.hackathon.sendingemail.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.albertsons.hackathon.sendingemail.model.MailModel;
import com.albertsons.hackathon.sendingemail.repository.PromoEmailRepository;
import com.albertsons.hackathon.sendingemail.service.SendingEmailService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


@Service
public class SendingEmailServiceImpl implements SendingEmailService {

    private static Logger log = LoggerFactory.getLogger(SendingEmailServiceImpl.class);

    @Autowired
    private JavaMailSender emailSender;
    
    @Autowired
    PromoEmailRepository promoEmailRepository;

    @Autowired
    @Qualifier("emailConfigBean")
    private Configuration emailConfig;

    @Override
    public void sendEmail(MailModel mailModel, String banner) throws MessagingException, IOException, TemplateException {

        Map<String, String> model = new HashMap<String, String>();
        model.put("name", mailModel.getName());
        model.put("location", "USA- Charlotte NC");
        model.put("signature", "https://safeway.com");
        model.put("savings", mailModel.getSavings());
        model.put("transaction", mailModel.getTransaction());
        model.put("itemName", mailModel.getItemName());
        
        model.put("partnerPrice", mailModel.getPartnerPrice());
        model.put("ourPrice", mailModel.getOurPrice());
        model.put("upcId", mailModel.getUpcId());
        
        model.put("content", mailModel.getContent());
        model.put("to", mailModel.getTo());

        /**
         * Add below line if you need to create a token to verification emails and uncomment line:32 in "email.ftl"
         * model.put("token",UUID.randomUUID().toString());
         */

        mailModel.setModel(model);


        log.info("Sending Email to: " + mailModel.getTo());
        log.info("Banner  Name: " + banner);

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        mimeMessageHelper.addInline("logo.png", new ClassPathResource("classpath:/techmagisterLogo.png"));

        Template template = emailConfig.getTemplate(banner.concat(".ftl"));
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailModel.getModel());

        
        mimeMessageHelper.setTo(mailModel.getTo());
        mimeMessageHelper.setText(html, true);
        mimeMessageHelper.setSubject(mailModel.getSubject());
        mimeMessageHelper.setFrom(mailModel.getFrom());


        emailSender.send(message);
        

    }
}
