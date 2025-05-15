package com.cluz.stocktracker.mapper;

import com.cluz.stocktracker.entity.Stock;
import com.cluz.stocktracker.entity.StockPurchase;
import com.cluz.stocktracker.util.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

import static org.junit.jupiter.api.Assertions.*;

class StockMapperTest {

	@Test
	@DisplayName("Should convert StockRequest to Stock and StockPurchase")
	void shouldConvertStockRequestToStockAndStockPurchase() {
		// Given
		var stockRequest = TestUtils.buildStockRequest();

		//When
		Pair<Stock, StockPurchase> stockPair = StockMapper.toStock(stockRequest);

		// Then
		assertNotNull(stockPair);
		assertEquals(stockRequest.getStock(), stockPair.getFirst().getStock());
		assertEquals(stockRequest.getPrice(), stockPair.getSecond().getPrice());
		assertEquals(stockRequest.getDate(), stockPair.getSecond().getPurchaseDate());
		assertEquals(stockPair.getFirst().getId(), stockPair.getSecond().getId());
	}

	@Test
	@DisplayName("Should convert StockAddPurchaseRequest to StockPurchase")
	void shouldConvertStockAddPurchaseRequestToStockPurchase() {

		// Given
		var stockAddPurchaseRequest = TestUtils.buildStockAddPurchaseRequest();

		//When
		var stockPurchase = StockMapper.toStockPurchase(stockAddPurchaseRequest);

		// Then
		assertNotNull(stockPurchase);
		assertEquals(stockAddPurchaseRequest.getStockId(), stockPurchase.getId());
		assertEquals(stockAddPurchaseRequest.getPrice(), stockPurchase.getPrice());
		assertEquals(stockAddPurchaseRequest.getDate(), stockPurchase.getPurchaseDate());
		assertEquals(stockAddPurchaseRequest.getQuantity(), stockPurchase.getQuantity());

	}

	@Test
	@DisplayName("Should convert Stock to StockResponse")
	void shouldConvertStockToStockResponse() {

		// Given
		var stock = TestUtils.buildStock();

		//When
		var stockResponse= StockMapper.toStockResponse(stock);

		// Then
		assertNotNull(stockResponse);
		assertEquals(stock.getStock(), stockResponse.getStock());
		assertEquals(stock.getPrice(), stockResponse.getPrice());
		assertEquals(stock.getId(), stockResponse.getId());
		assertEquals(stock.getPurchases().size(), 2);
	}

	@Test
	@DisplayName("Should convert StockPurchase to StockDetailResponse")
	void shouldConvertStockPurchaseToStockDetailResponse() {
		// Given
		var stockPurchase = TestUtils.buildStockPurchase();

		//When
		var StockDetailResponse= StockMapper.toStockDetailResponse(stockPurchase);

		// Then
		assertNotNull(StockDetailResponse);
		assertEquals(stockPurchase.getPurchaseDate(), StockDetailResponse.getDate());
		assertEquals(stockPurchase.getPrice(), StockDetailResponse.getPrice());
		assertEquals(stockPurchase.getQuantity(), StockDetailResponse.getQuantity());
	}
}