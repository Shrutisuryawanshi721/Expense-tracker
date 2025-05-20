package com.expense.tracker.app.controller;

import com.expense.tracker.app.dto.TransactionDTO;
import com.expense.tracker.app.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Month;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Add a new transaction 
    @PostMapping("/add")
    public ResponseEntity<String> addTransaction(@RequestBody TransactionDTO dto) {
        transactionService.addTransaction(dto);
        return ResponseEntity.ok("Transaction added successfully");
    }

    
    @GetMapping("/summary/{month}")
    public List<TransactionDTO> getMonthlySummary(@PathVariable String month) {
        Month m = Month.valueOf(month.toUpperCase());
        return transactionService.getMonthlySummary(m);
    }

    // Get all transactions
    @GetMapping("/all")
    public List<TransactionDTO> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

   
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        transactionService.loadFromFile(file);
        return ResponseEntity.ok("File uploaded and transactions loaded successfully");
    }

    
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile() {
        Resource fileResource = transactionService.saveToFile();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResource.getFilename() + "\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(fileResource);
    }
}
