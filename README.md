# Scala-Project-Dynamic-Pricing-System-with-Logging-And-MySQL-Database

## Overview

This project is designed to calculate discounts based on specific rules for transactional data and insert the calculated data into a MySQL database. The project is implemented in Scala and utilizes Log4j2 for logging purposes.

## Components

- **Scala Code**: The core logic of the discount calculation and database insertion is implemented in Scala programming language.
- **Log4j2**: Logging framework used to log events and messages during the execution of the program.
- **MySQL Database**: The target database where the calculated discounts and corresponding prices after discount will be stored.

## Tools Used

- **Scala**: A functional programming language running on the Java Virtual Machine (JVM).
- **Log4j2**: A fast, flexible, and comprehensive logging framework for Java and Scala.
- **MySQL**: An open-source relational database management system (RDBMS).
- **Git**: Version control system for tracking changes in the project.

## Input Data

The input data for this project is stored in a CSV file named `TRX1000.csv`. This file contains transactional data including purchase date, product name, expiration date, units sold, channel, payment method, etc. Each row represents a single transaction.

![image](https://github.com/Mostafa2096/Scala-Project-Dynamic-Pricing-System-with-Logging-And-MySQL-Database/assets/106194954/f2675819-8f89-4e5a-a2c9-d7d31ee43fde)


## Output

The output of the project includes:

- **Discounts**: The calculated discounts based on specific rules for each transaction.
- **Prices After Discount**: The prices after applying the calculated discounts for each transaction.

The calculated discounts and prices after discount will be inserted into the MySQL database or csv file for further analysis or processing.

![image](https://github.com/Mostafa2096/Scala-Project-Dynamic-Pricing-System-with-Logging-And-MySQL-Database/assets/106194954/493074cc-b886-46e8-8b3b-01aba423c184)

OR

![image](https://github.com/Mostafa2096/Scala-Project-Dynamic-Pricing-System-with-Logging-And-MySQL-Database/assets/106194954/acdfd0a6-bf5f-4d95-8830-895b8dca467a)


## Qualifying Rules and Calculation

### Less Than 30 Days Remaining for Product Expiry
- If the product has 29 days remaining until expiry, a 1% discount is applied.
- If the product has 28 days remaining until expiry, a 2% discount is applied.
- If the product has 27 days remaining until expiry, a 3% discount is applied.
- Discounts continue to increase by 1% for each day closer to expiry, up to a maximum of 29% for products with only 1 day remaining until expiry.

### Cheese and Wine Special Discounts
- Cheese products are eligible for a 10% discount.
- Wine products are eligible for a 5% discount.

### Special Discount for March 23rd Sales
- Products sold on March 23rd receive a special discount of 50%.

### Bulk Purchase Discounts
- If more than 5 units of the same product are purchased:
  - For 6 to 9 units: a 5% discount is applied.
  - For 10 to 14 units: a 7% discount is applied.
  - For 15 or more units: a 10% discount is applied.

### App Sales Special Discount
- Sales made through the app qualify for a special discount based on the quantity purchased, rounded up to the nearest multiple of 5:
  - For quantities 1 to 5: a 5% discount is applied.
  - For quantities 6 to 10: a 10% discount is applied.
  - For quantities 11 to 15: a 15% discount is applied.
  - Discounts increase by 5% for each additional multiple of 5 in quantity.

### Visa Card Sales Discount
- Sales made using Visa cards qualify for a minor discount of 5%.

These rules govern the calculation of discounts for each transaction in the system. The Scala code implements these rules to determine the appropriate discount for each product based on the transaction details.
