import { Box } from "@chakra-ui/react";

import CustomText from "./CustomText";


function DateTimeDisplay({ time }) {
  return (
    <Box display='flex' flexDirection='column-reverse'>
      <CustomText gray xsmall>{time.split('T')[0]}</CustomText>
      <CustomText gray small>{time.split('T')[1].split('+')[0]}</CustomText>
    </Box>
  ); 
}

export default DateTimeDisplay;