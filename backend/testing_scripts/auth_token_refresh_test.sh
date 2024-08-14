#!/bin/bash

# Configuration
AUTH_URL="http://localhost:8080/api/v1/auth/authenticate"
REFRESH_URL="http://localhost:8080/api/v1/auth/refresh-token"
API_URL="http://localhost:8080/api/v1/transaction"
EMAIL="alexandermichael@mail.com"
PASSWORD="cake"
ACCESS_TOKEN=""
REFRESH_TOKEN=""
TOTAL_REQUESTS=6  # Total number of requests to send in the test
REQUEST_INTERVAL=600  # Interval between requests in seconds (10 minutes)

# Counters
successful_requests=0
unauthorized_requests=0
rate_limited_requests=0
service_unavailable_requests=0
other_requests=0
total_requests_sent=0
auth_calls=0
refresh_calls=0

# Function to authenticate and retrieve access and refresh tokens
authenticate() {
    AUTH_RESPONSE=$(curl -s -X POST "$AUTH_URL" -H "Content-Type: application/json" -d "{\"email\": \"$EMAIL\", \"password\": \"$PASSWORD\"}")
    ACCESS_TOKEN=$(echo $AUTH_RESPONSE | jq -r '.access_token')
    REFRESH_TOKEN=$(echo $AUTH_RESPONSE | jq -r '.refresh_token')

    if [ -z "$ACCESS_TOKEN" ] || [ "$ACCESS_TOKEN" == "null" ]; then
        echo "$(date +"%Y-%m-%d %H:%M:%S") - Failed to retrieve access token"
        exit 1
    fi

    auth_calls=$((auth_calls + 1))
}

# Function to refresh tokens using the refresh token
refresh_tokens() {
    REFRESH_RESPONSE=$(curl -s -X POST "$REFRESH_URL" -H "Authorization: Bearer $REFRESH_TOKEN")
    ACCESS_TOKEN=$(echo $REFRESH_RESPONSE | jq -r '.access_token')
    REFRESH_TOKEN=$(echo $REFRESH_RESPONSE | jq -r '.refresh_token')

    if [ -z "$ACCESS_TOKEN" ] || [ "$ACCESS_TOKEN" == "null" ]; then
        echo "$(date +"%Y-%m-%d %H:%M:%S") - Failed to refresh access token"
        exit 1
    fi

    refresh_calls=$((refresh_calls + 1))
}

# Function to display the progress bar
show_progress_bar() {
    local progress=$((($1 * 100) / $2))
    local done=$((($progress * 4) / 10))
    local left=$((40 - $done))
    local fill=$(printf "%${done}s")
    local empty=$(printf "%${left}s")
    printf "\rProgress: |${fill// /#}${empty// /-}| %3d%% (%d/%d)" $progress $1 $2
}

# Initial authentication and summary of test settings
authenticate
echo "Test Summary:"
echo "Start Time: $(date)"
echo "Total Requests to be Sent: $TOTAL_REQUESTS"
echo "Request Interval: $REQUEST_INTERVAL seconds"
echo "API Endpoint: $API_URL"
echo

# Main loop: send requests and track progress
for ((i=1; i<=TOTAL_REQUESTS; i++)); do
    total_requests_sent=$((total_requests_sent + 1))
    RESPONSE=$(curl -s -o response_${i}.txt -w "%{http_code}" -X GET "$API_URL" -H "Authorization: Bearer $ACCESS_TOKEN")
    RESPONSE_BODY=$(cat response_${i}.txt)

    if [[ "$RESPONSE" == "401" ]]; then
        unauthorized_requests=$((unauthorized_requests + 1))
        refresh_tokens
        RESPONSE=$(curl -s -o response_${i}.txt -w "%{http_code}" -X GET "$API_URL" -H "Authorization: Bearer $ACCESS_TOKEN")
        RESPONSE_BODY=$(cat response_${i}.txt)
    fi

    case "$RESPONSE" in
        200)
            successful_requests=$((successful_requests + 1))
            ;;
        429)
            rate_limited_requests=$((rate_limited_requests + 1))
            ;;
        503)
            service_unavailable_requests=$((service_unavailable_requests + 1))
            ;;
        *)
            other_requests=$((other_requests + 1))
            ;;
    esac

    # Show the progress bar
    show_progress_bar $i $TOTAL_REQUESTS

    # Wait for the next interval
    sleep $REQUEST_INTERVAL
done

# Summary of test results
echo
echo "Test Completed:"
echo "Total Requests Sent: $total_requests_sent"
echo "Successful Requests (200): $successful_requests"
echo "Unauthorized Requests (401): $unauthorized_requests"
echo "Rate-Limited Requests (429): $rate_limited_requests"
echo "Service Unavailable Requests (503): $service_unavailable_requests"
echo "Other Responses: $other_requests"
echo "Total Authentication API Calls: $auth_calls"
echo "Total Refresh Token API Calls: $refresh_calls"
echo "End Time: $(date)"
