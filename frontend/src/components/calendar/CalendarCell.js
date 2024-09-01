import { Box } from "@chakra-ui/react";
import { useState } from "react";


export default function CalendarCell({ 
  children,
  ...rest 
}) {
  const [ isHovered, setIsHovered ] = useState(false);
  
  return (
    <Box
      bgColor={isHovered ? '#F3F3F3' : 'white'}
      height='4em' 
      width='3em'   
      transition={'background-color 0.3s'}
      textAlign='center'
      display='flex'
      flexDirection='column'
      alignItems='center'
      justifyContent='center'
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
      {...rest}
    >
      {children}
    </Box>
  );
}