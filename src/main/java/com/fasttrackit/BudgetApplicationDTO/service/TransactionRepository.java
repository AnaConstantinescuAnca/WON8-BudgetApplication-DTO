package com.fasttrackit.BudgetApplicationDTO.service;

import com.fasttrackit.BudgetApplicationDTO.model.Transaction;
import com.fasttrackit.BudgetApplicationDTO.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


//    @Query("select t from Transaction t where t.product=:name")
//    List<Transaction> findByProductByQuery(@Param("name") String product);

//    //@Query("select t from Transaction t where t.type =:value")
//    List<Transaction> findByType(TransactionType type);

    @Query("select t.type, sum(t.amount) from Transaction t group by t.type")
    Map<TransactionType, Double> groupByType();

    @Query("select t.product, sum(t.amount) from Transaction t group by t.product")
    Map<String, List<Transaction>> groupByProduct();


    @Query("select t from Transaction t where (t.type=:type or :type is null) " +
                                                "and (t.amount>=:minAmount or :minAmount is null)" +
                                                " and (t.amount<=:maxAmount or :maxAmount is null)")
    List<Transaction> getByTypeAndMinAndMax(@Param("type") TransactionType type,
                                            @Param("minAmount") Double minAmount,
                                            @Param("maxAmount") Double maxAmount);


}
