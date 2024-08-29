import { refreshTokensAPI } from "./APIEndpoints";

export class TokenExpiredError extends Error {
    constructor(message) {
        super(message);
        this.name = 'RefreshTokenExpired';
    }
}

const makeRequest = async (method, endpointURL, requiresAuth, additionalHeaders = {}, requestBody = null) => {
    try {
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

        const handleResponse = async (response) => {
            if (!response.ok) {
                if (requiresAuth && response.status === 401) {
                    const tokenResponse = await fetch(refreshTokensAPI, {
                        method: 'POST',
                        headers: {
                            "Content-type": "application/json",
                            Authorization: `Bearer ${sessionStorage.getItem('refresh')}`
                        }
                    });
                    
                    if (!tokenResponse.ok) {
                        throw new TokenExpiredError('Failed to refresh tokens');
                    }
                    
                    const newTokens = await tokenResponse.json();
                    sessionStorage.setItem('access', newTokens.access_token);
                    sessionStorage.setItem('refresh', newTokens.refresh_token);

                    const retryResponse = await fetch(endpointURL, {
                        method,
                        headers: {
                            ...additionalHeaders,
                            "Content-type": "application/json",
                            Authorization: `Bearer ${sessionStorage.getItem('access')}`
                        },
                        body: requestBody ? JSON.stringify(requestBody) : null
                    });

                    if (!retryResponse.ok) {
                        throw new Error('Retry request failed');
                    }
                    return retryResponse;
                } else {
                    const errorDetails = await response.text();
                    throw new Error(`Request failed: ${response.status} ${response.statusText} - ${errorDetails}`);
                }
            }
            return response;
        };
        const finalResponse = await handleResponse(response);

        const contentType = finalResponse.headers.get('content-type');
        if (contentType && contentType.includes('application/json')) {
            return await finalResponse.json();
        } else {
            return await finalResponse.text();
        }

    } catch (error) {
        if (error instanceof TokenExpiredError) {
            throw new TokenExpiredError("Failed to get new refresh token")
        }
        console.error('Error making request:', error);
        throw new Error('Error making request: ' + error.message);
    }
};

export default makeRequest;
