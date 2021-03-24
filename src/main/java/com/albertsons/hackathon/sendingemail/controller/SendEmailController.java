package com.albertsons.hackathon.sendingemail.controller;


import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.albertsons.hackathon.sendingemail.model.MailModel;
import com.albertsons.hackathon.sendingemail.model.PromoEmail;
import com.albertsons.hackathon.sendingemail.repository.PromoEmailRepository;
import com.albertsons.hackathon.sendingemail.service.SendingEmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(
        allowCredentials = "true",
        origins = "*",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT}
)
@RestController
@Slf4j
@RequestMapping("/sendmail")
public class SendEmailController {

    @Autowired
    private SendingEmailService sendingEmailService;
    
    @Autowired
    PromoEmailRepository promoEmailRepository;

    @PostMapping(path = "/{banner}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public MailModel restPostLoanRequest(@RequestBody MailModel mailModel, @PathVariable String banner) throws JsonProcessingException {

    	//promoEmailRepo.saveAll(saveData());
    	List<PromoEmail> promoEmail = promoEmailRepository.findByBanner(banner);
    	ObjectMapper mapper = new ObjectMapper();

    	PromoEmail email = promoEmail.get(0);
    	log.info(promoEmail.toString());
    	String cutomerEmail = email.getEmail();
    	String customerName  = email.getFirstname();
    	String savings = email.getTotal_savings();
    	
    	String partnerPrice = email.getPartner_price();
    	String ourPrice = email.getValue_price();
    	BigInteger itemid = email.getUpc_id();
    	String itemName = email.getItem_name();
    	BigInteger transactionNumber = email.getTransaction_number();
    	
    	log.info(cutomerEmail);
    	log.info(customerName);
    	log.info(savings);
    	mailModel.setTo(cutomerEmail);
    	mailModel.setName(customerName);
    	mailModel.setSavings(savings);
    	mailModel.setPartnerPrice(partnerPrice);
    	mailModel.setOurPrice(ourPrice);
    	mailModel.setUpcId(itemid.toString());
    	mailModel.setItemName(itemName);
    	mailModel.setTransaction(transactionNumber.toString());
    	
        try {
            sendingEmailService.sendEmail(mailModel, banner);
        } catch (MessagingException e) {
            e.printStackTrace();
           // return ResponseEntity.ok().body(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            //return ResponseEntity.ok().body(e.getMessage());
        } catch (TemplateException e) {
            e.printStackTrace();
           // return ResponseEntity.ok().body(e.getMessage());
        }
		return mailModel;
    }
    
   
}
