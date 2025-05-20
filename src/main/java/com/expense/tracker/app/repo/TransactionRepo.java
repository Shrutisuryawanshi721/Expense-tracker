package com.expense.tracker.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expense.tracker.app.model.Transactions;

public interface TransactionRepo extends JpaRepository<Transactions, Long> {

}
