const baseURL = "http://localhost:8080/api/v1/"

export const authenticateAPI = baseURL + 'auth/authenticate'
export const validateKYCAPI = baseURL + 'auth/validateKyc'
export const onfidoIdetityCheckAPI = baseURL + 'auth/onfidoSdk'
export const addBeneficiaries = baseURL + 'currency-cloud/beneficiaries/add'
export const getBeneficiaries = baseURL + 'currency-cloud/beneficiaries/find'
export const createPayment = baseURL + 'currency-cloud/payments/create'
export const getUserDetailsAPI = baseURL + 'users/getUserDetails'
export const updateUserDetailsAPI = baseURL + 'users/updateUserDetails'
