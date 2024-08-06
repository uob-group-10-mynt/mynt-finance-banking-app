import React, {useEffect, useState} from 'react';
import {useLocation, useNavigate} from 'react-router-dom';
import CustomHeading from "../../components/CustomHeading";
import Container from "../../components/container/Container";
import Icon from "../../components/util/Icon";
import InfoBlock from "../../components/util/InfoBlock";
import CustomText from "../../components/CustomText";
import CustomForm from "../../components/forms/CustomForm";
import {Center} from "@chakra-ui/react";
import {getBalance} from "../../utils/APIEndpoints";

export default function Amount() {
    const navigate = useNavigate();
    const [amount, setAmount] = useState('');
    const [balance, setBalance] = useState('')
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

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

    useEffect(() => {
        fetchBalance();
    }, []);
    if (loading) return <CustomText>Loading...</CustomText>;
    if (error) return <CustomText>Error {error.message}</CustomText>;

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
            helperText: `Available balance: ${balance.toLocaleString()} KES`,
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

    async function fetchBalance() {
        try {
            const response = await fetch(getBalance, {
                method: 'POST',
                headers: {
                    Authorization: `Bearer ${sessionStorage.getItem('access')}`
                }
            });

            if (!response.ok) {
                const error = new Error(await response.text());
                setError(error);
                setLoading(false);
                return;
            }

            // Parse the JSON from the response
            const balance = await response.json();
            setBalance(balance);
        } catch (error) {
            setError(error);
        } finally {
            setLoading(false);
        }
    }

}
