# Receipt Processor microservice

# Verify the following endpoints by running sending requests using Postman

Endpoint: Process Receipts
Path: /receipts/process
Method: POST
Payload: Receipt JSON
Response: JSON containing an id for the receipt.

Example:

Body (Type = JSON):
{
  "retailer": "Target",
  "purchaseDate": "2022-01-01",
  "purchaseTime": "13:01",
  "items": [
    {
      "shortDescription": "Mountain Dew 12PK",
      "price": "6.49"
    },{
      "shortDescription": "Emils Cheese Pizza",
      "price": "12.25"
    },{
      "shortDescription": "Knorr Creamy Chicken",
      "price": "1.26"
    },{
      "shortDescription": "Doritos Nacho Cheese",
      "price": "3.35"
    },{
      "shortDescription": "Klarbrunn 12-PK 12 FL OZ",
      "price": "12.00"
    }
  ],
  "total": "35.35"
}

Expected Response (JSON):

{
    "id": "1f5ef0fa-2848-400f-af25-dcc4ea8ff50a"
}

Endpoint: Get Points
Path: /receipts/{id}/points
Method: GET
Response: A JSON object containing the number of points awarded.

Example:

Send Request: GET http://localhost:8080/receipts/1f5ef0fa-2848-400f-af25-dcc4ea8ff50a/points

Expected Response: 
{
    "points": 28
}