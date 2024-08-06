import { refreshTokensAPI } from "./APIEndpoints";

const makeRequest = async (method, endpointURL, requiresAuth, additionalHeaders = {}, requestBody = null) => {
    try {
        // Initial request setup
        const headers = {
            ...additionalHeaders,
            "Content-type": "application/json",
            ...(requiresAuth && { Authorization: `Bearer ${sessionStorage.getItem('access')}` })
        };

        const response = await fetch(endpointURL, {
            method,
            headers,
            body: requestBody ? JSON.stringify(requestBody) : null
        });

        // If response is not okay, handle potential token refresh
        if (!response.ok) {
            if (requiresAuth && response.status === 401) {
                // Attempt to refresh tokens
                const tokenResponse = await fetch(refreshTokensAPI, {
                    method: 'POST',
                    headers: {
                        "Content-type": "application/json",
                        Authorization: `Bearer ${sessionStorage.getItem('refresh')}`
                    }
                });

                if (!tokenResponse.ok) {
                    throw new Error('Failed to refresh tokens');
                }

                const newTokens = await tokenResponse.json();
                sessionStorage.setItem('access', newTokens.access_token);
                sessionStorage.setItem('refresh', newTokens.refresh_token);

                // Retry original request with new tokens
                const retryResponse = await fetch(endpointURL, {
                    method,
                    headers: {
                        ...additionalHeaders,
                        "Content-type": "application/json",
                        Authorization: `Bearer ${newTokens.access_token}`
                    },
                    body: requestBody ? JSON.stringify(requestBody) : null
                });

                if (!retryResponse.ok) {
                    throw new Error('Retry request failed');
                }

                return await retryResponse.json();
            } else {
                const errorDetails = await response.text();
                throw new Error(`Request failed: ${response.status} ${response.statusText} - ${errorDetails}`);
            }
        }

        return await response.json();
    } catch (error) {
        console.error('Error making request:', error);
        throw new Error('Error making request: ' + error.message);
    }
};

export default makeRequest;
