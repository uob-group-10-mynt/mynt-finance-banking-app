const CURRENCY_SYMBOLS = {
  'AUD': 'A$', // Added symbol for Australian Dollar
  'BHD': 'BD', // Added symbol for Bahrain Dinar
  'CAD': 'C$', // Added symbol for Canadian Dollar
  'CNY': '¥', // Added symbol for Chinese Yuan (Renminbi)
  'CZK': 'Kč', // Added symbol for Czech Koruna
  'DKK': 'kr', // Added symbol for Danish Krone
  'EUR': '€',  // Added symbol for European Euro
  'HKD': 'HK$', // Added symbol for Hong Kong Dollar
  'HUF': 'Ft', // Added symbol for Hungarian Forint
  'INR': '₹', // Added symbol for Indian Rupee
  'IDR': 'Rp', // Added symbol for Indonesian Rupee
  'ILS': '₪', // Added symbol for Israel Shekel
  'JPY': '¥', // Added symbol for Japanese Yen
  'KES': '/=', // Kenyan Shilling
  'KWD': 'KD', // Added symbol for Kuwait Dinar
  'MYR': 'RM', // Added symbol for Malaysian Ringgit
  'MXN': 'MX$', // Added symbol for Mexican Peso
  'NZD': 'NZ$', // Added symbol for New Zealand Dollar
  'NOK': 'kr', // Added symbol for Norwegian Krone
  'OMR': 'OMR', // Added symbol for Oman Rial
  'PHP': '₱', // Added symbol for Philippine Peso
  'PLN': 'zł', // Added symbol for Polish Zloty
  'QAR': 'QR', // Added symbol for Qatar Rial
  'RON': 'lei', // Added symbol for Romanian Leu
  'SAR': 'SR', // Added symbol for Saudi Riyal
  'SGD': 'S$', // Added symbol for Singapore Dollar
  'ZAR': 'R', // Added symbol for South African Rand
  'SEK': 'kr', // Added symbol for Swedish Krona
  'CHF': 'CHF', // Added symbol for Swiss Franc
  'THB': '฿', // Added symbol for Thai Baht
  'TRY': '₺', // Added symbol for Turkish Lira
  'UGX': 'USh', // Added symbol for Ugandan Shilling
  'GBP': '£', // UK Sterling
  'AED': 'د.إ', // Added symbol for UAE Dirham
  'USD': '$', // US Dollar
  '': '', // No symbol for empty currency code
};

// Function to get the symbol for a given currency code
export default function useCurrencySymbols(currencyCode) {
  return CURRENCY_SYMBOLS[currencyCode] || currencyCode;
}