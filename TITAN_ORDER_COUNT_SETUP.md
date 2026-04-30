# Titan Order Count Service Configuration Guide

## Overview
This guide explains how to configure and test the IBM Sterling OMS Titan Order Count service integration.

## Service Details
- **Service URL**: `https://titan-preprod-1.oms.supply-chain.ibm.com/smcfs/restapi/executeFlow/Titan_OrderCount`
- **Method**: POST
- **Request Content-Type**: application/xml
- **Response Content-Type**: application/xml
- **Request Body**: `<Order />`

## Configuration Steps

### 1. Update application.properties
Edit `src/main/resources/application.properties` and configure the following properties:

```properties
# Titan Order Count Service Configuration (IBM Sterling OMS)
titan.order-count.url=https://titan-preprod-1.oms.supply-chain.ibm.com/smcfs/restapi/executeFlow/Titan_OrderCount

# If Basic Auth is required, provide credentials:
titan.order-count.username=your-titan-username
titan.order-count.password=your-titan-password

# Leave empty if no authentication is required:
titan.order-count.username=
titan.order-count.password=
```

### 2. Build the Project

```bash
# Using Maven (if installed globally)
mvn clean compile -DskipTests

# Or use Maven wrapper if available
./mvnw clean compile -DskipTests

# Or compile with IDE (IntelliJ, Eclipse, VS Code)
```

### 3. Run the Application

```bash
# Using Maven
mvn spring-boot:run

# Or deploy as JAR
mvn clean package
java -jar target/oms-monitoring-dashboard-0.0.1-SNAPSHOT.jar
```

## Testing the Service

### API Endpoint
Once the application is running, access the Titan Order Count service at:

```
POST http://localhost:8080/api/reports/titan/order-count
OR
GET http://localhost:8080/api/reports/titan/order-count
```

### Response Format
The service returns XML formatted as:

```xml
<ROWSET>
    <ROW ENTRY_TYPE="Myntra" SHIPNODE_KEY="H1F                     " TOTAL_ORDERS="2"/>
    <ROW ENTRY_TYPE="Nykaa" SHIPNODE_KEY="H1F                     " TOTAL_ORDERS="6"/>
    <ROW ENTRY_TYPE="TataCliq" SHIPNODE_KEY="H1F                     " TOTAL_ORDERS="1"/>
    <ROW ENTRY_TYPE="WCS" SHIPNODE_KEY="H1F                     " TOTAL_ORDERS="541"/>
    <ROW ENTRY_TYPE="WCS" SHIPNODE_KEY="N1F                     " TOTAL_ORDERS="6"/>
    <ROW ENTRY_TYPE="WCS" SHIPNODE_KEY="WEC                     " TOTAL_ORDERS="20"/>
    <ROW ENTRY_TYPE="WCS" SHIPNODE_KEY="WLF                     " TOTAL_ORDERS="147"/>
</ROWSET>
```

## Testing Methods

### Method 1: Using cURL
See `titan-order-count-test.sh` for detailed cURL commands.

### Method 2: Using Postman
1. Create a new request
2. Set method to POST
3. URL: `http://localhost:8080/api/reports/titan/order-count`
4. Headers: 
   - Content-Type: application/xml
   - Accept: application/xml
5. Body (raw): `<Order />`
6. Click Send

### Method 3: Using Java Test Class
Run the JUnit test:
```bash
mvn test -Dtest=OmsServiceTitanOrderCountTest
```

### Method 4: Using Browser Console
```javascript
fetch('http://localhost:8080/api/reports/titan/order-count', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/xml',
        'Accept': 'application/xml'
    },
    body: '<Order />'
})
.then(response => response.text())
.then(data => console.log(data))
.catch(error => console.error('Error:', error));
```

## Troubleshooting

### Issue: 401 Unauthorized
**Solution**: Verify Basic Auth credentials in `application.properties`

### Issue: 404 Not Found
**Solution**: Verify the service URL is correct in `application.properties`

### Issue: Connection Timeout
**Solution**: 
- Check if the preprod server is accessible
- Verify network connectivity to `titan-preprod-1.oms.supply-chain.ibm.com`
- Check firewall rules

### Issue: XML Parsing Error
**Solution**: 
- Ensure the response is valid XML
- Verify Jackson XML dependency is added to pom.xml

## Project Structure

```
src/main/
  java/com/example/omsmonitoringdashboard/
    controller/
      ReportController.java          # REST endpoint (GET/POST /api/reports/titan/order-count)
    service/
      OmsService.java                # Service with getTitanOrderCount() method
    model/
      OrderCountRow.java             # XML row model
      OrderCountRowset.java          # XML rowset wrapper model
  resources/
    application.properties           # Configuration file
```

## Files Created/Modified

1. **pom.xml** - Added Jackson XML dependency
2. **application.properties** - Added Titan configuration
3. **OmsService.java** - Added getTitanOrderCount() method
4. **ReportController.java** - Added /titan/order-count endpoint
5. **OrderCountRow.java** - New model class (created)
6. **OrderCountRowset.java** - New model class (created)
7. **OmsServiceTitanOrderCountTest.java** - New test class (created)

## Dependencies Added

```xml
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
</dependency>
```

## Next Steps

1. Update credentials in `application.properties`
2. Build and run the application
3. Test the endpoint using one of the methods above
4. Monitor logs for any errors
5. Deploy to preprod/production environment
