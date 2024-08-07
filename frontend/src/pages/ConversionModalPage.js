import { Box, Input } from "@chakra-ui/react";
import { ArrowUpDownIcon, PhoneIcon, AtSignIcon } from '@chakra-ui/icons';
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import useConversion from "../hooks/useConversion";
import useQuery from "../hooks/useQuery";

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

const fetchRates = async (currency, setBaseValue, setCompareValue) => {
  try {
    const response = await fetch(`http://localhost:8080/api/v1/rates/getBasicRates?base_currency=${currency}&other_currencies=KES`, {
      headers: { 'Authorization': `Bearer ${sessionStorage.getItem('access')}` }
    });
    const data = await response.json();
    setBaseValue(data[0].rate);
    setCompareValue(data[1].rate);
  } catch (e) {
    console.error("ERROR: " + e);
  }
};

export default function ConversionModalPage({ onClose, currency }) {
  const query = useQuery();
  const [ rates, setRates ] = useState(parseFloat(null));
  const [ baseValue, setBaseValue ] = useState(null);
  const [ compareValue, setCompareValue ] = useState(null);
  const [ mobile, setMobile ] = useState(null);
  const [ beneficiaryName, setBeneficiaryName ] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    fetchRates(currency, setBaseValue, setCompareValue);
  }, [currency]);

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

  const handleConfirmOnClick = () => {
    fetch('http://localhost:8080/api/v1/flutterwave/cloudCurrency2Mpesa', {
      method: "POST",
      headers: {
        'Authorization': `Bearer ${sessionStorage.getItem('access')}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ 
        amount: compareValue,
        mobile_number: mobile,
        beneficiary_name: beneficiaryName,
       })
    })

    navigate('confirm');
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
      width={[ '100%', '60%' ]}
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
          value={type === 'number' ? formatNumberWithCommas(value) : value}
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
          {renderInputBox(baseValue, handleOnChangeBaseValue, 'Amount', 'number', <Icon name={currency} boxSize={[ '1.5em', '3em' ]} currency />)}
        </CustomBox>
        <ArrowUpDownIcon />
        <CustomBox gap='0.7em' justifyContent='center' alignItems='center'>
          <CustomText small black>Equivalent to</CustomText>
          {renderInputBox(compareValue, handleOnChangeCompareValue, 'Amount', 'number', <Icon name="KES" boxSize={[ '1.5em', '3em' ]} currency />)}
        </CustomBox>
        <CustomBox gap='0.7em' justifyContent='center' alignItems='center'>
          <CustomText small black>Mobile Number</CustomText>
          {renderInputBox(null, setMobile, 'Mobile Number', 'text', <PhoneIcon />)}
          <CustomText small black>Beneficiary Name</CustomText>
          {renderInputBox(null, setBeneficiaryName, 'Beneficiary Name', 'text', <AtSignIcon />)}
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
          {renderInputBox(baseValue, handleOnChangeBaseValue, 'Amount', 'number', <Icon name={currency} boxSize={[ '1.5em', '3em' ]} currency />)}
          <CustomText small black>Mobile Number</CustomText>
          {renderInputBox(null, setMobile, 'Mobile Number', 'text', <PhoneIcon />)}
          <CustomText small black>Beneficiary Name</CustomText>
          {renderInputBox(null, setBeneficiaryName, 'Beneficiary Name', 'text', <AtSignIcon />)}
        </CustomBox>
        <CustomButton confirm onClick={handleConfirmOnClick}>Confirm</CustomButton>
      </CustomBox>
    </Box>
  );
}
