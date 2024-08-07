import {
    Card,
    CardHeader,
    CardBody,
    Heading,
    Box,
    Text,
    Container,
    Image
} from "@chakra-ui/react";
import CustomButton from "../components/forms/CustomButton";
import graphic from '../../public/images/mobile_banking612x612.jpg'
import { Link } from "react-router-dom";


export default function HomePage() {
    return(
    <Box 
        maxW={["100%", "100%", "90%", "1024px"]}
        justifyContent="center"
        alignItems="center"
        margin='auto'

        >
        <Container maxW="container.xl" py={20} textAlign="center">
            <Heading as="h1" size="2xl" mb={4}>
            Welcome to Kenya's first international neobank
            </Heading>
            <Text fontSize="xl" mb={6}>
            Modern banking, simplified. Manage your finances with ease.
            </Text>
            <Link to='/signup'>
                <CustomButton colorScheme="teal" size="lg" color='white'>Get Started</CustomButton>
            </Link>
            <Image src={graphic} alt="Banking Illustration" mt={10} mx="auto" />
        </Container>

        <Card mt='2rem' textAlign="center">
            <CardHeader>
                <Heading as='h1' >Transfer domestically or internationally</Heading>
            </CardHeader>
            <CardBody>
                <Heading as='h2' size='md'>All from the same account, using IBAN (more support to come)</Heading>
            </CardBody>
        </Card>

            <Card mt='2rem' textAlign="center">
                <CardHeader>
                    <Heading as='h1' >Choose from a vast array of currencies</Heading>
                </CardHeader>
                <CardBody>
                    <Heading as='h2' size='md'>KES, NGN, CNY, GBP, USB, and many more!</Heading>
                </CardBody>
            </Card>
    </Box>
    )
}