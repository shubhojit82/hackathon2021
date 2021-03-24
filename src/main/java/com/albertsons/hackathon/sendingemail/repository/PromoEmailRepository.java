package com.albertsons.hackathon.sendingemail.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import com.albertsons.hackathon.sendingemail.model.PromoEmail;


@Repository
public interface PromoEmailRepository extends CassandraRepository<PromoEmail, String> {
	
	
	@Query(value="SELECT email,firstname,total_savings,item_name,partner_price,transaction_number,upc_id,value_price FROM promoemail WHERE banner=?0 ALLOW  FILTERING")
	@AllowFiltering
	public List<PromoEmail> findByBanner(String banner);
	
	
}
