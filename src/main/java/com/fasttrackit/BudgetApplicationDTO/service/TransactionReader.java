package com.fasttrackit.BudgetApplicationDTO.service;

import com.fasttrackit.BudgetApplicationDTO.model.Transaction;
import com.fasttrackit.BudgetApplicationDTO.model.TransactionType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class TransactionReader {
    @Value("${file.transactions}")
    private String fileTransactionPath;

    public List<Transaction> getTransactions(){
        try {
            return Files.lines(Path.of(fileTransactionPath))
                    .map(this::lineToTransaction)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Transaction lineToTransaction(String line) {
        String [] transactionParts = line.split("\\|");
        return new Transaction(Long.parseLong(transactionParts[0]),transactionParts[1],
                TransactionType.valueOf(transactionParts[2]),Double.parseDouble(transactionParts[3]));

    }
}
