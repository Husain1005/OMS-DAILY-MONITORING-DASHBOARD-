# Titan Order Count Service - Quick Reference

## Service Integration Summary

### What Was Built
- ✅ XML response parsing from Titan OMS API
- ✅ Spring Boot REST endpoint for order count
- ✅ Support for Basic Authentication
- ✅ Comprehensive test suite
- ✅ Multiple testing tools (Postman, cURL, JUnit)

## Key Files Modified/Created

| File | Type | Status |
|------|------|--------|
| pom.xml | Modified | Added Jackson XML dependency |
| application.properties | Modified | Added Titan configuration |
| OmsService.java | Modified | Added getTitanOrderCount() method |
| ReportController.java | Modified | Added /api/reports/titan/order-count endpoint |
| OrderCountRow.java | Created | XML row model |
| OrderCountRowset.java | Created | XML rowset wrapper model |
| OmsServiceTitanOrderCountTest.java | Created | JUnit test class |
| TITAN_ORDER_COUNT_SETUP.md | Created | Setup guide |
| IMPLEMENTATION_SUMMARY.md | Created | Implementation details |
| Titan_OrderCount_Postman.json | Created | Postman collection |
| titan-order-count-test.bat | Created | Windows test script |
| titan-order-count-test.sh | Created | Linux/Mac test script |

## Quick Setup (5 Steps)

### 1. Update Configuration
Edit `application.properties`:
```properties
titan.order-count.url=https://titan-preprod-1.oms.supply-chain.ibm.com/smcfs/restapi/executeFlow/Titan_OrderCount
titan.order-count.username=YOUR_USERNAME
titan.order-count.password=YOUR_PASSWORD
```

### 2. Build
```bash
mvn clean compile
```

### 3. Run
```bash
mvn spring-boot:run
```

### 4. Test
```bash
curl -X POST http://localhost:8080/api/reports/titan/order-count \
  -H "Content-Type: application/xml" \
  -d '<Order />'
```

### 5. Verify
Look for XML response with ROWSET and ROW elements

## API Endpoint

**URL**: `http://localhost:8080/api/reports/titan/order-count`
**Methods**: GET, POST
**Request**: `<Order />`
**Response**: XML with order counts by entry type and shipnode

## Response Example
```xml
<ROWSET>
    <ROW ENTRY_TYPE="Myntra" SHIPNODE_KEY="H1F" TOTAL_ORDERS="2"/>
    <ROW ENTRY_TYPE="WCS" SHIPNODE_KEY="WLF" TOTAL_ORDERS="147"/>
</ROWSET>
```

## Testing Options

| Method | Command | File |
|--------|---------|------|
| cURL (Windows) | `titan-order-count-test.bat` | titan-order-count-test.bat |
| cURL (Linux/Mac) | `bash titan-order-count-test.sh` | titan-order-count-test.sh |
| Postman | Import `Titan_OrderCount_Postman.json` | Postman Desktop App |
| JUnit | `mvn test -Dtest=OmsServiceTitanOrderCountTest` | Maven |
| Browser Console | See TITAN_ORDER_COUNT_SETUP.md | Any browser |

## Common Issues & Fixes

```
❌ 401 Unauthorized
✅ Update username/password in application.properties

❌ 404 Not Found
✅ Verify URL in application.properties

❌ Connection Timeout
✅ Check network access to titan-preprod-1.oms.supply-chain.ibm.com

❌ Maven compilation error
✅ Run: mvn clean && mvn compile

❌ Jackson error
✅ Verify jackson-dataformat-xml dependency in pom.xml
```

## Architecture

```
Browser/Client
     ↓
Spring Boot Application (Port 8080)
     ↓
ReportController (/api/reports/titan/order-count)
     ↓
OmsService.getTitanOrderCount()
     ↓
RestTemplate (HTTP POST)
     ↓
IBM Sterling OMS Titan
(titan-preprod-1.oms.supply-chain.ibm.com)
     ↓
Returns XML Response
     ↓
Jackson XML Parser
     ↓
OrderCountRowset Model
     ↓
Returns JSON Response to Client
```

## Properties Reference

```properties
# Service URL (default pre-configured)
titan.order-count.url=https://...

# Authentication (optional)
titan.order-count.username=
titan.order-count.password=
```

## Deployment Checklist

- [ ] Pull latest code
- [ ] Update application.properties with credentials
- [ ] Run `mvn clean package`
- [ ] Test locally with cURL/Postman
- [ ] Deploy JAR to server
- [ ] Verify endpoint in preprod/prod
- [ ] Monitor logs for errors

## Support Resources

| Resource | Location |
|----------|----------|
| Setup Guide | TITAN_ORDER_COUNT_SETUP.md |
| Implementation Details | IMPLEMENTATION_SUMMARY.md |
| Postman Tests | Titan_OrderCount_Postman.json |
| Test Scripts | titan-order-count-test.* |
| Source Code | src/main/java/com/example/omsmonitoringdashboard/* |

## Technology Stack

- **Framework**: Spring Boot 3.5.4
- **Java Version**: 21
- **Build Tool**: Maven 3.8+
- **XML Parsing**: Jackson Dataformat XML
- **Authentication**: Basic Auth (HTTP)
- **Protocol**: HTTPS (REST)

## Performance Notes

- Response time depends on Titan service
- No caching implemented (add if frequently accessed)
- Supports concurrent requests
- Basic Auth overhead negligible

---
**Status**: ✅ Ready for Testing
**Last Updated**: 2024
**Version**: 1.0
