import React from 'react';
import TabBar from "../../components/TabBar";
import CustomHeading from "../../components/CustomHeading";

function Payee() {
    const tabs = ['Recent payees', 'My payees', 'New payees']
    const panels = ['a', 'b', 'c']

    return (
        <>
            <CustomHeading>Where would you like to send the money?</CustomHeading>
            <TabBar tabNames={tabs} tabPanels={panels}></TabBar>
        </>
    );
}

export default Payee;
