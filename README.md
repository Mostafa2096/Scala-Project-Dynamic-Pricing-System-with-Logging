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
