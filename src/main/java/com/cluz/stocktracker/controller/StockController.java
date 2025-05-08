package com.cluz.stocktracker.controller;

import com.cluz.stocktracker.client.response.StockResponse;
import com.cluz.stocktracker.controller.request.StockAddPurchaseRequest;
import com.cluz.stocktracker.controller.request.StockRequest;
import com.cluz.stocktracker.entity.Stock;
import com.cluz.stocktracker.entity.StockPurchase;
import com.cluz.stocktracker.mapper.StockMapper;
import com.cluz.stocktracker.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {
	private final StockService stockService;

	@PostMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<StockResponse> savePurchase(@RequestBody StockRequest request) {
		Pair<Stock, StockPurchase> stock = StockMapper.toStock(request);
		var savedStock = stockService.savePurchase(stock.getFirst(), stock.getSecond());

		return ResponseEntity.status(HttpStatus.CREATED).body(StockMapper.toStockResponse(savedStock));
	}

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

	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<List<Stock>> findAllStock() {
		return ResponseEntity.ok(stockService.findAllStock());
	}
}
