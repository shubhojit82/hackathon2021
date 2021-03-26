package com.albertsons.hackathon.sendingemail.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Consistency;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import com.albertsons.hackathon.sendingemail.entity.PartnerTransactionEntity;
import com.datastax.driver.core.ConsistencyLevel;


@Repository
public interface PartnerTransactionRepository extends CassandraRepository<PartnerTransactionEntity, String> {
	

	@Consistency(value = ConsistencyLevel.LOCAL_ONE)
	@Query(value = "SELECT * FROM instacart_txn WHERE txn_id  = ?0  allow filtering")
	List<PartnerTransactionEntity> findByTransactionNumber(Long transactionId);
	
	
}
