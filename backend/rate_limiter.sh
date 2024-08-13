#!/bin/bash

# Configuration
AUTH_URL="http://localhost:8080/api/v1/auth/authenticate"
API_URL="http://localhost:8080/api/v1/transaction"
EMAIL="alexandermichael@mail.com"
PASSWORD="cake"
ACCESS_TOKEN=""

# Function to authenticate and retrieve the access token
authenticate() {
    echo "$(date +"%Y-%m-%d %H:%M:%S") - Authenticating..."
    AUTH_RESPONSE=$(curl -s -X POST "$AUTH_URL" -H "Content-Type: application/json" -d "{\"email\": \"$EMAIL\", \"password\": \"$PASSWORD\"}")
    ACCESS_TOKEN=$(echo $AUTH_RESPONSE | jq -r '.access_token')

    if [ -z "$ACCESS_TOKEN" ] || [ "$ACCESS_TOKEN" == "null" ]; then
        echo "$(date +"%Y-%m-%d %H:%M:%S") - Failed to retrieve access token"
        exit 1
    fi

    echo "$(date +"%Y-%m-%d %H:%M:%S") - Access token: $ACCESS_TOKEN"
}

# Initial authentication
authenticate

# Send the first request
echo "$(date +"%Y-%m-%d %H:%M:%S") - Sending first request to $API_URL ..."
RESPONSE_1=$(curl -s -X GET "$API_URL" -H "Authorization: Bearer $ACCESS_TOKEN")

if [[ "$RESPONSE_1" == *"too_many_requests"* ]]; then
    echo "$(date +"%Y-%m-%d %H:%M:%S") - First request: Rate limit exceeded"
else
    echo "$(date +"%Y-%m-%d %H:%M:%S") - First request: Success, response: $RESPONSE_1"
fi

# Send the second request
echo "$(date +"%Y-%m-%d %H:%M:%S") - Sending second request to $API_URL ..."
RESPONSE_2=$(curl -s -X GET "$API_URL" -H "Authorization: Bearer $ACCESS_TOKEN")

if [[ "$RESPONSE_2" == *"too_many_requests"* ]]; then
    echo "$(date +"%Y-%m-%d %H:%M:%S") - Second request: Rate limit exceeded"
else
    echo "$(date +"%Y-%m-%d %H:%M:%S") - Second request: Success, response: $RESPONSE_2"
fi

echo "$(date +"%Y-%m-%d %H:%M:%S") - Test completed."
