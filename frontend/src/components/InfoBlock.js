import { Box } from "@chakra-ui/react";

function InfoBlock({ children }) {
  const [ title , content ] = children;

  return (
    <Box width='45%'>
      <Box>{title}</Box>
      <Box>{content}</Box>
    </Box>
  );
}

export default InfoBlock;