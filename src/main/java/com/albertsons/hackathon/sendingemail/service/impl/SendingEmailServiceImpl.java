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
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.albertsons.hackathon.sendingemail.model.MailModel;
import com.albertsons.hackathon.sendingemail.model.PartnerTransactionModel;
import com.albertsons.hackathon.sendingemail.repository.PartnerTransactionRepository;
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
    PartnerTransactionRepository partnerTransactionRepository;

    @Autowired
    @Qualifier("emailConfigBean")
    private Configuration emailConfig;
    
    @Autowired
    private CassandraOperations cassandraOperations;

    @Override
    public void sendEmail(MailModel mailModel) throws MessagingException, IOException, TemplateException {

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", mailModel.getName());
        model.put("location", "USA- Charlotte NC");
        model.put("signature", "https://safeway.com");
        model.put("to", mailModel.getTo());
        model.put("orderId", String.valueOf(mailModel.getOrderId()));
        model.put("orderDate", String.valueOf(mailModel.getOrderDate()));
        model.put("subject", "We found you a way to Save $$$");
        
        model.put("totalSavings", mailModel.getSavings());
        StringBuilder buf = new StringBuilder();
        buf.append(
                   "<table border=\"1\">" +
                   "<tr>" +
                   "<th>ITEM#</th>" +
                   "<th>ITEM DESCRIPTION</th>" +
                   "<th>PARTNER PRICE</th>" +
                   "<th>LOYALTY PRICE</th>" +
                   "<th>SAVINGS</th>" +
                   "</tr>");
        for (int i = 0; i < mailModel.getUpcs().size(); i++) {
            buf.append("<tr><td>")
               .append(mailModel.getUpcs().get(i).getUpc_id())
               .append("</td><td>")
               .append(mailModel.getUpcs().get(i).getItem_description())
               .append("</td><td><span  style=\" color: red;\"> $<b>")
               .append(mailModel.getUpcs().get(i).getNet_amount_paid())
               .append("</b> </span></td><td><span  style=\" color: blue;\"> $<b>")
               .append(mailModel.getUpcs().get(i).getLoyal_cust_wud_hv_paid())
               .append("</b> </span></td><td><span  style=\" color: green;\"> $<b>")
               .append(mailModel.getUpcs().get(i).getNet_amount_paid() - mailModel.getUpcs().get(i).getLoyal_cust_wud_hv_paid())
               .append("</b> </span></td></tr>");
        }
        buf.append("</table>");
        
        model.put("UPCTable", buf);
        
        
        mailModel.setModel(model);


        log.info("Sending Email to: " + mailModel.getTo());
        log.info("Banner  Name: " + mailModel.getBanner());

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Template template = emailConfig.getTemplate( mailModel.getBanner().concat(".ftl"));
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailModel.getModel());

        
        mimeMessageHelper.setTo(mailModel.getTo());
        mimeMessageHelper.setText(html, true);
        mimeMessageHelper.setSubject("We found you a way to Save $$$");
        if("VONS".equalsIgnoreCase(mailModel.getBanner())) {
        	mimeMessageHelper.setFrom("no-reply@status.vons.com");
        } else  {
        	mimeMessageHelper.setFrom("no-reply@status.safeway.com");
        }
        
        emailSender.send(message);
        
        log.info("Email sent successfully to : " + mailModel.getTo());
       
    }
    
    @Override
    public PartnerTransactionModel saveOrUpdate(PartnerTransactionModel product) {
    	partnerTransactionRepository.save(product);
        return product;
    }
}
