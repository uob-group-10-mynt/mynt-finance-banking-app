import logo from '../images/mynt_fake_logo.png';
import { Image, Spinner, Text } from '@chakra-ui/react'

function SplashPage() {
  return (
    <div style={{
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      justifyContent: 'space-between',
      gap: '2em'
    }}>
      <Image 
        src={logo}
        size='xs'
      />
      <Spinner
          thickness="4px"
          speed="0.65s"
          color="teal"
          boxSize='100px'
      />
      <Text fontSize='xs'>Loading .... Please Wait</Text>
    </div>
  );
}

export default SplashPage;