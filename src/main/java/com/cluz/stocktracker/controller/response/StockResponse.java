package com.cluz.stocktracker.controller.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class StockResponse {

	private String id;
	private String stock;
	private long quantity;
	private BigDecimal price;
	private BigDecimal priceTotal;
}