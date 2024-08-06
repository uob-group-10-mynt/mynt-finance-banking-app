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
import {getBeneficiaries} from "../../utils/APIEndpoints";

export default function Payee() {
    const tabs = ['My payees', 'New payee']
    const panels = [<MyPayeesPanel/>, <AddPayeePanel/>];
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

function MyPayeesPanel() {
    const [payees, setPayees] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
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
                                <CustomText gray small>{payee.currency + " " + payee.account_number}</CustomText>
                            </InfoBlock>
                            <CustomButton side onClick={() => {
                                navigate('/remittance/amount', {
                                    state: {
                                        selectedPayee: payee,
                                        selectedCurrencyAccount: selectedCurrencyAccount
                                    }
                                })
                            }}>Send</CustomButton>
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
    }
}
