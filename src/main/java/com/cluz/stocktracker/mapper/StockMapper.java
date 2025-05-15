package com.cluz.stocktracker.mapper;

import com.cluz.stocktracker.controller.response.StockPurchaseResponse;
import com.cluz.stocktracker.client.response.StockResponse;
import com.cluz.stocktracker.controller.request.StockAddPurchaseRequest;
import com.cluz.stocktracker.controller.request.StockRequest;
import com.cluz.stocktracker.entity.Stock;
import com.cluz.stocktracker.entity.StockPurchase;
import lombok.experimental.UtilityClass;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;
import java.util.Objects;

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
				.id(request.getStockId())
				.purchaseDate(request.getDate())
				.price(request.getPrice())
				.quantity(request.getQuantity())
				.build();
	}

	public static StockResponse toStockResponse(Stock stock) {
		final Long quantity = stock.getPurchases().stream().map(StockPurchase::getQuantity).reduce(0L,Long::sum);

		BigDecimal price = stock.getPurchases().get(0).getPrice();

		return StockResponse.builder()
				.id(stock.getId())
				.stock(stock.getStock())
				.price(price)
				.quantity(quantity)
				.priceTotal(Objects.nonNull(price) ? price.multiply(BigDecimal.valueOf(quantity)):BigDecimal.ZERO)
				.build();
	}

	public static StockPurchaseResponse toStockDetailResponse(StockPurchase stockPurchase){
		return StockPurchaseResponse.builder()
				.date(stockPurchase.getPurchaseDate())
				.quantity(stockPurchase.getQuantity())
				.price(stockPurchase.getPrice())
				.priceTotal(stockPurchase.getPrice().multiply(BigDecimal.valueOf(stockPurchase.getQuantity())))
				.build();
	}
}
