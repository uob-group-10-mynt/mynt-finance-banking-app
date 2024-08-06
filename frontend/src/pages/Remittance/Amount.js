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

    const location = useLocation();
    const selectedCurrencyAccount = location.state.selectedCurrencyAccount;
    const availableBalance = selectedCurrencyAccount.balance;
    const selectedPayee = location.state.selectedPayee;
    const renderSelectedPayee = [selectedPayee].map((payee) => {
        return {
            ...payee,
            render: () => {
                return (
                    <>
                        <Icon name={payee.bank_name}/>
                        <InfoBlock>
                            <CustomText black big>{payee.name}</CustomText>
                            <CustomText gray small>{payee.currency + " " + payee.account_number}</CustomText>
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
            helperText: `Available balance: ${selectedCurrencyAccount.currency} ${availableBalance.toLocaleString()}`,
            inputLeftElement: selectedPayee.currency
        },
    ];
    const [fields, setFields] = useState(amountInputFields)

    const handleAmountSubmit = () => {
        // Add validation and submission logic here
        selectedPayee['transfer_amount'] = fields[0].value;
        navigate('/remittance/transfer', {state: {selectedPayee: selectedPayee}});
    };

    return (
        <>
            <CustomHeading align='center'>How much would you like to send to your payee?</CustomHeading>
            <Center>
                <Container name='Selected Payee' data={renderSelectedPayee} keyFn={(info) => info.id}/>
            </Center>
            <CustomForm
                onSubmit={handleAmountSubmit} buttonText="Confirm" buttonId="amountButton" parentState={fields}
                setParentState={setFields}>
            </CustomForm>
        </>
    );

}
