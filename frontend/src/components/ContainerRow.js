import { Box } from "@chakra-ui/react";

function ContainerRow({ info }) {
  console.log(info);
  return (
    <Box 
      display='flex'
      flexDirection='row'
      alignItems="center"
      justifyContent="space-around"
    >
      {info.render(info)}
    </Box>
  );
}

export default ContainerRow;