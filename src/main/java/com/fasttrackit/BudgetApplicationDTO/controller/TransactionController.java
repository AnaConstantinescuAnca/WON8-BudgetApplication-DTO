package com.fasttrackit.BudgetApplicationDTO.controller;

import com.fasttrackit.BudgetApplicationDTO.controller.dto.DeleteTransactionResponse;
import com.fasttrackit.BudgetApplicationDTO.controller.dto.PatchTransactionRequest;
import com.fasttrackit.BudgetApplicationDTO.model.Transaction;
import com.fasttrackit.BudgetApplicationDTO.model.TransactionType;
import com.fasttrackit.BudgetApplicationDTO.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("transactions")  //http://host:port/transactions
public class TransactionController {
    private final TransactionService transactionService;

    //GET /transactions - get all transactions.
    // Make it filterable by type, minAmount, maxAmount
    // (you will have 6 filtering methods in repository: byType, byMinAmount, byMaxAmout,
    // byTypeAndMin, byTypeAndMax, byMinAndMax, byTypeAndMinAndMax)

    @GetMapping()    //http://host:port/transactions
    public List<Transaction> getAll(@RequestParam(required = false) TransactionType type,
                                    @RequestParam(required = false) Double minAmount,
                                    @RequestParam(required = false) Double maxAmount) {

        return transactionService.getTransactionsFiltered(type, minAmount, maxAmount);
    }

//aici expunem doar 2 fielduri id si product
//    @GetMapping()    //http://host:port/transactions
//    public List<TransactionOverviewDTO> getAll(@RequestParam(required = false) TransactionType type,
//                                    @RequestParam(required = false) Double minAmount,
//                                    @RequestParam(required = false) Double maxAmount) {
//
//        return transactionService.getTransactionsFiltered(type, minAmount, maxAmount)
//                .stream()
//                .map(transaction -> new TransactionOverviewDTO(transaction.getId(), transaction.getProduct()))
//                .toList();
//    }

    //GET /transactions/{id} - get transaction with id
    @GetMapping("{id}")  //GET  http://host:port/transactions/3
    public Transaction getById(@PathVariable long id) {
        return transactionService.getById(id);
    }

    //POST /transactions - adds a new transaction
    @PostMapping
    public Transaction add(@RequestBody Transaction transaction) {
        return transactionService.add(transaction);
    }

    //PUT  /transactions/{id} - replaces the transaction with id
    @PutMapping("{id}")
    public Transaction update(@PathVariable long id, @RequestBody Transaction transaction) {
        return transactionService.update(id, transaction);
    }

    //DELETE /transactions/{id} - deletes the transaction with id
//    @DeleteMapping("{id}")
//    public Transaction delete(@PathVariable long id) {
//        return transactionService.deleteById(id);
//    }

//    //ex cand vrem sa intoarcem un mesaj ca am sters inregistrarea
    @DeleteMapping("{id}")
    public DeleteTransactionResponse delete(@PathVariable long id) {
        transactionService.deleteById(id);
        return new DeleteTransactionResponse(true, "Transaction was deleted");
    }


    //PATCH /transactions/{id} - supports changing the product and the amount
    @PatchMapping("{id}")
    public Transaction patch(@PathVariable long id, @RequestBody PatchTransactionRequest request){
        return transactionService.patch(id, request.product(), request.diffAmount());
    }


    //   GET /transactions/reports/type -> returns a map from type to sum of amount -
    //   the processing is done in memory, not in the database. you can try making another implementation with calculations in db
    @GetMapping("reports/type")
    public Map<TransactionType, List<Transaction>> reportByType() {
        return transactionService.getTransactionsByType();
    }

    //   GET /transactions/reports/product -> returns a map from product to sum of amount
    @GetMapping("reports/product")
    public Map<String, List<Transaction>> reportByProduct() {
        return transactionService.getTransactionsByProduct();
    }


//    @GetMapping("type") // http://host:port/transactions/type?val=SELL
//    public List<Transaction> getType(@RequestParam(required = false) String val) {
//
//        return transactionService.getByType(val);
//    }
//
//    @GetMapping("minAmount/{value}") // http://localhost:8080/transaction/minAmount/100
//    public List<Transaction> getAllByMinAmount(@PathVariable Double value) {
//        return transactionService.getByMinAmount(value);
//    }

}
