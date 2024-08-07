import { Box, Input } from "@chakra-ui/react";
import { ArrowUpDownIcon } from '@chakra-ui/icons';
import { useEffect, useState } from "react";
import { useNavigate, useLocation, Outlet } from "react-router-dom";

import useConversion from "../../hooks/useConversion";
import useQuery from "../../hooks/useQuery";

import CustomBox from "../../components/util/CustomBox";
import CustomText from "../../components/CustomText";
import CustomButton from "../../components/forms/CustomButton";
import Icon from "../../components/util/Icon";


const formatNumberWithCommas = (number) => {
  if (!number) return '';
  return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
};

const parseNumberFromString = (numberString) => {
  if (!numberString) return 0;
  return parseFloat(numberString.replace(/,/g, ''));
};

export default function ConversionPage() {
  const query = useQuery();
  const [ rates, setRates ] = useState(null);
  const [ baseValue, setBaseValue ] = useState(1);
  const [ compareValue, setCompareValue ] = useState(null);

  const { conversionRequest, setConversionRequest } = useConversion();
  const navigate = useNavigate();

  const base = query.get('base');
  const compare = query.get('compare');

  useEffect(() => {
    fetch(`http://localhost:8080/api/v1/rates/getBasicRates?other_currencies=${compare}`, {
      headers: { 'Authorization': `Bearer ${sessionStorage.getItem('access')}` }
    })
    .then(response => 
      response.json()
    )
    .then(data => {
      setBaseValue(data[0].rate);
      setCompareValue(data[1].rate);
    })
    .catch((e) => {
      console.log("ERROR: " + e);
    })
  }, []);
  
  

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
      base: base,
      compare: compare,
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
          {renderInputBox(baseValue, handleOnChangeBaseValue, base)}
        </CustomBox>
        <ArrowUpDownIcon />
        <CustomBox gap='0.7em' justifyContent='center' alignItems='center'>
          <CustomText small black>Recipient gets</CustomText>
          {renderInputBox(compareValue, handleOnChangeCompareValue, compare)}
        </CustomBox>
        <CustomButton standard marginTop='1em' onClick={handleNextOnClick}>Next</CustomButton>
      </CustomBox>
      <Outlet />
    </Box>
  );
}
