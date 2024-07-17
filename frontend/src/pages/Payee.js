import React from 'react';
import TabBar from "../components/TabBar";
import CustomHeading from "../components/CustomHeading";
import {Box} from "@chakra-ui/react";
import Page from "../components/Page";

function Payee() {
    const tabs = ['Recent payees', 'My payees', 'New payees']
    const panels = ['a','b','c']

    return (
        <Page>
            <CustomHeading>Where would you like to send the money?</CustomHeading>
            <TabBar tabNames={tabs} tabPanels={panels}></TabBar>
        </Page>
    );
}

export default Payee;
