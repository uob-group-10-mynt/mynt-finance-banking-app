import React, {useState} from 'react';
import {useLocation, useNavigate} from 'react-router-dom';
import CustomHeading from "../../components/CustomHeading";
import Container from "../../components/container/Container";
import Icon from "../../components/util/Icon";
import InfoBlock from "../../components/util/InfoBlock";
import CustomText from "../../components/CustomText";
import CustomForm from "../../components/forms/CustomForm";
import {Center} from "@chakra-ui/react";

export default function Amount() {
    const navigate = useNavigate();
    const [amount, setAmount] = useState('');
    const availableBalance = 1000; // To be replaced with logic to fetch balance dynamically from an API

    const location = useLocation();
    const selectedPayee = location.state.selectedPayee;
    const renderSelectedPayee = [selectedPayee].map((payee) => {
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
                    </>
                );
            },
            onClick: () => {
            },
        }
    });

    const amountInputFields = [
        {
            id: "transfer-amount",
            label: "Amount: ",
            display: "formattedNumber",
            placeholder: "0",
            type: "number",
            required: true,
            value: amount,
            onChange: (e) => setAmount(e.target.value),
            helperText: `Available balance: ${availableBalance.toLocaleString()} KES`,
            inputLeftElement: "£"
        },
    ];
    const [fields, setFields] = useState(amountInputFields)

    const handleAmountSubmit = () => {
        // Add validation and submission logic here
        selectedPayee['transfer_amount'] = amount;
        navigate('/remittance/transfer', {state: {selectedPayee: selectedPayee}});
    };

    return (
        <>
            <CustomHeading align='center'>How much would you like to send to your payee?</CustomHeading>
            <Center>
                <Container name='Selected Payee' data={renderSelectedPayee} keyFn={(info) => info.id}/>
            </Center>
            <CustomForm
                onSubmit={handleAmountSubmit} buttonText="Confirm" buttonId="amountButton" parentState={fields} setParentState={setFields}>
            </CustomForm>
        </>
    );
}
