package com.expense.tracker.app.service;

import com.expense.tracker.app.dto.TransactionDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.time.Month;
import java.util.List;

public interface TransactionService {
    void addTransaction(TransactionDTO dto);
    List<TransactionDTO> getMonthlySummary(Month month);
    void loadFromFile(MultipartFile file);
    Resource saveToFile();
    List<TransactionDTO> getAllTransactions();
}
