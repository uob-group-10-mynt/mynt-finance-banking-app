import React from 'react';
import {useNavigate} from 'react-router-dom';
import TabBar from "../../components/TabBar";
import CustomHeading from "../../components/CustomHeading";
import AddPayeePanel from "./AddPayeePanel";
import Icon from "../../components/util/Icon";
import InfoBlock from "../../components/util/InfoBlock";
import CustomText from "../../components/CustomText";
import CustomButton from "../../components/forms/CustomButton";
import Container from "../../components/container/Container";

function Payee() {
    const tabs = ['Recent payees', 'My payees', 'New payee']
    const panels = [<MyPayeesPanel/>, <MyPayeesPanel/>, <AddPayeePanel/>];

    return (
        <>
            <CustomHeading align='center'>Where would you like to send the money?</CustomHeading>
            <TabBar tabNames={tabs} tabPanels={panels}></TabBar>
        </>
    );
}

function MyPayeesPanel() {
    const navigate = useNavigate();
    // const fetchPayees = await fetch(getPayees, {
    //     method: 'GET',
    //     headers: {"Content-type": "application/json"},
    // });
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
        <Container data={renderPayees} keyFn={(info) => info.id}/>
    );
}

export default Payee;
