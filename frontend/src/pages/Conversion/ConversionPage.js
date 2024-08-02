import { Box, Input } from "@chakra-ui/react";
import { ArrowUpDownIcon } from '@chakra-ui/icons';
import { useState } from "react";
import { useNavigate, Outlet } from "react-router-dom";

import useConversion from "../../hooks/useConversion";

import CustomBox from "../../components/util/CustomBox";
import CustomText from "../../components/CustomText";
import CustomButton from "../../components/forms/CustomButton";
import Icon from "../../components/util/Icon";


const FETCH_CONVERSION_DATA = {
  rates: '1.54',
  buy_currency: 'USD',
  sell_currency: 'KES',
  settlement_date: "2024-07-13T14:13:18+00:00",
};

const formatNumberWithCommas = (number) => {
  if (!number) return '';
  return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
};

const parseNumberFromString = (numberString) => {
  if (!numberString) return 0;
  return parseFloat(numberString.replace(/,/g, ''));
};

export default function ConversionPage() {
  const [ rates, setRates ] = useState(parseFloat(FETCH_CONVERSION_DATA.rates));
  const [ baseValue, setBaseValue ] = useState(1);
  const [ compareValue, setCompareValue ] = useState(1 * rates);

  const { conversionRequest, setConversionRequest } = useConversion();
  const navigate = useNavigate();

  const handleOnChangeBaseValue = (e) => {
    const value = parseNumberFromString(e.target.value);
    setBaseValue(value);
    setCompareValue(value * rates);
  };

  const handleOnChangeCompareValue = (e) => {
    const value = parseNumberFromString(e.target.value);
    setCompareValue(value);
    setBaseValue(value / rates);
  };

  const handleNextOnClick = () => {
    setConversionRequest({
      ...conversionRequest,
      base: FETCH_CONVERSION_DATA.sell_currency,
      compare: FETCH_CONVERSION_DATA.buy_currency,
      amount: baseValue,
      convertedAmount: compareValue,
    });

    navigate('confirm');
  };

  const renderInputBox = (value, onChange, currency) => (
    <Box
      display='flex'
      flexDirection='row'
      justifyContent='space-around'
      alignItems='center'
      borderWidth="1px"
      borderColor="black.300"
      borderRadius="md"
      padding="0.5em"
      width={[ '100%', '60%' ]}
    >
      <Input
        width='60%' 
        height='20%'
        type='text'
        variant='outline'
        border='none' 
        borderRadius='full'
        fontWeight='bold'
        fontSize={[ '0.8em', '0.9em', '1.5em' ]}
        size='md'
        _input={{ 
          fontWeight: 'bold',
          fontSize: [ '0.8em', '0.9em', '1.5em' ], 
        }}
        _focus={{ 
          borderColor: 'transparent', 
          boxShadow: 'none',
        }}
        min={0}
        value={formatNumberWithCommas(value)} 
        onChange={onChange}
      />
      <Box
        display='flex'
        flexDirection='row'
        justifyContent='center'
        alignItems='center'
        gap='0.3em'
      >
        <Icon name={currency} boxSize={[ '1.5em', '3em' ]} currency />
        <CustomText medium black>
          {currency}
        </CustomText>
      </Box>
    </Box>
  );

  return (
    <Box
      display="flex"
      flexDirection='column'
      justifyContent="center"
      alignItems="center"
      gap='1.3em'
      margin='auto'
    >
      <CustomBox justifyContent='center' alignItems='center'>
        <CustomBox gap='0.7em' justifyContent='center' alignItems='center'>
          <CustomText small black>You send exactly</CustomText>
          {renderInputBox(baseValue, handleOnChangeBaseValue, FETCH_CONVERSION_DATA.sell_currency)}
        </CustomBox>
        <ArrowUpDownIcon />
        <CustomBox gap='0.7em' justifyContent='center' alignItems='center'>
          <CustomText small black>Recipient gets</CustomText>
          {renderInputBox(compareValue, handleOnChangeCompareValue, FETCH_CONVERSION_DATA.buy_currency)}
        </CustomBox>
        <CustomButton standard marginTop='1em' onClick={handleNextOnClick}>Next</CustomButton>
      </CustomBox>
      <Outlet />
    </Box>
  );
}
