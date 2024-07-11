import {Tab} from '@chakra-ui/react'

function CustomTab({children}) {
    return (
        <Tab _hover={{bg: '#ebedf0'}}>{children}</Tab>
    );
}

export default CustomTab;