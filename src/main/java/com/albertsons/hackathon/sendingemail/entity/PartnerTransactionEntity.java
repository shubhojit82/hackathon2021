package com.albertsons.hackathon.sendingemail.entity;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(PartnerTransactionEntity.TABLE)
public class PartnerTransactionEntity implements Serializable {

	private static final long serialVersionUID = -3569825165460714750L;
	
	public static final String ISO_OFFSET_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSS[xxx]";
	
	public static final String TABLE = "instacart_txn";

	@PrimaryKeyColumn(name = "txn_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private Long transactionId;

	@PrimaryKeyColumn(name = "upc_id", ordinal = 0, type = PrimaryKeyType.CLUSTERED)
	private Long upcId;

	@Column(value = "banner")
	private String bannerName;

	@Column(value = "email_id")
	private String emailId;

	@Column(value = "first_name")
	private String firstname;

	@Column(value = "last_name")
	private String lastname;

	@Column(value = "household_id")
	private Long household_id;

	@Column(value = "instacart_order_no")
	private Long instacartOrderNumber;

	@Column(value = "item_description")
	private String itemDescription;

	@Column(value = "loyal_cust_wud_hv_paid")
	private Double loyaltyCustomerPrice;

	@Column(value = "net_amount_paid")
	private Double netAmountPaid;

	@Column(value = "purchased_qty")
	private int purchasedQuantity;

	@Column(value = "txn_dt")
	private LocalDate transactionDate;
	
	@Column(value = "mail_flag")
	private int mailFlag;

}
