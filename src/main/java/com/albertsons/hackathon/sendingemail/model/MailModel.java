package com.albertsons.hackathon.sendingemail.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class MailModel {

    public MailModel() {
	}

	private String from;
    
    private String to;
    
    private String name;
    
    private String subject;
    
    private String content;
    
    private String savings;
    
    private Long transaction;
    
    private String itemName;
    
    private String partnerPrice;
    
    private String ourPrice;
    
    private String upcId;
    
    private  String banner;
    
    private int orderId;
    
    private LocalDate orderDate;
    
    private  List<UPC>  upcs;
    
    private Map<String, String> model;
}
