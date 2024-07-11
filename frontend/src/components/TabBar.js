import {Tabs, TabList, TabPanels, TabPanel} from '@chakra-ui/react'
import CustomTab from "./CustomTab";

function TabBar({tabNames, tabPanels, ...props}) {
    return (
        <Tabs variant='soft-rounded' colorScheme='teal' m={2} {...props}>
            <TabList>
                {tabNames.map((tabName) => (
                    <CustomTab>{tabName}</CustomTab>
                ))}
            </TabList>
            <TabPanels>
                {tabPanels.map((tabPanel) => (
                    <TabPanel>{tabPanel}</TabPanel>
                ))}
            </TabPanels>
        </Tabs>
    );
}

export default TabBar;