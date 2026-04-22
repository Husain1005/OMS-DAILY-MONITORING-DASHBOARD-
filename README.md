# OMS Daily Monitoring Dashboard

A Spring Boot application that provides a modern web dashboard for monitoring OMS (Order Management System) daily reports. The dashboard displays various order metrics and statuses, refreshing automatically every 5 minutes.

## Features

- **Order Count**: Displays order counts by shipnode and entry type from April onwards
- **HMTR Orders**: Shows yesterday's order counts for HMTR and other shipnodes
- **Backorder Report**: Displays the mail subject for backorder reports
- **Rejected at POS**: Shows the mail subject for rejected at POS reports
- **Manual Cancellation**: Lists orders cancelled manually today
- **Fraud Check Hold Orders**: Orders stuck in fraud check with holds for more than 48 hours
- **Stuck Orders (No Holds)**: Orders stuck without holds for more than 4 hours
- **EGC and GC Orders**: Electronic Gift Card and Gift Card orders with scheduled dates

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Oracle Database with OMS schema

## Configuration

Update src/main/resources/application.properties with your database connection details:

`properties
spring.datasource.url=jdbc:oracle:thin:@//your-host:1521/your-service
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
`

## Building and Running

1. Clone the repository
2. Navigate to the project directory
3. Run mvn clean install
4. Run mvn spring-boot:run
5. Open http://localhost:8080 in your browser

## Notes

- The dashboard auto-refreshes every 5 minutes
- 'WCS' entry types are displayed as 'SFCC'
- Flipkart orders are excluded from all reports
- Tax Order SFTQ000344232 is excluded from fraud check hold orders
- Dates are dynamically calculated (e.g., yesterday, today)

## Technologies Used

- Spring Boot 3.1.0
- Thymeleaf
- Bootstrap 5
- Oracle JDBC Driver
- Java 17
