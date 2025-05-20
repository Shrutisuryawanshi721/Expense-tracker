package com.expense.tracker.app.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transactions {
	@Id
	@GeneratedValue
	private Long id;
	private String type;
	private String category;
	private double amount;
	private LocalDate date;

}
