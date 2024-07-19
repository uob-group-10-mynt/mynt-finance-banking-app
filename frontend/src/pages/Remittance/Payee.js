import React from 'react';
import {useNavigate} from 'react-router-dom';
import TabBar from "../../components/TabBar";
import CustomHeading from "../../components/CustomHeading";
import AddPayeePanel from "./AddPayeePanel";
import Icon from "../../components/Icon";
import InfoBlock from "../../components/InfoBlock";
import CustomText from "../../components/CustomText";
import CustomButton from "../../components/forms/CustomButton";
import Container from "../../components/Container";
import {Box} from "@chakra-ui/react";

function Payee() {
    const tabs = ['Recent payees', 'My payees', 'New payee']
    const panels = [<MyPayeesPanel/>, <MyPayeesPanel/>, <AddPayeePanel/>];

    return (
        <>
            <CustomHeading>Where would you like to send the money?</CustomHeading>
            <TabBar tabNames={tabs} tabPanels={panels}></TabBar>
        </>
    );
}

function MyPayeesPanel() {
    const navigate = useNavigate();
    const fetchPayees = [
        {
            'id': '1',
            'payee_reference': '66f51c98-1ef8-4e48-97de-aac0353ba2b4',
            'bank': 'HSBC',
            'account_number': '00000000',
            'label': 'Jan Phillips',
        },
        {
            'id': '2',
            'payee_reference': '66f51c98-1ef8-4e48-97de-aac0353ba2b4',
            'bank': 'Citi',
            'account_number': '01010101',
            'label': 'Gunho Ryu',
        },
    ];

    const renderPayees = fetchPayees.map((payee) => {
        return {
            ...payee,
            render: () => {
                return (
                    <>
                        <Icon name={payee.bank}/>
                        <InfoBlock>
                            <CustomText gray small>{payee.label}</CustomText>
                            <CustomText black big>{payee.account_number}</CustomText>
                        </InfoBlock>
                        <CustomButton side>Send</CustomButton>
                    </>
                );
            },

            onClick: () => {
                navigate('/remittance/amount', {state: {selectedPayee: payee}});
            },
        }
    });

    return (
        <Box
            display="flex"
            flexDirection='column'
            justifyContent="center"
            alignItems="center"
            gap='1.3em'
            margin='auto'
        >
            <Container name='Payees' data={renderPayees} keyFn={(info) => info.id}/>
        </Box>
    );
}

export default Payee;
