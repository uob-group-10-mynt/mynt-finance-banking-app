import { Box } from "@chakra-ui/react";

function CustomBox({ 
  children, 
  ...rest
}) {
  return (
    <Box
      display='flex' 
      flexDirection='column'
      borderRadius='lg' 
      width='85%'
      padding='1em'
      backgroundColor='white'
      {...rest}
    >
      {children}
    </Box>
  );
}

export default CustomBox;