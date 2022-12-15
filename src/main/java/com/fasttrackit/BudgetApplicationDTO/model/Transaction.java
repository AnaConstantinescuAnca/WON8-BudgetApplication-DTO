package com.fasttrackit.BudgetApplicationDTO.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@Builder(toBuilder = true)
//@Data    // le cuprinde pe toate cele de sus
@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private String product;
    @Column
    private TransactionType type;
    @Column
    private double amount;

}