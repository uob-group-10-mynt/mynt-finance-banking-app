import React, { useState } from "react";
import { 
  Flex, 
  Box, 
  Heading, 
  FormControl, 
  FormLabel, 
  Input, 
  Button 
} from "@chakra-ui/react";

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    //TODO hash password before posting it
    const handleSubmit = (e) => {
      e.preventDefault();
      const credentials = { email, password };
      //TODO send POST request
      fetch('URL FOR API ENDPOINT', {
        method: 'POST',
        headers: { "Content-type": "application/json" },
        body: JSON.stringify(credentials)
      }).then(()=> {
        console.log('Form submitted', credentials);
        setEmail('');
        setPassword('');
      })
    }

    return(
      <Flex width="full" align="center" justifyContent="center">
        <Box p={2} className="remittance-page">
          <Box textAlign="center">
            <Heading>Login</Heading>
          </Box>
          <Box my={4} textAlign="left">
            <form onSubmit={handleSubmit}>
              <FormControl>
                <FormLabel>Email</FormLabel>
                <Input 
                isRequired 
                type="email" 
                placeholder="hello@email.com" 
                value={email}
                onChange={(e) => setEmail(e.target.value)}/>
              </FormControl>

              <FormControl mt={6}>
                <FormLabel>Password</FormLabel>
                <Input 
                  isRequired 
                  type="password" 
                  placeholder="*******" 
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
              </FormControl>
              <Button width="full" mt={4} type="submit">
                Sign In 
              </Button>
            </form>
          </Box>
        </Box>
      </Flex>
    );
}

export default Login;