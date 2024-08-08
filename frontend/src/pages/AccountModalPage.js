import { Box, List, ListItem, Text } from '@chakra-ui/react';
import { ChevronDownIcon } from '@chakra-ui/icons';
import { useState } from 'react';

import { ALL_CURRENCY_COUNTRY_MAP } from '../hooks/useAllCountryName';
import useCurrencyDescription from '../hooks/useCurrencyDescription'; 

import Icon from '../components/util/Icon'; 
import CustomButton from '../components/forms/CustomButton';

export default function AccountModalPage({ onClose, selectedCurrency, setSelectedCurrency }) {
  const [ isDropdownOpen, setIsDropdownOpen ] = useState(false);

  const countryOptions = Object.entries(ALL_CURRENCY_COUNTRY_MAP).map(([currency, country]) => (
    <ListItem
      key={currency}
      onClick={() => handleCountrySelect(currency)}
      padding="0.5em"
      cursor="pointer"
      _hover={{ bg: 'gray.100' }} 
    >
      <Box display="flex" alignItems="center">
        <Icon name={currency} currency style={{ marginRight: '8px' }} />
        <Box>
          <Text fontWeight="bold">{country}</Text>
          <Text fontSize="sm">{useCurrencyDescription(currency)}</Text>
        </Box>
      </Box>
    </ListItem>
  ));

  const handleDropdownToggle = () => {
    setIsDropdownOpen(!isDropdownOpen);
  };

  const handleCountrySelect = (currency) => {
    setSelectedCurrency(currency);
    setIsDropdownOpen(false);
  };

  const handleButtonOnClick = (e) => {
    setSelectedCurrency(e.target.value);    
    onClose();
  }

  return (
    <Box position="relative" width="250px">
      <Box
        onClick={handleDropdownToggle}
        padding="0.5em"
        border="1px"
        borderColor="gray.300"
        borderRadius="md"
        cursor="pointer"
        display="flex"
        alignItems="center"
        justifyContent="space-between"
      >
        <Text>
          {selectedCurrency ? ALL_CURRENCY_COUNTRY_MAP[selectedCurrency] : 'Select a country'}
        </Text>
        <ChevronDownIcon boxSize={6} />
      </Box>
      {isDropdownOpen && (
        <Box
          border="1px"
          borderColor="gray.300"
          borderRadius="md"
          mt="0.5em"
          maxHeight="300px" 
          overflowY="auto"
          position="absolute"
          bg="white"
          width="100%" 
          zIndex={10} 
        >
          <List spacing={1}>
            {countryOptions}
          </List>
        </Box>
      )}
      <Box mt="2em"> 
        <CustomButton standard onClick={handleButtonOnClick}>
          Select
        </CustomButton>
      </Box>
    </Box>
  );
}
