import { 
    Center, 
    Heading, 
    Box 
} from "@chakra-ui/react";
import { MdDoneOutline } from "react-icons/md";

export const SuccessBlock = ({heading, subHeading}) => {
    return (
        <Box      
        display='flex' 
        flexDirection='column'
        borderRadius='lg' 
        width='35%'
        padding='1em'
        backgroundColor='white'
        justifyContent="center"
        alignItems="center"
        gap='1.3em'
        margin='auto'
        >
                <MdDoneOutline size={40}/>
                <Heading as='h1'size='xl'>{heading}</Heading>
                <Heading as='h2' size='sm'>{subHeading}</Heading>
        </Box>
    )
}