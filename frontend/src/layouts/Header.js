import {Box} from '@chakra-ui/react';
import CustomHeading from "../components/CustomHeading";

export default function Header({children}) {
    return (
        <Box>
            <CustomHeading>
                {children}
            </CustomHeading>
        </Box>
    );
}