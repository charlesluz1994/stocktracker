package com.cluz.stocktracker.service;

import com.cluz.stocktracker.client.response.BrapiStockDataResponse;
import com.cluz.stocktracker.config.SecurityContextData;
import com.cluz.stocktracker.entity.Stock;
import com.cluz.stocktracker.entity.StockPurchase;
import com.cluz.stocktracker.entity.User;
import com.cluz.stocktracker.repository.StockPurchaseRepository;
import com.cluz.stocktracker.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {

	private final StockRepository stockRepository;
	private final StockPurchaseRepository stockPurchaseRepository;
	private final FindStockDetailService findStockDetailService;

	private final CacheManager cacheManager;

	@Transactional
	public Stock saveStock(Stock stock, StockPurchase stockPurchase) {
		String userId = SecurityContextData.getUserData().getUserId();

		Optional<Stock> optStock = stockRepository.findByStockAndUserId(stock.getStock(), userId);
		if (optStock.isPresent()) {
			Stock savedStock = optStock.get();
			return this.savePurchase(savedStock, stockPurchase);
		}

		var savedStockPurchased = stockPurchaseRepository.save(stockPurchase);
		stock.setUser(User.builder().id(userId).build());
		stock.setPurchases(List.of(savedStockPurchased));
		return stockRepository.save(stock);
	}

	public Stock savePurchase(Stock stock, StockPurchase stockPurchase) {
		var savedStockPurchase = stockPurchaseRepository.save(stockPurchase);
		stock.getPurchases().add(savedStockPurchase);
		return stockRepository.save(stock);
	}

	@Transactional
	public Stock addPurchaseStock(String stockId, StockPurchase stockPurchase) {
		return stockRepository.findByIdAndUserId(stockId, SecurityContextData.getUserData().getUserId())
				.map(stock -> savePurchase(stock, stockPurchase))
				.orElseThrow(() -> new IllegalArgumentException("Cannot find this stock."));
	}

	public List<Stock> findAllStocksByUser() {
		var stocks = stockRepository.findByUser(User.builder().id(SecurityContextData.getUserData().getUserId()).build());

		stocks.forEach(stock -> {
			Optional<BrapiStockDataResponse> brapiStockDetail = findStockDetailService.getBrapiStockDetail(stock.getStock());
			stock.setPrice(BigDecimal.valueOf(brapiStockDetail.map(BrapiStockDataResponse::getRegularMarketPrice)
					.orElse(0.0)));
		});
		return stocks;
	}

	public Optional<Stock> findById(String stockId) {
		return stockRepository.findByIdAndUserId(stockId, SecurityContextData.getUserData().getUserId());
	}

	@Transactional
	public void deleteStockById(String stockId) {
		stockRepository.deleteById(stockId);
	}

	@CacheEvict(value = "stocks", key = "#stock")
	public void evictCacheByStock(String stock) {
		log.info("Evicting stock from cache: {}", stock);
	}

	public void refreshStockCache(String cacheName) {
		boolean cacheExists = cacheManager.getCacheNames()
				.stream()
				.anyMatch(name -> name.equals(cacheName));

		if (cacheExists) {
			Cache cache = cacheManager.getCache(cacheName);
			if (cache != null) {
				log.info("Clearing cache '{}'", cacheName);
				cache.clear();
			}
		} else {
			log.warn("Cache '{}' not found in CacheManager", cacheName);
		}
	}
}
