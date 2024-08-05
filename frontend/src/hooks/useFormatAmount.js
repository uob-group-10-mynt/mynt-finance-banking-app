import useCurrencySymbols from '../hooks/useCurrencySymbols';

const useFormatAmount = (amount, currencyCode='KES') => {
  const currencySymbol = useCurrencySymbols(currencyCode);
  return new Intl.NumberFormat('en-US', { style: 'currency', currency: currencyCode }).format(amount).replace(currencyCode, currencySymbol);
};

export default useFormatAmount;