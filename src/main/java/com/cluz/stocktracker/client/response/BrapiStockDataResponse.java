package com.cluz.stocktracker.client.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"regularMarketPrice"})
public class BrapiStockDataResponse implements Serializable {
	private String currency;
	private String marketCap;
	private String shortName;
	private String longName;
	private Double regularMarketChange;
	private Double regularMarketChangePercent;
	private LocalDate regularMarketTime;
	private Double regularMarketPrice;
	private Double regularMarketDayHigh;
	private String regularMarketDayRange;
	private Double regularMarketDayLow;
	private Double regularMarketVolume;
	private Double regularMarketPreviousClose;
	private Double regularMarketOpen;
	private String fiftyTwoWeekRange;
	private Double fiftyTwoWeekLow;
	private Double fiftyTwoWeekHigh;
	private String symbol;
	private Double priceEarnings;
	private Double earningsPerShare;
	private String logourl;
}
