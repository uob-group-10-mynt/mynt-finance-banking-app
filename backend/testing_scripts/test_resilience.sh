#!/bin/bash

# Configuration
AUTH_URL="http://localhost:8080/api/v1/auth/authenticate"
REFRESH_URL="http://localhost:8080/api/v1/auth/refresh-token"
API_URL="http://localhost:8080/api/v1/transaction"
EMAIL="alexandermichael@mail.com"
PASSWORD="cake"
ACCESS_TOKEN=""
REFRESH_TOKEN=""
#REQUEST_INTERVAL=5  # Interval between requests in seconds
#MAX_REQUESTS=10     # Maximum number of requests to simulate rate limiting

REQUEST_INTERVAL=0.1
MAX_REQUESTS=100

# Function to authenticate and retrieve access and refresh tokens
authenticate() {
    echo "$(date +"%Y-%m-%d %H:%M:%S") - Authenticating..."
    AUTH_RESPONSE=$(curl -s -X POST "$AUTH_URL" -H "Content-Type: application/json" -d "{\"email\": \"$EMAIL\", \"password\": \"$PASSWORD\"}")
    ACCESS_TOKEN=$(echo $AUTH_RESPONSE | jq -r '.access_token')
    REFRESH_TOKEN=$(echo $AUTH_RESPONSE | jq -r '.refresh_token')

    if [ -z "$ACCESS_TOKEN" ] || [ "$ACCESS_TOKEN" == "null" ]; then
        echo "$(date +"%Y-%m-%d %H:%M:%S") - Failed to retrieve access token"
        exit 1
    fi

    echo "$(date +"%Y-%m-%d %H:%M:%S") - Access token: $ACCESS_TOKEN"
    echo "$(date +"%Y-%m-%d %H:%M:%S") - Refresh token: $REFRESH_TOKEN"
}

# Function to refresh tokens using the refresh token
refresh_tokens() {
    echo "$(date +"%Y-%m-%d %H:%M:%S") - Refreshing tokens..."
    REFRESH_RESPONSE=$(curl -s -X POST "$REFRESH_URL" -H "Authorization: Bearer $REFRESH_TOKEN")
    ACCESS_TOKEN=$(echo $REFRESH_RESPONSE | jq -r '.access_token')
    REFRESH_TOKEN=$(echo $REFRESH_RESPONSE | jq -r '.refresh_token')

    if [ -z "$ACCESS_TOKEN" ] || [ "$ACCESS_TOKEN" == "null" ]; then
        echo "$(date +"%Y-%m-%d %H:%M:%S") - Failed to refresh access token"
        exit 1
    fi

    echo "$(date +"%Y-%m-%d %H:%M:%S") - New access token successfully retrieved"
    echo "$(date +"%Y-%m-%d %H:%M:%S") - New refresh token successfully retrieved"
}

# Initial authentication
authenticate

# Main loop: send requests at intervals to test rate limiting and retry logic
for ((i=1; i<=MAX_REQUESTS; i++)); do
    echo "$(date +"%Y-%m-%d %H:%M:%S") - Sending request #$i to $API_URL ..."
    RESPONSE=$(curl -s -X GET "$API_URL" -H "Authorization: Bearer $ACCESS_TOKEN")

    if [[ "$RESPONSE" == *"too_many_requests"* ]]; then
        echo "$(date +"%Y-%m-%d %H:%M:%S") - Rate limit exceeded. Response: $RESPONSE"
    elif [[ "$RESPONSE" == *"service_unavailable"* ]]; then
        echo "$(date +"%Y-%m-%d %H:%M:%S") - Service unavailable, triggering retry logic. Response: $RESPONSE"
    else
        echo "$(date +"%Y-%m-%d %H:%M:%S") - Request successful, response: $RESPONSE"
    fi

    # Wait for the next interval
    sleep $REQUEST_INTERVAL

    # Refresh tokens periodically
    if (( i % 3 == 0 )); then
        refresh_tokens
    fi
done

echo "$(date +"%Y-%m-%d %H:%M:%S") - Completed $MAX_REQUESTS requests. Script finished."
