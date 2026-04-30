#!/bin/bash

# Titan Order Count Service - Testing Script
# This script contains various test commands for the Titan Order Count endpoint

# Configuration
LOCALHOST="http://localhost:8080"
API_ENDPOINT="$LOCALHOST/api/reports/titan/order-count"
TITAN_URL="https://titan-preprod-1.oms.supply-chain.ibm.com/smcfs/restapi/executeFlow/Titan_OrderCount"

# Color codes
RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo ""
echo "========================================"
echo -e "${BLUE}Titan Order Count Service - Test Suite${NC}"
echo "========================================"
echo ""

# Test 1: Local Spring Boot Service Test
echo -e "${GREEN}[TEST 1]${NC} Testing Local Spring Boot Service Endpoint"
echo "URL: $API_ENDPOINT"
echo ""
echo "Running: curl -X POST '$API_ENDPOINT' -H 'Content-Type: application/xml' -d '<Order />'"
echo ""

curl -X POST "$API_ENDPOINT" \
  -H "Content-Type: application/xml" \
  -H "Accept: application/xml" \
  -d '<Order />' \
  -v

echo ""
echo ""

# Test 2: Direct Titan Preprod Service Test (with GET)
echo -e "${GREEN}[TEST 2]${NC} Testing Direct Titan Preprod Service (GET)"
echo "URL: $TITAN_URL"
echo ""

curl -X GET "$TITAN_URL" \
  -H "Accept: application/xml" \
  -v

echo ""
echo ""

# Test 3: Direct Titan Preprod Service Test (with POST)
echo -e "${GREEN}[TEST 3]${NC} Testing Direct Titan Preprod Service (POST with empty Order)"
echo "URL: $TITAN_URL"
echo ""

curl -X POST "$TITAN_URL" \
  -H "Content-Type: application/xml" \
  -H "Accept: application/xml" \
  -d '<Order />' \
  -v

echo ""
echo ""

# Test 4: Test with Basic Authentication (if required)
echo -e "${GREEN}[TEST 4]${NC} Testing with Basic Authentication"
echo ""
USERNAME="your-username"
PASSWORD="your-password"

curl -X POST "$API_ENDPOINT" \
  -H "Content-Type: application/xml" \
  -H "Accept: application/xml" \
  -d '<Order />' \
  --basic \
  -u "$USERNAME:$PASSWORD" \
  -v

echo ""
echo ""

# Test 5: Silent test (without verbose output)
echo -e "${GREEN}[TEST 5]${NC} Testing Local Endpoint (Silent Mode)"
echo ""

curl -X POST "$API_ENDPOINT" \
  -H "Content-Type: application/xml" \
  -H "Accept: application/xml" \
  -d '<Order />' \
  -s

echo ""
echo ""

# Test 6: Save response to file
echo -e "${GREEN}[TEST 6]${NC} Saving response to file: titan_order_count_response.xml"
echo ""

curl -X POST "$API_ENDPOINT" \
  -H "Content-Type: application/xml" \
  -H "Accept: application/xml" \
  -d '<Order />' \
  -o titan_order_count_response.xml \
  -v

echo ""
echo "Response saved to: titan_order_count_response.xml"
echo ""

# Display saved response
if [ -f "titan_order_count_response.xml" ]; then
    echo ""
    echo "===== Response Content ====="
    cat titan_order_count_response.xml
    echo ""
    echo "===== End Response ====="
    echo ""
fi

echo ""
echo "========================================"
echo -e "${BLUE}Testing Complete!${NC}"
echo "========================================"
echo ""
echo "Notes:"
echo "1. Ensure Spring Boot application is running on port 8080"
echo "2. Update credentials if Basic Auth is required"
echo "3. Check application.properties for Titan service configuration"
echo "4. For direct Titan testing, ensure network access to preprod environment"
echo ""
