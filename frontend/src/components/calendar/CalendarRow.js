import { Box } from "@chakra-ui/react";

function CalendarRow({
  children,
  ...rest
}) {
  return (
    <Box 
      display='flex'
      flexDirection='row'
      alignItems='center'
      textAlign='center'
      justifyContent='space-around'
      {...rest}
    >
      {children}
    </Box>
  );
}

export default CalendarRow;