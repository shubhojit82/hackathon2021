package com.albertsons.hackathon.sendingemail.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Consistency;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.albertsons.hackathon.sendingemail.entity.PartnerTransactionEntity;
import com.albertsons.hackathon.sendingemail.model.PartnerTransactionModel;
import com.datastax.driver.core.ConsistencyLevel;

@Repository
public interface PartnerTransactionRepository extends CrudRepository<PartnerTransactionEntity, String> {

	@Consistency(value = ConsistencyLevel.LOCAL_ONE)
	@Query(value = "SELECT txn_id FROM instacart_txn WHERE banner  = ?0")
	List<PartnerTransactionEntity> findByBanner(String bannerName);

	@Consistency(value = ConsistencyLevel.LOCAL_ONE)
	@Query(value = "SELECT upc_id FROM instacart_txn WHERE txn_id  = ?0  allow filtering")
	List<PartnerTransactionEntity> findByTransactionNumber(Long transactionId);

	@Consistency(value = ConsistencyLevel.LOCAL_ONE)
	@Query(value = "SELECT * FROM instacart_txn WHERE upc_id  = 3100030744 allow filtering")
	List<PartnerTransactionEntity> findByUpc(Long upc);
	
	
	@Consistency(value = ConsistencyLevel.LOCAL_ONE)
	@Query(value = "SELECT * FROM instacart_txn WHERE upc_id  = ?0  and banner = ?1 allow filtering")
	List<PartnerTransactionEntity> findByUPCAndBanner(Long upcId, String  banner);
	
	@Consistency(value = ConsistencyLevel.LOCAL_ONE)
	@Query(value = "SELECT * FROM instacart_txn WHERE txn_id  = ?0  and banner = ?1 allow filtering")
	List<PartnerTransactionEntity> findByTransactionIdAndBanner(Long transactionId, String  banner);

	void save(PartnerTransactionModel product);

}
