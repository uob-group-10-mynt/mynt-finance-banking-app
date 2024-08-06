import {
    Card,
    CardHeader,
    CardBody,
    Heading,
    Box,
    Text

} from "@chakra-ui/react";
//TODO sticky header


export default function HomePage() {
    return(
        <Box 
        maxW={["100%", "100%", "90%", "1024px"]}
        justifyContent="center"
        alignItems="center"
        margin='auto'

        >
            <Card mt='3rem'>
                <CardHeader>
                    <Heading as='h1' >Kenya's first international neobank</Heading>
                </CardHeader>
                <CardBody>
                    <Box>
                        <Heading size='xs' textTransform='uppercase'>
                            Access all of our services from your device
                        </Heading>
                        <Text pt='2' fontSize='sm'>
                        Open an account today
                            (put link here)
                        </Text>
                    </Box>
                </CardBody>
            </Card>

            <Card mt='2rem'>
                <CardHeader>
                    <Heading as='h1' >Transfer domestically or internationally</Heading>
                </CardHeader>
                <CardBody>
                    <Heading as='h2' size='md'>All from the same account, using IBAN (more support to come)</Heading>
                </CardBody>
            </Card>

            <Card mt='2rem'>
                <CardHeader>
                    <Heading as='h1' >Choose from a vast array of currencies</Heading>
                </CardHeader>
                <CardBody>
                    <Heading as='h2' size='md'>Kenya's first international neobank</Heading>
                    <Box>
                        <Heading size='xs' textTransform='uppercase'>
                        Summary
                        </Heading>
                        <Text pt='2' fontSize='sm'>
                        View a summary of all your clients over the last month.
                        </Text>
                    </Box>
                </CardBody>
            </Card>
        </Box>
    )
}