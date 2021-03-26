package com.albertsons.hackathon.sendingemail.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.albertsons.hackathon.sendingemail.entity.PartnerTransactionEntity;
import com.albertsons.hackathon.sendingemail.model.MailModel;
import com.albertsons.hackathon.sendingemail.model.PartnerTransactionModel;
import com.albertsons.hackathon.sendingemail.model.UPC;
import com.albertsons.hackathon.sendingemail.repository.PartnerTransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PartnerTransactionService {

	@Autowired
	PartnerTransactionRepository partnerTransactionRepository;
	
	@Autowired
    private SendingEmailService sendingEmailService;
	
	MailModel mailModel = new MailModel();

	public void getPartnerTransaction(Long transactionId) throws MessagingException, IOException, TemplateException {

		partnerTransactionMapper(transactionId);
		
	}

	private void partnerTransactionMapper(Long transactionId) throws MessagingException, IOException, TemplateException  {

		List<PartnerTransactionEntity> partnerTransactionEntities = partnerTransactionRepository
				.findByTransactionNumber(transactionId);
		List<PartnerTransactionModel> partnerTransactionDTos = new ArrayList<PartnerTransactionModel>();
		partnerTransactionEntities.forEach(entity -> {
			partnerTransactionDTos
					.add(new PartnerTransactionModel(entity.getBannerName(), entity.getEmailId(), entity.getFirstname(),
							entity.getLastname(), entity.getHousehold_id(), entity.getInstacartOrderNumber(),
							entity.getTransactionDate(), entity.getTransactionId(), getUPC(entity)));
		});
		
		partnerTransactionDTos.forEach(partnerTransactionDetail -> {
            mailModel.setTo(partnerTransactionDetail.getEmail_id());
            mailModel.setBanner(partnerTransactionDetail.getBanner());
            mailModel.setName(partnerTransactionDetail.getFirstname().concat(" ").concat(partnerTransactionDetail.getLastname()));
            mailModel.setTransaction(partnerTransactionDetail.getTxn_id());
            mailModel.setOrderId(partnerTransactionDetail.getInstacart_order_no());
            mailModel.setOrderDate(partnerTransactionDetail.getTxn_dt());
            mailModel.setUpcs(getUPCList(partnerTransactionDetail));
        });
		
		sendingEmailService.sendEmail(mailModel);
	}
	
	private List<UPC> getUPC(PartnerTransactionEntity entity) {
		List<UPC> upcs = new ArrayList<UPC>();
		upcs.add(UPC.builder().upc_id(entity.getUpcId()).purchased_qty(entity.getPurchasedQuantity())
				.net_amount_paid(entity.getNetAmountPaid()).loyal_cust_wud_hv_paid(entity.getLoyaltyCustomerPrice())
				.item_description(entity.getItemDescription()).build());
		return upcs;
	}
	
	private List<UPC> getUPCList(PartnerTransactionModel partnerTransactionDetail)  {
		List<UPC> upcs = new ArrayList<UPC>();
		
		partnerTransactionDetail.getUpc().forEach(upc ->  {
			upcs.add(UPC.builder()
					.item_description(upc.getItem_description())
					.loyal_cust_wud_hv_paid(upc.getLoyal_cust_wud_hv_paid())
					.upc_id(upc.getUpc_id())
					.net_amount_paid(upc.getNet_amount_paid())
					.purchased_qty(upc.getPurchased_qty())
					.build());
		});
		
		
		return upcs;
	}

}