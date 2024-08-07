import { Box, Input } from "@chakra-ui/react";
import { useState } from "react";

import CustomBox from "../components/util/CustomBox";
import CustomText from "../components/CustomText";
import CustomButton from "../components/forms/CustomButton";
import Icon from "../components/util/Icon";


const formatNumberWithCommas = (number) => {
  if (!number) return '';
  return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
};

const parseNumberFromString = (numberString) => {
  if (!numberString) return 0;
  return parseFloat(numberString.replace(/,/g, ''));
};


export default function DepositPage({ onClose }) {
  const [ value, setValue ] = useState(1);

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
      width={[ '100%', '80%' ]}
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

  const handleOnChangeBaseValue = (e) => {
    const inputValue = parseNumberFromString(e.target.value);
    setValue(inputValue);
  };

  const handleConfirmOnClick = () => {
    fetch('http://localhost:8080/api/v1/flutterwave/sendMpesaToCurrencyCloud', {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${sessionStorage.getItem('access')}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ amount: value.toString() })
    })
    .then(response => 
      response.json()
    )
    .then(() => {
      onClose();
    })
  };

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
          <CustomText small black>You deposit</CustomText>
          {renderInputBox(value, handleOnChangeBaseValue, 'KES')}
        </CustomBox>
        <CustomButton confirm onClick={handleConfirmOnClick}>confirm</CustomButton>
      </CustomBox>
    </Box>
  );
}