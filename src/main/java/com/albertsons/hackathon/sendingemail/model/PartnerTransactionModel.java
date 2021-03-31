package com.albertsons.hackathon.sendingemail.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PartnerTransactionModel {
	
	private String banner;
	
	private  String  email_id;
	
	private String firstname;
	
	private String lastname;
	
	private Long household_id;
	
	private Long instacart_order_no;
	
	private LocalDate txn_dt;
	
	private Long txn_id;
	
	private int mail_flag;
	
	private List<UPC> upc;

}
