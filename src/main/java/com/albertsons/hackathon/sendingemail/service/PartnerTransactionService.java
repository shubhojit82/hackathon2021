package com.albertsons.hackathon.sendingemail.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.albertsons.hackathon.sendingemail.entity.PartnerTransactionEntity;
import com.albertsons.hackathon.sendingemail.model.MailModel;
import com.albertsons.hackathon.sendingemail.model.UPC;
import com.albertsons.hackathon.sendingemail.repository.PartnerTransactionRepository;

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
	
	
	public void getPartnerTransaction(String banner) {
		log.info("banner Name = "+ banner);
		partnerTransactionMapper(banner);
	}

	private void partnerTransactionMapper(String banner) {
		List<Long> transId = getTransactionIds(banner);
		transId.stream().forEach(transaction -> {
			log.info("Transaction  number = " + transaction);
			transactionDetails(transaction,banner);
		});
	}
	
	/**
	 * This method returns all the distinct transaction id.
	 * @param banner
	 * @return
	 */
	private List<Long> getTransactionIds(String banner) {
		
		List<PartnerTransactionEntity> partnerTransactionEntities = partnerTransactionRepository.findByBanner(banner.toUpperCase());
		List<Long> tansactionIds = new ArrayList<Long>();
		partnerTransactionEntities.forEach(transId -> {
			tansactionIds.add(transId.getTransactionId());
		});
		
		return tansactionIds.stream().distinct().collect(Collectors.toList());
	}
	
	/*
	 * This method update the model with list of UPC
	 */
	private void transactionDetails(Long transId, String banner)  {
		
		List<PartnerTransactionEntity> partnerTransactionEntities = partnerTransactionRepository
				.findByTransactionIdAndBanner(transId.longValue(), banner.toUpperCase());
		
		boolean  emaill_already_sent = false;
		for (int j=0; j<partnerTransactionEntities.size(); j++) {
			int mailFlag = partnerTransactionEntities.listIterator().next().getMailFlag();
			if(mailFlag == 1)
				emaill_already_sent = true;
		}
		
		if(!emaill_already_sent) {
			List<UPC> upclist = getUPCList(partnerTransactionEntities);
			Double totalSavings =  0.00;
			Double totalPartnerPrice = 0.00;
			for(int i=0;i<upclist.size();i++) {
			    totalSavings += upclist.get(i).getSavings();
			    totalPartnerPrice += upclist.get(i).getNet_amount_paid();
				mailModel.setSavings(totalSavings);   
				mailModel.setTotal_partner_price(totalPartnerPrice);
			}
			
			
			partnerTransactionEntities.stream().forEach(entity -> {
				mailModel.setTo(entity.getEmailId());
				mailModel.setBanner(entity.getBannerName());
				mailModel.setOrderDate(entity.getTransactionDate());
				mailModel.setOrderId(entity.getInstacartOrderNumber());
				mailModel.setName(entity.getFirstname());
				mailModel.setTransaction(entity.getTransactionId());
			});
			
			log.info("************mailModel**********  ="  + mailModel.toString());
			
			try {
				sendingEmailService.sendEmail(mailModel);
				//Update table to set email sent flag to 1
			} catch (MessagingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TemplateException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private List<UPC> getUPCList(List<PartnerTransactionEntity> partnerTransactionEntities) {

		List<UPC> upcs = new ArrayList<UPC>();
		partnerTransactionEntities.stream().forEach(entities -> {
			upcs.add(UPC.builder()
					.upc_id(entities.getUpcId())
					.loyal_cust_wud_hv_paid(entities.getLoyaltyCustomerPrice())
					.net_amount_paid(entities.getNetAmountPaid())
					.purchased_qty(entities.getPurchasedQuantity())
					.savings(entities.getNetAmountPaid() - entities.getLoyaltyCustomerPrice())
					.item_description(entities.getItemDescription()).build());
		});
		
		log.info("upcs >>>>>>>> " + upcs );
		mailModel.setUpcs(upcs);
		return upcs;
	}

}