package com.cluz.stocktracker.service;

import com.cluz.stocktracker.client.BrapiDevClient;
import com.cluz.stocktracker.client.response.BrapiStockDataResponse;
import com.cluz.stocktracker.client.response.BrapiStockResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindStockDetailService {

	@Value("${stock.client.brapi.token}")
	private String token;

	private final BrapiDevClient brapiClient;

	@Cacheable(value = "stocks", key = "#stock")
	public Optional<BrapiStockDataResponse> getBrapiStockDetail(String stock) {
		try {
			log.info("Consulting stock information: {} on Brapi", stock);
			BrapiStockResponse brapiStockResponse = brapiClient.getStock(stock, token);
			log.info("Return from Brapi of stock {} : {}", stock, brapiStockResponse.getResults().get(0));
			return Optional.of(brapiStockResponse.getResults().get(0));
		} catch (RedisConnectionFailureException ex) {
			log.warn("Redis is unavailable, skipping cache for stock: {}", stock);
			// fallback without cache
			BrapiStockResponse fallbackResponse = brapiClient.getStock(stock, token);
			return Optional.of(fallbackResponse.getResults().get(0));
		}
	}
}
