package com.albertsons.hackathon.sendingemail.controller;


import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.albertsons.hackathon.sendingemail.model.MailModel;
import com.albertsons.hackathon.sendingemail.service.PartnerTransactionService;
import com.albertsons.hackathon.sendingemail.service.SendingEmailService;
import com.fasterxml.jackson.core.JsonProcessingException;

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
    PartnerTransactionService partnerTransactionService;
    
    
    @PostMapping(path = "/{transactionId}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> restPostLoanRequest(@PathVariable String transactionId) throws JsonProcessingException {
    	
    	log.info("transactionId******* = " + transactionId);

    	try {
			partnerTransactionService.getPartnerTransaction(Long.valueOf(transactionId));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok(null);
		
    	
    	
    	
    	
		/*
		 * try { sendingEmailService.sendEmail(mailModel, banner); } catch
		 * (MessagingException e) { e.printStackTrace(); // return
		 * ResponseEntity.ok().body(e.getMessage()); } catch (IOException e) {
		 * e.printStackTrace(); //return ResponseEntity.ok().body(e.getMessage()); }
		 * catch (TemplateException e) { e.printStackTrace(); // return
		 * ResponseEntity.ok().body(e.getMessage()); }
		 */
		 
		
    }
    
   
}
