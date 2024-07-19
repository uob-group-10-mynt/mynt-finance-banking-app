import { Box } from "@chakra-ui/react";

import CustomText from "../CustomText";


function DateTimeDisplay({ time }) {
  return (
    <Box display='flex' flexDirection='column-reverse'>
      <CustomText gray small>{time.split('T')[0]}</CustomText>
      <CustomText gray big>{time.split('T')[1].split('+')[0].substring(0, 5)}</CustomText>
    </Box>
  ); 
}

export default DateTimeDisplay;