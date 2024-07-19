import React, {useState} from 'react';
import {useLocation, useNavigate} from 'react-router-dom';
import CustomHeading from "../../components/CustomHeading";
import Container from "../../components/container/Container";
import Icon from "../../components/util/Icon";
import InfoBlock from "../../components/util/InfoBlock";
import CustomText from "../../components/CustomText";
import CustomForm from "../../components/forms/CustomForm";
import {Box} from "@chakra-ui/react";

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
            label: "Amount",
            placeholder: "0",
            type: "number",
            required: true,
            value: amount,
            onChange: (e) => setAmount(e.target.value),
            helperText: `Available balance: ${availableBalance.toFixed(2)} KES`
        },
    ];

    const handleAmountSubmit = (event) => {
        event.preventDefault();

        // Add validation and submission logic here
        selectedPayee['transfer_amount'] = amount;
        navigate('/remittance/transfer', {state: {selectedPayee: selectedPayee}});

        // Reset form fields after submission
        // setAmount('');
    };

    return (
        <Box
            display="flex"
            flexDirection='column'
            justifyContent="center"
            alignItems="center"
            gap='1.3em'
            margin='auto'
        >
            <CustomHeading>How much would you like to send to your payee?</CustomHeading>
            <Container name='Selected Payee' data={renderSelectedPayee} keyFn={(info) => info.id}/>
            <CustomForm onSubmit={handleAmountSubmit} buttonText="Confirm" buttonId="amountButton">
                {amountInputFields}
            </CustomForm>
        </Box>
    );
}
