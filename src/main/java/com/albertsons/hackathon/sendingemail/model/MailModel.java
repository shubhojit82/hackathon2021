package com.albertsons.hackathon.sendingemail.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class MailModel {

    private String from;
    
    private String to;
    
    private String name;
    
    private String subject;
    
    private String content;
    
    private String savings;
    
    private String transaction;
    
    private String itemName;
    
    private String partnerPrice;
    
    private String ourPrice;
    
    private String upcId;
    
    private Map<String, String> model;
}
