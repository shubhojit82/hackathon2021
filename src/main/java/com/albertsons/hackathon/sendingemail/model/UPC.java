package com.albertsons.hackathon.sendingemail.model;

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
public class UPC {

	private Long upc_id;
	
	private int purchased_qty;
	
	private Double net_amount_paid;
	
	private Double loyal_cust_wud_hv_paid;
	
	private Double savings;
	
	private String item_description;
	
}
