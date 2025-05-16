package com.cluz.stocktracker.controller;

import com.cluz.stocktracker.client.response.StockResponse;
import com.cluz.stocktracker.controller.request.StockAddPurchaseRequest;
import com.cluz.stocktracker.controller.request.StockRequest;
import com.cluz.stocktracker.controller.response.StockPurchaseResponse;
import com.cluz.stocktracker.entity.Stock;
import com.cluz.stocktracker.entity.StockPurchase;
import com.cluz.stocktracker.mapper.StockMapper;
import com.cluz.stocktracker.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {
	private final StockService stockService;

	@Operation(summary = "Save a new stock", security = @SecurityRequirement(name = "bearerAuth"))
	@PostMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<StockResponse> saveStock(@RequestBody StockRequest request) {
		Pair<Stock, StockPurchase> stock = StockMapper.toStock(request);
		var savedStock = stockService.saveStock(stock.getFirst(), stock.getSecond());

		return ResponseEntity.status(HttpStatus.CREATED).body(StockMapper.toStockResponse(savedStock));
	}

	@Operation(summary = "Add new purchase to stock already existent", security = @SecurityRequirement(name = "bearerAuth"))
	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<StockResponse> addPurchaseStock(@RequestBody StockAddPurchaseRequest request) {
		try {
			var stockPurchase = StockMapper.toStockPurchase(request);
			var savedPurchase = stockService.addPurchaseStock(request.getStockId(), stockPurchase);

			return ResponseEntity.ok(StockMapper.toStockResponse(savedPurchase));
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.unprocessableEntity().build();
		}
	}

	@Operation(summary = "Get stocks by user", security = @SecurityRequirement(name = "bearerAuth"))
	@GetMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<StockResponse>> getStocks() {

		final var stocks = stockService.findAllStocksByUser();
		var stockResponses = stocks.stream()
				.map(StockMapper::toStockResponse)
				.toList();

		return ResponseEntity.ok(stockResponses);
	}

	@Operation(summary = "Get stocks by user", security = @SecurityRequirement(name = "bearerAuth"))
	@GetMapping("/detail/{stockId}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<List<StockPurchaseResponse>> getPurchasesByStockId(@PathVariable String stockId) {
		return stockService.findById(stockId)
				.map(stock -> {
					final List<StockPurchaseResponse> stockPurchaseResponseList = stock.getPurchases()
							.stream()
							.map(StockMapper::toStockDetailResponse)
							.toList();

					return ResponseEntity.status(HttpStatus.OK).body(stockPurchaseResponseList);
				})
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

	}

	@Operation(summary = "Delete stock by stock id", security = @SecurityRequirement(name = "bearerAuth"))
	@DeleteMapping("{stockId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteStockById(@PathVariable String stockId) {

		final var optStock = stockService.findById(stockId);

		if (optStock.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		stockService.deleteStockById(stockId);
		stockService.evictCacheByStock(optStock.get().getStock());

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@Operation(summary = "Clear cache storage completely", security = @SecurityRequirement(name = "bearerAuth"))
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/refresh-cache/{cacheName}")
	public ResponseEntity<String> refreshCache(@PathVariable String cacheName) {
		stockService.refreshStockCache(cacheName);
		return ResponseEntity.ok("Cache " + cacheName + " has been refreshed.");
	}
}
