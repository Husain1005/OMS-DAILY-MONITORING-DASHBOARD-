# Titan Order Count Service - Manual Testing Guide

## Overview
This guide provides step-by-step instructions for manually testing the Titan Order Count service integration.

---

## Method 1: Testing with Postman (Recommended for UI Testing)

### Step 1: Import Postman Collection
1. Open Postman Desktop Application
2. Click `Import` button (top-left)
3. Select `File` tab
4. Navigate to and select: `Titan_OrderCount_Postman.json`
5. Click `Import`

### Step 2: Configure Credentials (if needed)
1. In Postman, navigate to collection: `Titan Order Count Service`
2. Open folder: `Local Spring Boot Service`
3. Select request: `Get Titan Order Count - With Auth`
4. Click `Authorization` tab
5. Enter your credentials:
   - Username: Your Titan username
   - Password: Your Titan password
6. Click `Send`

### Step 3: Test Local Spring Boot Service
1. Ensure application is running: `mvn spring-boot:run`
2. In Postman, select: `Get Titan Order Count - POST`
3. Click `Send`
4. Verify response:
   - Status: `200 OK`
   - Body contains: `<ROWSET>`, `<ROW>`, `ENTRY_TYPE`, `SHIPNODE_KEY`, `TOTAL_ORDERS`

### Step 4: Test Direct Titan Preprod Service
1. Select folder: `Direct Titan Preprod Service`
2. Choose request: `Titan Preprod - Direct POST`
3. Click `Send`
4. Verify response (may need credentials):
   - Status: `200 OK`
   - Body: XML with order counts

---

## Method 2: Testing with cURL (Command Line)

### Prerequisite
Ensure curl is installed:
```bash
curl --version
```

### Step 1: Test Local Spring Boot Service (Basic)
```bash
curl -X POST http://localhost:8080/api/reports/titan/order-count \
  -H "Content-Type: application/xml" \
  -H "Accept: application/xml" \
  -d '<Order />' \
  -v
```

**Expected Output:**
- HTTP Status: 200 OK
- Response Body: XML with ROWSET structure

### Step 2: Test with Authentication
```bash
curl -X POST http://localhost:8080/api/reports/titan/order-count \
  -H "Content-Type: application/xml" \
  -H "Accept: application/xml" \
  -d '<Order />' \
  --basic \
  -u "username:password" \
  -v
```

Replace `username:password` with actual credentials.

### Step 3: Test Direct Titan Service
```bash
curl -X POST https://titan-preprod-1.oms.supply-chain.ibm.com/smcfs/restapi/executeFlow/Titan_OrderCount \
  -H "Content-Type: application/xml" \
  -H "Accept: application/xml" \
  -d '<Order />' \
  -v
```

### Step 4: Save Response to File
```bash
curl -X POST http://localhost:8080/api/reports/titan/order-count \
  -H "Content-Type: application/xml" \
  -d '<Order />' \
  -o response.xml
```

Then view the file:
```bash
cat response.xml
```

### Step 5: Run Complete Test Suite (Windows)
```batch
titan-order-count-test.bat
```

### Step 6: Run Complete Test Suite (Linux/Mac)
```bash
bash titan-order-count-test.sh
```

---

## Method 3: Testing with Java/JUnit

### Step 1: Run All Tests
```bash
mvn test -Dtest=OmsServiceTitanOrderCountTest
```

### Step 2: Run Specific Test Method
```bash
mvn test -Dtest=OmsServiceTitanOrderCountTest#testGetTitanOrderCount
```

### Step 3: View Test Results
Output will show:
- Test method names
- Pass/Fail status
- Response details
- Total orders count

**Expected Output Example:**
```
EntryType: Myntra, ShipnodeKey: H1F, TotalOrders: 2
EntryType: Nykaa, ShipnodeKey: H1F, TotalOrders: 6
Test passed! Total rows received: 7
```

---

## Method 4: Testing with Browser Console

### Step 1: Start Application
```bash
mvn spring-boot:run
```

### Step 2: Open Browser
- Open any modern browser (Chrome, Firefox, Edge, Safari)
- Press `F12` to open Developer Tools
- Go to `Console` tab

### Step 3: Execute Fetch Request
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
.then(data => {
    console.log('Response received:');
    console.log(data);
})
.catch(error => {
    console.error('Error:', error);
});
```

### Step 4: Analyze Response
- Check console for XML response
- Verify structure with ROW elements
- Check for any error messages

---

## Method 5: Testing with IDE (IntelliJ/VS Code)

### IntelliJ IDEA
1. Open `OmsServiceTitanOrderCountTest.java`
2. Click green arrow next to class name or test method
3. Select `Run 'OmsServiceTitanOrderCountTest'`
4. View results in Run window

### VS Code with REST Client Extension
1. Create file `test.http`:
```
POST http://localhost:8080/api/reports/titan/order-count
Content-Type: application/xml
Accept: application/xml

