import {Tabs, TabList, TabPanels, TabPanel} from '@chakra-ui/react'
import CustomTab from "./CustomTab";

function TabBar({tabNames, tabPanels, ...props}) {
    return (
        <Tabs align='center' variant='soft-rounded' colorScheme='teal' m={2} {...props}>
            <TabList>
                {tabNames.map((tabName, index) => (
                    <CustomTab key={index}>{tabName}</CustomTab>
                ))}
            </TabList>
            <TabPanels>
                {tabPanels.map((tabPanel, index) => (
                    <TabPanel key={index}>{tabPanel}</TabPanel>
                ))}
            </TabPanels>
        </Tabs>
    );
}

export default TabBar;