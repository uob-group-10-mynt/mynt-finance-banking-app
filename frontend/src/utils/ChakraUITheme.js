import { extendTheme } from '@chakra-ui/react'

const ChakraUITheme = extendTheme({
  config: {
      initialColorMode: 'light',
      useSystemColorMode: false,
  },
  colors: {
      // Define your light and dark mode colors here
      brand: {
          500: '#2ecc71', // Example brand color
      },
  },
  styles: {
      global: (props) => ({
          body: {
              bg: props.colorMode === 'dark' ? 'gray.800' : '#D9FFF0',
              color: props.colorMode === 'dark' ? '#D9FFF0' : 'gray.800',
          },
      }),
  },
});

export default ChakraUITheme;