const useFormatAmount = (amount, currencySymbol) => {
  return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(amount).replace('$', currencySymbol);
};

export default useFormatAmount;