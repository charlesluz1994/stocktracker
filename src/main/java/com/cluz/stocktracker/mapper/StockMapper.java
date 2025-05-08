package com.cluz.stocktracker.mapper;

import com.cluz.stocktracker.controller.request.StockAddPurchaseRequest;
import com.cluz.stocktracker.controller.request.StockRequest;
import com.cluz.stocktracker.entity.Stock;
import com.cluz.stocktracker.entity.StockPurchase;
import lombok.experimental.UtilityClass;
import org.springframework.data.util.Pair;

@UtilityClass
public class StockMapper {

	public static Pair<Stock, StockPurchase> toStock(StockRequest request) {

		final var stock = Stock.builder()
				.stock(request.getStock())
				.build();

		final var stockPurchase = StockPurchase.builder()
				.purchaseDate(request.getDate())
				.quantity(request.getQuantity())
				.price(request.getPrice())
				.build();

		return Pair.of(stock, stockPurchase);
	}

	public static StockPurchase toStockPurchase(StockAddPurchaseRequest request) {
		return StockPurchase.builder()
				.purchaseDate(request.getDate())
				.price(request.getPrice())
				.quantity(request.getQuantity())
				.build();
	}
}
