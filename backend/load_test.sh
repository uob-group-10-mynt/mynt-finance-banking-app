#!/bin/bash

# Configuration
AUTH_URL="http://localhost:8080/api/v1/auth/authenticate"
LOAD_TEST_URLS=(
    "http://localhost:8080/api/v1/users/getUserDetails"
    "http://localhost:8080/api/v1/rates/getBasicRates"
    # Add more endpoints as needed
)
EMAIL="alexandermichael@mail.com"
PASSWORD="cake"
REQUESTS=1000
CONCURRENCY=10

# Step 1: Authenticate and get the access token
echo "Authenticating..."
AUTH_RESPONSE=$(curl -s -X POST "$AUTH_URL" -H "Content-Type: application/json" -d "{\"email\": \"$EMAIL\", \"password\": \"$PASSWORD\"}")
ACCESS_TOKEN=$(echo $AUTH_RESPONSE | jq -r '.access_token')

if [ -z "$ACCESS_TOKEN" ] || [ "$ACCESS_TOKEN" == "null" ]; then
    echo "Failed to retrieve access token"
    exit 1
fi

echo "Access token: $ACCESS_TOKEN"

# Step 2: Load test the specified endpoints using ApacheBench
for URL in "${LOAD_TEST_URLS[@]}"; do
    echo "Testing $URL ..."
    ab -n $REQUESTS -c $CONCURRENCY -H "Authorization: Bearer $ACCESS_TOKEN" "$URL"
    echo "---------------------------------------"
done
