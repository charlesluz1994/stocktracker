package com.cluz.stocktracker.util;

import com.cluz.stocktracker.client.response.StockResponse;
import com.cluz.stocktracker.controller.request.StockAddPurchaseRequest;
import com.cluz.stocktracker.controller.request.StockRequest;
import com.cluz.stocktracker.controller.response.StockPurchaseResponse;
import com.cluz.stocktracker.entity.Stock;
import com.cluz.stocktracker.entity.StockPurchase;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@UtilityClass
public class TestUtils {

	public static Stock buildStock(){
		return Stock.builder()
				.id("68220ae9fe3d300b585d496f")
				.stock("AMZN")
				.price(BigDecimal.valueOf(10.00))
				.purchases(buildStockPurchaseList())
				.build();
	}

	public static StockRequest buildStockRequest(){
		return StockRequest.builder()
				.stock("AMZN")
				.price(BigDecimal.TEN)
				.date(LocalDate.of(2025, 5,15))
				.quantity(1L)
				.build();
	}

	public static StockResponse buildStockResponse(){
		return StockResponse.builder()
				.id("68220ae9fe3d300b585d496f")
				.stock("AMZN")
				.price(BigDecimal.valueOf(10.00))
				.quantity(2L)
				.priceTotal(BigDecimal.valueOf(20.00))
				.build();
	}

	public static StockPurchase buildStockPurchase(){
		return StockPurchase.builder()
				.id("68220ae9fe3d300b585d496f")
				.price(BigDecimal.valueOf(10.00))
				.purchaseDate(LocalDate.now())
				.quantity(2L)
				.build();
	}

	public static StockAddPurchaseRequest buildStockAddPurchaseRequest(){
		return StockAddPurchaseRequest.builder()
				.stockId("68220ae9fe3d300b585d496f")
				.quantity(1L)
				.date(LocalDate.now())
				.price(BigDecimal.valueOf(10.00))
				.build();
	}

	public static List<StockPurchase> buildStockPurchaseList(){
		var purchase1 = StockPurchase.builder()
				.id("68220ae9fe3d300b585d496f")
				.price(BigDecimal.valueOf(10.00))
				.purchaseDate(LocalDate.now())
				.quantity(2L)
				.build();

		var purchase2 = StockPurchase.builder()
				.id("68220ae9fe3d300b585d496f")
				.price(BigDecimal.valueOf(10.00))
				.purchaseDate(LocalDate.now())
				.quantity(3L)
				.build();

		return List.of(purchase1, purchase2);
	}

	public static StockPurchaseResponse buildStockPurchaseResponse(){
		return StockPurchaseResponse.builder()
				.date(LocalDate.now())
				.quantity(2L)
				.price(BigDecimal.valueOf(10.00))
				.priceTotal(BigDecimal.valueOf(20.00))
				.build();
	}
}