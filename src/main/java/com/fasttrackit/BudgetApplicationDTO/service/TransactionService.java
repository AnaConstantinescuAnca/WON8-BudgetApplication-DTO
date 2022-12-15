package com.fasttrackit.BudgetApplicationDTO.service;

import com.fasttrackit.BudgetApplicationDTO.exception.ResourceNotFoundException;
import com.fasttrackit.BudgetApplicationDTO.model.Transaction;
import com.fasttrackit.BudgetApplicationDTO.model.TransactionType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionReader transactionReader;

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionReader transactionReader, TransactionRepository transactionRepository) {

        this.transactionReader = transactionReader;
        this.transactionRepository = transactionRepository;
        transactionRepository.saveAll(transactionReader.getTransactions());

    }

    //GET /transactions - get all transactions.
    public List<Transaction> getAll(TransactionType type, Double minAmount, Double maxAmount) {
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionsFiltered(TransactionType type, Double minAmount, Double maxAmount) {
        return transactionRepository.getByTypeAndMinAndMax(type, minAmount, maxAmount);
    }


    public Transaction getById(Long id) {
        //return transactionRepository.getReferenceById(id);
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found", id));
    }

    public Transaction add(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction update(long id, Transaction transaction) {
        Transaction transactionToBeUpdated = getById(id);
        transactionToBeUpdated.setType(transaction.getType());
        transactionToBeUpdated.setProduct(transaction.getProduct());
        transactionToBeUpdated.setAmount(transaction.getAmount());
        return transactionRepository.save(transactionToBeUpdated);
    }

    public Transaction patch(long id, String product, Double diffAmount) {
        Transaction transactionToBeUpdated = getById(id);
        transactionToBeUpdated.setProduct(product);
        transactionToBeUpdated.setAmount(transactionToBeUpdated.getAmount() + diffAmount);
        return transactionRepository.save(transactionToBeUpdated);
    }

    public Transaction deleteById(long id) {
        Transaction transactionToBeDeleted = getById(id);
        transactionRepository.deleteById(id);
        return transactionToBeDeleted;
    }


    public Map<TransactionType, List<Transaction>> getTransactionsByType() {
        return transactionReader.getTransactions()
                .stream()
                .collect(Collectors.groupingBy(Transaction::getType));
    }

    public Map<String, List<Transaction>> getTransactionsByProduct() {
        return transactionReader.getTransactions()
                .stream()
                .collect(Collectors.groupingBy(Transaction::getProduct));
    }


}
