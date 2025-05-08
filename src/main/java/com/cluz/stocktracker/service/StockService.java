package com.cluz.stocktracker.service;

import com.cluz.stocktracker.client.response.BrapiStockDataResponse;
import com.cluz.stocktracker.entity.Stock;
import com.cluz.stocktracker.entity.StockPurchase;
import com.cluz.stocktracker.repository.StockPurchaseRepository;
import com.cluz.stocktracker.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService {

	private final StockRepository stockRepository;
	private final StockPurchaseRepository stockPurchaseRepository;
	private final FindStockDetailService findStockDetailService;

	public Stock savePurchase(Stock stock, StockPurchase stockPurchase) {
		var savedStockPurchase = stockPurchaseRepository.save(stockPurchase);
		stock.setPurchases(List.of(savedStockPurchase));
		return stockRepository.save(stock);
	}

	public Stock addPurchaseStock(String stockId, StockPurchase stockPurchase) {
		var optStock = stockRepository.findById(stockId);

		return optStock.map(stock -> {
			var savedStockPurchase = stockPurchaseRepository.save(stockPurchase);
			stock.getPurchases().add(savedStockPurchase);
			return stockRepository.save(stock);
		}).orElseThrow(() -> new IllegalArgumentException("Cannot find this stock."));
	}

	public List<Stock> findAllStock() {
		var stocks = stockRepository.findAll();
		stocks.forEach(stock -> {

			Optional<BrapiStockDataResponse> brapiStockDetail = findStockDetailService.getBrapiStockDetail(stock.getStock());
			stock.setPrice(BigDecimal.valueOf(brapiStockDetail.map(BrapiStockDataResponse::getRegularMarketPrice).orElse(0.0)));
		});
		return stocks;
	}
}
