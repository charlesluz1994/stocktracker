package com.cluz.stocktracker.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
public class StockPurchaseResponse {

	private LocalDate date;
	private long quantity;
	private BigDecimal price;
	private BigDecimal priceTotal;
}
