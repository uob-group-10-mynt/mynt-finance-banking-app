import { Box } from "@chakra-ui/react";

export default function CalendarRow({
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