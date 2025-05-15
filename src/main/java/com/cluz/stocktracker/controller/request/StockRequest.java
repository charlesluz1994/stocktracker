package com.cluz.stocktracker.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@Schema(description = "Stock request is used to save new Stock")
public class StockRequest {

	@Schema(description = "Stock option code", example = "AMZN", required = true)
	private String stock;

	@Schema(description = "Stock quantity", example = "10", required = true)
	private long quantity;

	@Schema(description = "date of purchase", example = "2024-04-30", required = true)
	private LocalDate date;

	@Schema(description = "Stock price", example = "10.00", required = true)
	private BigDecimal price;

}