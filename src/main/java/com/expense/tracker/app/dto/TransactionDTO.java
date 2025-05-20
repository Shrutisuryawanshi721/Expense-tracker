package com.expense.tracker.app.dto;

import java.time.LocalDate;

import lombok.Data;
@Data
public class TransactionDTO {
	private Long id;
	private String type;
	private String category;
	private double amount;
	private LocalDate date;
}
