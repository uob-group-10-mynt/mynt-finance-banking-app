import React from 'react';
import TabBar from "../../components/TabBar";
import CustomHeading from "../../components/CustomHeading";
import AddPayeePanel from "./AddPayeePanel";

function Payee() {
    const tabs = ['Recent payees', 'My payees', 'New payee']

    const panels = ['a', 'b', <AddPayeePanel/>];

    return (
        <>
            <CustomHeading>Where would you like to send the money?</CustomHeading>
            <TabBar tabNames={tabs} tabPanels={panels}></TabBar>
        </>
    );
}

export default Payee;
