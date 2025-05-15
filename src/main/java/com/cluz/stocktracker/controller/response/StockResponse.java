package com.cluz.stocktracker.controller.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
public class StockResponse {

	private long quantity;
	private LocalDate date;
	private BigDecimal price;

}