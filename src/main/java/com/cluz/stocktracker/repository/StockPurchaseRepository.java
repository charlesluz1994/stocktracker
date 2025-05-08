package com.cluz.stocktracker.repository;

import com.cluz.stocktracker.entity.StockPurchase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockPurchaseRepository extends MongoRepository<StockPurchase, String> {
}