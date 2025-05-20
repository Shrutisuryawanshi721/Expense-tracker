package com.expense.tracker.app.service.impl;

import java.io.File;
import java.time.Month;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.expense.tracker.app.util.FileUtil;
import com.expense.tracker.app.dto.TransactionDTO;
import com.expense.tracker.app.model.Transactions;
import com.expense.tracker.app.repo.TransactionRepo;
import com.expense.tracker.app.service.TransactionService;

@Service
public class TransactionServiceimpl implements TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;

    @Override
    public void addTransaction(TransactionDTO dto) {
        Transactions transaction = mapToEntity(dto);
        transactionRepo.save(transaction);
    }

    @Override
    public List<TransactionDTO> getMonthlySummary(Month month) {
        List<Transactions> all = transactionRepo.findAll();
        return all.stream()
                .filter(t -> t.getDate().getMonth() == month)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> getAllTransactions() {
        return transactionRepo.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void loadFromFile(MultipartFile file) {
        List<Transactions> transactions = FileUtil.readFromMultipartFile(file);
        transactionRepo.saveAll(transactions);
    }

    @Override
    public Resource saveToFile() {
        String fileName = "transactions_" + UUID.randomUUID() + ".csv";
        File file = FileUtil.writeToCsv(
                transactionRepo.findAll(),
                System.getProperty("java.io.tmpdir") + File.separator + fileName
        );
        return new FileSystemResource(file);
    }

    private Transactions mapToEntity(TransactionDTO dto) {
        Transactions t = new Transactions();
        t.setId(dto.getId());
        t.setType(dto.getType());
        t.setCategory(dto.getCategory());
        t.setAmount(dto.getAmount());
        t.setDate(dto.getDate());
        return t;
    }

    private TransactionDTO mapToDTO(Transactions t) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(t.getId());
        dto.setType(t.getType());
        dto.setCategory(t.getCategory());
        dto.setAmount(t.getAmount());
        dto.setDate(t.getDate());
        return dto;
    }
}
