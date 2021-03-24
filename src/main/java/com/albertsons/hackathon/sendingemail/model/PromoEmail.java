package com.albertsons.hackathon.sendingemail.model;

import java.math.BigInteger;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("promoemail")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PromoEmail {
	
	@PrimaryKeyColumn(name  = "", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private int id;
	
	private String firstname;
	
	private String lastname;
	
	private String email;
	
	@PrimaryKeyColumn(name  = "", ordinal = 0, type = PrimaryKeyType.CLUSTERED)
	private String banner;
	
	private String total_savings;
	
	private String partner_price;
	
	private String value_price;
	
	private BigInteger transaction_number;
	
	private BigInteger upc_id;
	
	private String  item_name;
	
	private String email_status;
}
