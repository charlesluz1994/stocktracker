package com.cluz.stocktracker.controller;

import com.cluz.stocktracker.controller.request.StockAddPurchaseRequest;
import com.cluz.stocktracker.controller.request.StockRequest;
import com.cluz.stocktracker.entity.Stock;
import com.cluz.stocktracker.entity.StockPurchase;
import com.cluz.stocktracker.mapper.StockMapper;
import com.cluz.stocktracker.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Stock> savePurchase(@RequestBody StockRequest request) {
		Pair<Stock, StockPurchase> stock = StockMapper.toStock(request);
		var savedStock = stockService.savePurchase(stock.getFirst(), stock.getSecond());

		return ResponseEntity.ok(savedStock);
	}

	@PostMapping("/add")
	public ResponseEntity<Stock> addPurchaseStock(@RequestBody StockAddPurchaseRequest request) {
		try {
			var stockPurchase = StockMapper.toStockPurchase(request);
			var savedPurchase = stockService.addPurchaseStock(request.getStockId(), stockPurchase);

			return ResponseEntity.ok(savedPurchase);
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.unprocessableEntity().build();
		}
	}

	@GetMapping("/all")
	public ResponseEntity<List<Stock>> findAllStock() {
		return ResponseEntity.ok(stockService.findAllStock());
	}
}
