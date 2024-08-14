import { Box, Input, useToast } from "@chakra-ui/react";
import { ArrowUpDownIcon, PhoneIcon, AtSignIcon } from '@chakra-ui/icons';
import { useEffect, useState } from "react";

import CustomBox from "../components/util/CustomBox";
import CustomText from "../components/CustomText";
import CustomButton from "../components/forms/CustomButton";
import Icon from "../components/util/Icon";

const formatNumberWithCommas = (number) => {
  if (number === null || number === undefined) return '';
  return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
};

const parseNumberFromString = (numberString) => {
  if (!numberString) return 0;
  return parseFloat(numberString.replace(/,/g, ''));
};

export default function ConversionModalPage({ onClose, currency }) {
  const [rates, setRates] = useState(null);
  const [baseValue, setBaseValue] = useState('');
  const [compareValue, setCompareValue] = useState('');
  const [mobile, setMobile] = useState('');
  const [beneficiaryName, setBeneficiaryName] = useState("");

  const toast = useToast();

  useEffect(() => {
    fetch(`http://localhost:8080/api/v1/rates/getBasicRates?sell_currency=${currency}&buy_currencies=KES`, {
      headers: { 'Authorization': `Bearer ${sessionStorage.getItem('access')}` }
    })
    .then(response => response.json())
    .then(data => {
      setBaseValue('1');
      setCompareValue(formatNumberWithCommas(data[1].rate));
      setRates(data[1].rate);
    });
  }, [currency]);

  const handleOnChangeBaseValue = (e) => {
    const value = parseNumberFromString(e.target.value);
    setBaseValue(formatNumberWithCommas(value));
    setCompareValue(formatNumberWithCommas(value * rates));
  };

  const handleOnChangeCompareValue = (e) => {
    const value = parseNumberFromString(e.target.value);
    setCompareValue(formatNumberWithCommas(value));
    setBaseValue(formatNumberWithCommas(value / rates));
  };

  const handleConfirmOnClick = () => {
    fetch('http://localhost:8080/api/v1/flutterwave/cloudCurrency2Mpesa', {
      method: "POST",
      headers: {
        'Authorization': `Bearer ${sessionStorage.getItem('access')}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ 
        amount: parseNumberFromString(compareValue),
        mobile_number: mobile,
        beneficiary_name: beneficiaryName,
      })
    }).then(() => {
      toast({
        position: 'top',
        title: 'Conversion Complete.',
        description: "You've successfully made a withdrawal.",
        status: 'success',
        duration: 5000,
        isClosable: true,
      });
    })
    .finally(() => {
      onClose();
    });
  };

  const renderInputBox = (value, onChange, label, type = 'text', icon) => (
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
      <Box width='60%'>
        <CustomText small black>{label}</CustomText>
        <Input
          type={type}
          variant='outline'
          border='none'
          borderRadius='full'
          fontWeight='bold'
          fontSize={[ '0.8em', '0.9em', '1.5em' ]}
          size='md'
          _focus={{ 
            borderColor: 'transparent', 
            boxShadow: 'none',
          }}
          min={0}
          value={value}
          onChange={onChange}
        />
      </Box>
      {icon && (
        <Box
          display='flex'
          flexDirection='row'
          justifyContent='center'
          alignItems='center'
          gap='0.3em'
        >
          {icon}
        </Box>
      )}
    </Box>
  );

  return (
    currency !== 'KES' ? 
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
          <CustomText small black>You Withdraw Exactly</CustomText>
          {renderInputBox(baseValue, handleOnChangeBaseValue, 'Amount', 'text', <Icon name={currency} boxSize={[ '1.5em', '3em' ]} currency />)}
        </CustomBox>
        <ArrowUpDownIcon />
        <CustomBox gap='0.7em' justifyContent='center' alignItems='center'>
          <CustomText small black>Equivalent to</CustomText>
          {renderInputBox(compareValue, handleOnChangeCompareValue, 'Amount', 'text', <Icon name="KES" boxSize={[ '1.5em', '3em' ]} currency />)}
        </CustomBox>
        <CustomBox gap='0.7em' justifyContent='center' alignItems='center'>
          <CustomText small black>Mobile Number</CustomText>
          {renderInputBox(mobile, (e) => setMobile(e.target.value), 'Mobile Number', 'text', <PhoneIcon />)}
          <CustomText small black>Beneficiary Name</CustomText>
          {renderInputBox(beneficiaryName, (e) => setBeneficiaryName(e.target.value), 'Beneficiary Name', 'text', <AtSignIcon />)}
        </CustomBox>
        <CustomButton standard marginTop='1em' onClick={handleConfirmOnClick}>Confirm</CustomButton>
      </CustomBox>
    </Box>
    :
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
          <CustomText small black>You Withdraw Exactly</CustomText>
          {renderInputBox(baseValue, handleOnChangeBaseValue, 'Amount', 'text', <Icon name={currency} boxSize={[ '1.5em', '3em' ]} currency />)}
          <CustomText small black>Mobile Number</CustomText>
          {renderInputBox(mobile, (e) => setMobile(e.target.value), 'Mobile Number', 'text', <PhoneIcon />)}
          <CustomText small black>Beneficiary Name</CustomText>
          {renderInputBox(beneficiaryName, (e) => setBeneficiaryName(e.target.value), 'Beneficiary Name', 'text', <AtSignIcon />)}
        </CustomBox>
        <CustomButton confirm onClick={handleConfirmOnClick}>Confirm</CustomButton>
      </CustomBox>
    </Box>
  );
}
