# StockTracker Application

This is a Restful application to check current stock prices, buy stocks, and add purchases to an existing stock

Using OpenFeign to connect brapi.dev


Using Redis - Docker locally:
docker pull redis

docker run -d --name my-redis -p 6379:6379 redis redis-server --requirepass "sa"


Tecnologies:
Java 17
Maven
MongoDB
Redis for caching

## Controllers

### StockController
This controller handles operations related to stocks.

Actions:

Create a Stock
Endpoint: `POST /stock`
This endpoint creates a new stock. You need to send a JSON object containing the stock data in the request body.

List all stocks
Endpoint: GET /stock/all
This endpoint returns all registered stocks and purchases with current stock price.

Insert a new purchase in a registered stock
Endpoint: `POST /stock/add`
This endpoint inserts a new purchase in a registered stock based on the provided ID in the request body.


Website used to get stock information:
https://brapi.dev/
https://brapi.dev/api/quote/ITUB4?token=