<Order />
```

2. Install `REST Client` extension
3. Click `Send Request` above the request
4. View response in side panel

---

## Validation Checklist

After running any test, verify:

### Response Structure ✓
- [ ] Response starts with `<ROWSET>`
- [ ] Contains `<ROW>` elements
- [ ] Each ROW has attributes: `ENTRY_TYPE`, `SHIPNODE_KEY`, `TOTAL_ORDERS`
- [ ] Response ends with `</ROWSET>`

### Data Validation ✓
- [ ] ENTRY_TYPE values: Myntra, Nykaa, TataCliq, WCS
- [ ] SHIPNODE_KEY not empty (padded with spaces)
- [ ] TOTAL_ORDERS is numeric and >= 0
- [ ] At least one ROW element present

### Status Codes ✓
- [ ] HTTP 200 for successful responses
- [ ] HTTP 401 if authentication fails
- [ ] HTTP 404 if endpoint not found
- [ ] HTTP 500 if server error

### Error Handling ✓
- [ ] Clear error messages in logs
- [ ] Connection errors logged
- [ ] Authentication failures identified
- [ ] XML parsing errors caught

---

## Sample Response

```xml
<?xml version="1.0" encoding="UTF-8"?>
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

---

## Troubleshooting During Testing

### Issue: "Connection refused"
**Cause**: Application not running
**Solution**: 
```bash
mvn spring-boot:run
```

### Issue: "401 Unauthorized"
**Cause**: Wrong or missing credentials
**Solution**:
1. Check `application.properties` for credentials
2. Verify credentials with Titan admin
3. Update properties file

### Issue: "404 Not Found"
**Cause**: Wrong endpoint URL
**Solution**:
- Verify endpoint: `http://localhost:8080/api/reports/titan/order-count`
- Check port (default 8080)
- Verify application is running

### Issue: "SSL Certificate Error"
**Cause**: HTTPS certificate issue with Titan server
**Solution**:
- Contact Titan admin for certificate
- Or use cURL with `-k` flag (not recommended for production)

### Issue: "Timeout"
**Cause**: Network/server slow or down
**Solution**:
- Check network connectivity
- Verify Titan server status
- Increase timeout in application.properties

### Issue: "XML Parsing Error"
**Cause**: Invalid XML response
**Solution**:
- Verify response format
- Check for encoding issues
- Review logs for details

---

## Performance Testing

### Test 1: Response Time
```bash
time curl -X POST http://localhost:8080/api/reports/titan/order-count \
  -H "Content-Type: application/xml" \
  -d '<Order />' \
  -s -o /dev/null
```

### Test 2: Multiple Concurrent Requests
```bash
# Using Apache Bench (ab)
ab -n 100 -c 10 -p '<Order />' \
  -H "Content-Type: application/xml" \
  http://localhost:8080/api/reports/titan/order-count
```

### Test 3: Load Testing
```bash
# Using Apache Bench
ab -n 1000 -c 50 \
  http://localhost:8080/api/reports/titan/order-count
```

---

## Creating Custom Tests

### Using REST Assured Library
```java
@Test
public void testTitanOrderCount() {
    RestAssured
        .given()
            .contentType("application/xml")
            .accept("application/xml")
            .body("<Order />")
        .when()
            .post("http://localhost:8080/api/reports/titan/order-count")
        .then()
            .statusCode(200)
            .body("ROWSET.ROW.size()", greaterThan(0));
}
```

---

## Documentation Reference

- **Setup**: See `TITAN_ORDER_COUNT_SETUP.md`
- **Implementation**: See `IMPLEMENTATION_SUMMARY.md`
- **Quick Reference**: See `QUICK_REFERENCE.md`
- **Source Code**: `src/main/java/com/example/omsmonitoringdashboard/`

---

## Next Steps After Successful Testing

1. **Deploy to Preprod**: Use the tested configuration
2. **Monitor Logs**: Watch for any errors in production
3. **Performance Tuning**: Optimize if needed
4. **User Documentation**: Train users on new endpoint
5. **Maintenance**: Plan for updates/upgrades

---

**Last Updated**: 2024
**Status**: ✅ Ready for Testing
**Version**: 1.0
