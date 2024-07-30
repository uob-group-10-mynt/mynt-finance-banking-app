import { Box } from "@chakra-ui/react";

function ContainerRowBalanceWrapper({ children }) {
  return (
    <Box minWidth='35%' textAlign="right">
      {children}
    </Box>
  );
}

export default ContainerRowBalanceWrapper