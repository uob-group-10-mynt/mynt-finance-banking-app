import { Box, useToast } from "@chakra-ui/react";
import { useNavigate } from "react-router-dom";

import useConversion from "../../hooks/useConversion";

import CustomBox from "../../components/util/CustomBox";
import CustomText from "../../components/CustomText";
import Icon from "../../components/util/Icon";
import CustomButton from "../../components/forms/CustomButton";

export default function ConversionConfirmPage() {
  const { conversionRequest } = useConversion();
  const { base, compare, amount, convertedAmount } = conversionRequest;

  const navigate = useNavigate();
  const toast = useToast();

  const handleOnClick = () => {
    toast({
      position: 'top',
      title: 'Conversion Complete.',
      description: "You've successfully made a conversion.",
      status: 'success',
      duration: 5000,
      isClosable: true,
    });

    navigate('/', { replace: true });
  };

  return (
    <Box
      display="flex"
      flexDirection="column"
      justifyContent="center"
      alignItems="center"
    >
      <CustomBox
        display="flex"
        flexDirection="column"
        alignItems="center"
        gap='1em'
      >
        <CustomText small black>
          Selected Currencies & Exchange Amount
        </CustomText>
        <Box
          display="flex"
          flexDirection="row"
          justifyContent="space-between"
          alignItems="center"
          width="100%"
        >
          <Box flex="1" textAlign="center">
            <CustomText medium>Convert</CustomText>
          </Box>
          <Box
            display="flex"
            flexDirection="column"
            alignItems="center"
            flex="2"
          >
            <Icon name={base} currency />
            <CustomText black big>
              {amount} {base}
            </CustomText>
          </Box>
        </Box>
        <Box
          display="flex"
          flexDirection="row"
          justifyContent="space-between"
          alignItems="center"
          width="100%"
        >
          <Box flex="1" textAlign="center">
            <CustomText medium>To</CustomText>
          </Box>
          <Box
            display="flex"
            flexDirection="column"
            alignItems="center"
            flex="2"
          >
            <Icon name={compare} currency />
            <CustomText black big>
              {convertedAmount} {compare}
            </CustomText>
          </Box>
        </Box>
        <CustomButton confirm onClick={handleOnClick}>
          Confirm
        </CustomButton>
      </CustomBox>
    </Box>
  );
}
