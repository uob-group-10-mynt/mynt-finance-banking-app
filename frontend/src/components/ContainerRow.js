import { useState } from "react";
import { Box } from "@chakra-ui/react";

function ContainerRow({ info }) {
  const [isHovered, setIsHovered] = useState(false);

  return (
    <Box 
      display='flex'
      flexDirection='row'
      alignItems="center"
      justifyContent="space-around"
      backgroundColor={isHovered ? '#f0f0f0' : 'transparent'}
      transition={'background-color 0.3s'}
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
      onClick={info.onClick}
    >
      {info.render(info)}
    </Box>
  );
}

export default ContainerRow;