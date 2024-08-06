export const POPULAR_CURRENCY_COUNTRY_MAP = {
  'KES': 'Kenya',
  'USD': 'United States',
  'GBP': 'United Kingdom',
  'EUR': 'European Union',
  'CAD': 'Canada',
  'JPY': 'Japan',
  'CNY': 'China',
};

export default function usePopularCountryName(currency) {
  return POPULAR_CURRENCY_COUNTRY_MAP[currency] || 'Unknown Country';
}