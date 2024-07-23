import { Box } from "@chakra-ui/react";
import { CheckIcon } from '@chakra-ui/icons';
import { useState } from "react";

import Container from "../../components/container/Container";
import Icon from "../../components/util/Icon";
import CustomText from "../../components/CustomText";
import InfoBlock from "../../components/util/InfoBlock";
import SearchBar from "../../components/SearchBar";
import CustomButton from "../../components/forms/CustomButton";
import useAllCountryName, { ALL_CURRENCY_COUNTRY_MAP } from "../../hooks/useAllCountryName";
import usePopularCountryName, { POPULAR_CURRENCY_COUNTRY_MAP } from "../../hooks/usePopularCountryName";


const ALL_CURRENCIES = Object.keys(ALL_CURRENCY_COUNTRY_MAP);

export default function ConversionListPage({ 
  onClose, 
  selectedCurrency, 
  setSelectedCurrency, 
  setBaseCurrency, 
  setCompareCurrency, 
  isBaseCurrency 
}) {
  const [ searchTerm, setSearchTerm ] = useState("");

  const filterData = (data) => {
    const lowercasedSearchTerm = searchTerm.toLowerCase();
    return data.filter(({ currency }) => {
      const country = useAllCountryName(currency);

      return currency.toLowerCase().includes(lowercasedSearchTerm) ||
             country.toLowerCase().includes(lowercasedSearchTerm);
    });
  };

  const conversionKeyFn = (currency) => currency.currency;

  const popularCurrencyList = Object.keys(POPULAR_CURRENCY_COUNTRY_MAP).map((currency) => {
    const country = usePopularCountryName(currency);
    return {
      currency,
      render: () => (
        <>
          <Icon name={currency} currency />
          <InfoBlock>
            <CustomText black>{country}</CustomText> 
            <CustomText medium>{currency}</CustomText> 
          </InfoBlock>
          <Box width='10%' height='10%'>
            {(selectedCurrency === currency) 
            ? <CheckIcon color='blue' />
            : null}
          </Box>
        </>
      ),
      onClick: () => {
        if (isBaseCurrency) {
          setBaseCurrency(currency);
          setSelectedCurrency(currency);
        } else {
          setCompareCurrency(currency);
          setSelectedCurrency(currency);
        }
      },
    }
  });

  const allCurrencyList = ALL_CURRENCIES.map((currency) => {
    const country = useAllCountryName(currency);
    return {
      currency,
      render: () => (
        <>
          <Icon name={currency} currency />
          <InfoBlock>
            <CustomText black>{country}</CustomText>
            <CustomText medium>{currency}</CustomText>
          </InfoBlock>
          <Box width='10%' height='10%'>
            {(selectedCurrency === currency) 
            ? <CheckIcon color='blue' />
            : null}
          </Box>
        </>
      ),
      onClick: () => {
        if (isBaseCurrency) {
          setBaseCurrency(currency);
          setSelectedCurrency(currency);
        } else {
          setCompareCurrency(currency);
          setSelectedCurrency(currency);
        }
      },
    }
  });

  const filteredAllCurrencyList = filterData(allCurrencyList);

  const showFilteredResults = searchTerm.length > 0;

  return (
    <Box
      display="flex"
      flexDirection='column'
      justifyContent="center"
      alignItems="center"
      gap='1.3em'
      margin='auto'
    >
      <SearchBar
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        placeholder='Search by country or currency code'
      />
      {showFilteredResults ? (
        <Container
          name='Search Results'
          data={filteredAllCurrencyList}
          keyFn={conversionKeyFn}
        />
      ) : (
        <>
          <Container
            name='Popular Countries'
            data={popularCurrencyList}
            keyFn={conversionKeyFn}
          />
          <Container
            name='All'
            data={allCurrencyList}
            keyFn={conversionKeyFn}
          />
        </>
      )}
      <CustomButton standard onClick={onClose}>Confirm</CustomButton>
    </Box>
  );
}
