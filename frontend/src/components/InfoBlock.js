import { Box } from "@chakra-ui/react";

function InfoBlock({ children }) {
  const [ title , content ] = children;

  return (
    <Box
      display='flex'
      flexDirection='column'
      justifyContent='flex-start' 
      width='45%'
    >
      {children}
    </Box>
  );
}

export default InfoBlock;