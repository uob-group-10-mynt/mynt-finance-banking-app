const baseURL = "http://localhost:8080/api/v1/"

export const authenticateAPI = baseURL + 'auth/authenticate'
export const validateKYCAPI = baseURL + 'auth/validateKyc'
export const onfidoIdetityCheckAPI = baseURL + 'auth/onfidoSdk'
export const getUserDetailsAPI = baseURL + 'users/getUserDetails'
export const updateUserDetailsAPI = baseURL + 'users/updateUserDetails'
export const refreshTokensAPI = baseURL + 'auth/refresh-token'