import { Box } from "@chakra-ui/react";

function TransactionCircle({ colour }) {
  return (
    <Box
      width="25%"    
      paddingTop="25%"   
      borderRadius="50%" 
      backgroundColor={colour}
      position="relative"
    />
  );
}

export default TransactionCircle;