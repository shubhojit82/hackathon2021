package com.albertsons.hackathon.sendingemail.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.albertsons.hackathon.sendingemail.model.UPC;
import com.albertsons.hackathon.sendingemail.service.PartnerTransactionService;
import com.albertsons.hackathon.sendingemail.service.SendingEmailService;
import com.fasterxml.jackson.core.JsonProcessingException;

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
    
    
    @PostMapping(path = "/{banner}", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> restPostLoanRequest(@PathVariable String banner) throws JsonProcessingException {
    	
    	partnerTransactionService.getPartnerTransaction(banner);
    	
		return ResponseEntity.ok(null);
    }
   
}
