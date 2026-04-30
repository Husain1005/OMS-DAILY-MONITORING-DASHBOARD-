@echo off
REM Titan Order Count Service - Testing Script
REM This script contains various test commands for the Titan Order Count endpoint

setlocal enabledelayedexpansion

REM Configuration
set LOCALHOST=http://localhost:8080
set API_ENDPOINT=%LOCALHOST%/api/reports/titan/order-count
set TITAN_URL=https://titan-preprod-1.oms.supply-chain.ibm.com/smcfs/restapi/executeFlow/Titan_OrderCount

REM Colors for output
REM (Note: Not all Windows cmd supports ANSI colors, using simple text instead)

echo.
echo ===============================================
echo Titan Order Count Service - Test Suite
echo ===============================================
echo.

REM Test 1: Local Spring Boot Service Test
echo [TEST 1] Testing Local Spring Boot Service Endpoint
echo URL: %API_ENDPOINT%
echo.
echo Running: curl -X POST "%API_ENDPOINT%" -H "Content-Type: application/xml" -H "Accept: application/xml" -d "^<Order /^>"
echo.

curl -X POST "%API_ENDPOINT%" ^
  -H "Content-Type: application/xml" ^
  -H "Accept: application/xml" ^
  -d "^<Order /^>" ^
  -v

echo.
echo.

REM Test 2: Direct Titan Preprod Service Test (with GET)
echo [TEST 2] Testing Direct Titan Preprod Service (GET)
echo URL: %TITAN_URL%
echo.
echo Running: curl -X GET "%TITAN_URL%" -H "Accept: application/xml" -v
echo.

curl -X GET "%TITAN_URL%" ^
  -H "Accept: application/xml" ^
  -v

echo.
echo.

REM Test 3: Direct Titan Preprod Service Test (with POST)
echo [TEST 3] Testing Direct Titan Preprod Service (POST with empty Order)
echo URL: %TITAN_URL%
echo.
echo Running: curl -X POST "%TITAN_URL%" -H "Content-Type: application/xml" -d "^<Order /^>"
echo.

curl -X POST "%TITAN_URL%" ^
  -H "Content-Type: application/xml" ^
  -H "Accept: application/xml" ^
  -d "^<Order /^>" ^
  -v

echo.
echo.

REM Test 4: Test with Basic Authentication (if required)
echo [TEST 4] Testing with Basic Authentication (Update credentials as needed)
echo.
set USERNAME=your-username
set PASSWORD=your-password
echo Running: curl -X POST "%API_ENDPOINT%" --basic -u "%USERNAME%:%PASSWORD%" ...
echo.

curl -X POST "%API_ENDPOINT%" ^
  -H "Content-Type: application/xml" ^
  -H "Accept: application/xml" ^
  -d "^<Order /^>" ^
  --basic ^
  -u "%USERNAME%:%PASSWORD%" ^
  -v

echo.
echo.

REM Test 5: Silent test (without verbose output)
echo [TEST 5] Testing Local Endpoint (Silent Mode)
echo.

curl -X POST "%API_ENDPOINT%" ^
  -H "Content-Type: application/xml" ^
  -H "Accept: application/xml" ^
  -d "^<Order /^>" ^
  -s

echo.
echo.

REM Test 6: Save response to file
echo [TEST 6] Saving response to file: titan_order_count_response.xml
echo.

curl -X POST "%API_ENDPOINT%" ^
  -H "Content-Type: application/xml" ^
  -H "Accept: application/xml" ^
  -d "^<Order /^>" ^
  -o titan_order_count_response.xml ^
  -v

echo.
echo Response saved to: titan_order_count_response.xml
echo.

REM Display saved response
if exist titan_order_count_response.xml (
    echo.
    echo ===== Response Content =====
    type titan_order_count_response.xml
    echo ===== End Response =====
    echo.
)

echo.
echo ===============================================
echo Testing Complete!
echo ===============================================
echo.
echo Notes:
echo 1. Ensure Spring Boot application is running on port 8080
echo 2. Update credentials in test 4 if Basic Auth is required
echo 3. Check application.properties for Titan service configuration
echo 4. For direct Titan testing, ensure network access to preprod environment
echo.

endlocal
