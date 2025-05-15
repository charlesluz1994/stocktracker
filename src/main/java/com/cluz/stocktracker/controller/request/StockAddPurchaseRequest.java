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
@Schema(description = "StockAddPurchase request is used to save a new purchase in an existent stock ")
public class StockAddPurchaseRequest {

	@Schema(description = "Stock option id", example = "682542209dc2a5312b9561aa", required = true)
	private String stockId;

	@Schema(description = "Stock quantity", example = "10.00", required = true)
	private long quantity;

	@Schema(description = "Date of purchase", example = "2024-04-30", required = true)
	private LocalDate date;

	@Schema(description = "Option price", example = "10.00", required = true)
	private BigDecimal price;

}