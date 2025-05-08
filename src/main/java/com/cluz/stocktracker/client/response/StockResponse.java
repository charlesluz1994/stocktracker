package com.cluz.stocktracker.client.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockResponse {
	private String id;
	private String stock;
	private BigDecimal price;
	private Long quantity;
	private BigDecimal priceTotal;
}
