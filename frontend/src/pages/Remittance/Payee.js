import React, {useEffect, useState} from 'react';
import {useLocation, useNavigate} from 'react-router-dom';
import TabBar from "../../components/TabBar";
import CustomHeading from "../../components/CustomHeading";
import AddPayeePanel from "./AddPayeePanel";
import Icon from "../../components/util/Icon";
import InfoBlock from "../../components/util/InfoBlock";
import CustomText from "../../components/CustomText";
import CustomButton from "../../components/forms/CustomButton";
import Container from "../../components/container/Container";
import {createMynt2MyntPayment, getBeneficiaries} from "../../utils/APIEndpoints";
import {useToast} from "@chakra-ui/react";
import CustomForm from "../../components/forms/CustomForm";

export default function Payee() {
    const tabs = ['My payees', 'New payee', 'Mynt to Mynt']
    const panels = [<MyPayeesPanel/>, <AddPayeePanel/>, <Mynt2MyntPanel/>];
    const location = useLocation();
    const selectedCurrencyAccount = location.state.selectedCurrencyAccount;

    return (
        <>
            <CustomHeading align='center'>Where would you like to send
                your {selectedCurrencyAccount.currency}?</CustomHeading>
            <TabBar tabNames={tabs} tabPanels={panels}></TabBar>
        </>
    );
}

function Mynt2MyntPanel() {
    const toast = useToast();
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const myntTransferInputFields = [
        {
            id: "email",
            label: "Email",
            placeholder: "Enter mynt account email of your payee",
            type: "email",
            required: true,
            value: ""
        },
        {
            id: "currency",
            label: "Currency",
            placeholder: "Enter the currency your payee will receive",
            type: "text",
            required: true,
            value: ""
        },
        {
            id: "amount",
            label: "Amount",
            display: "formattedNumber",
            placeholder: "Enter transfer amount",
            type: "number",
            required: true,
            value: ""
        },
    ];
    const [formData, setFormData] = useState(myntTransferInputFields)

    return (
        <>
            <CustomForm onSubmit={handleMyntTransfer} buttonText="Confirm Transfer" buttonId="myntTransferButton"
                        parentState={formData} setParentState={setFormData}>
            </CustomForm>
            {loading && <CustomText>Processing...</CustomText>}
            {error && <CustomText>Error {error.message}</CustomText>}
        </>
    );

    async function handleMyntTransfer(formValuesJSON) {
        setLoading(true);
        const m2mQuery = createMynt2MyntPayment + "?email=" + formValuesJSON.email + "&currency=" + formValuesJSON.currency + "&amount=" + formValuesJSON.amount;
        try {
            // POST request to add a payee
            const response = await fetch(m2mQuery, {
                method: 'POST',
                headers: {
                    Authorization: `Bearer ${sessionStorage.getItem('access')}`,
                    // "Content-Type": "application/json"
                },
                // body: JSON.stringify({formValuesJSON})
            });

            if (response.ok) {
                toast({
                    position: 'top',
                    title: 'Transfer made.',
                    description: "You've successfully made a transfer.",
                    status: 'success',
                    duration: 5000,
                    isClosable: true,
                });

                setTimeout(() => {
                    navigate('/');
                }, 3000);
                return;
            }

            if (!response.ok) {
                const error = new Error(await response.text());
                setError(error);
                setLoading(false);
            }
        } catch (error) {
            setError(error);
        } finally {
            setLoading(false);
        }
    }
}

function MyPayeesPanel() {
    const [payees, setPayees] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const toast = useToast();
    const navigate = useNavigate();
    const location = useLocation();
    const selectedCurrencyAccount = location.state.selectedCurrencyAccount;

    useEffect(() => {
        fetchPayees();
    }, []);
    if (loading) return <CustomText>Loading...</CustomText>;
    if (error) return <CustomText>Error {error.message}</CustomText>;

    return (
        <Container data={renderPayees(payees)} keyFn={(info) => info.id}/>
    );

    async function fetchPayees() {
        try {
            const response = await fetch(getBeneficiaries, {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${sessionStorage.getItem('access')}`
                },
            });

            if (!response.ok) {
                const error = new Error(await response.text());
                setError(error);
                setLoading(false);
                return;
            }

            // Parse the JSON from the response
            const data = await response.json(); // Await the promise to get the parsed JSON data
            setPayees(data.beneficiaries);
        } catch (error) {
            setError(error);
        } finally {
            setLoading(false);
        }
    }

    function renderPayees(payees) {
        return payees.map(payee => {
            return {
                ...payee,
                render: () => {
                    return (
                        <>
                            <Icon name={payee.bank_name}/>
                            <InfoBlock>
                                <CustomText black big>{payee.name}</CustomText>
                                <CustomText gray xsmall>{"Currency: " + payee.currency}</CustomText>
                                <CustomText gray xsmall>{"IBAN: " + payee.iban}</CustomText>
                            </InfoBlock>
                            <CustomButton side onClick={() => {
                                navigate('/remittance/amount', {
                                    state: {
                                        selectedPayee: payee,
                                        selectedCurrencyAccount: selectedCurrencyAccount
                                    }
                                })
                            }}>Send</CustomButton>
                            <CustomButton side onClick={() => {
                                handleDeletePayee(payee.id);
                            }}>Delete</CustomButton>
                        </>
                    );
                },
                onClick: () => navigate('/remittance/amount', {
                    state: {
                        selectedPayee: payee,
                        selectedCurrencyAccount: selectedCurrencyAccount
                    }
                }),
            };
        });

        async function handleDeletePayee(id) {
            try {
                const response = await fetch(`${getBeneficiaries}/${id}`, {
                    method: 'DELETE',
                    headers: {
                        Authorization: `Bearer ${sessionStorage.getItem('access')}`
                    },
                });

                if (response.ok) {
                    navigate(0);
                    toast({
                        position: 'top',
                        title: 'Payee deleted.',
                        description: "You've successfully removed a payee.",
                        status: 'success',
                        duration: 5000,
                        isClosable: true,
                    });
                }

                if (!response.ok) {
                    const error = new Error(await response.text());
                    setError(error);
                    setLoading(false);
                }
            } catch (error) {
                setError(error);
            } finally {
                setLoading(false);
            }
        }
    }
}
