import React, {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import TabBar from "../../components/TabBar";
import CustomHeading from "../../components/CustomHeading";
import AddPayeePanel from "./AddPayeePanel";
import Icon from "../../components/util/Icon";
import InfoBlock from "../../components/util/InfoBlock";
import CustomText from "../../components/CustomText";
import CustomButton from "../../components/forms/CustomButton";
import Container from "../../components/container/Container";
import {getBeneficiaries} from "../../utils/APIEndpoints";

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
    const [payees, setPayees] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    async function fetchPayees() {
        try {
            // GET request to fetch payees for the specific payer
            const response = await fetch(getBeneficiaries, {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${sessionStorage.getItem('access')}`
                }
            });

            if (!response.ok) {
                const error = new Error(response.status + " : Can't retrieve your payees info!");
                setError(error);
                setLoading(false);
                return;
            }

            // Parse the JSON from the response
            const payees = await response.json();

            setPayees(payees);
        } catch (error) {
            setError(error);
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        fetchPayees();
    }, []);

    if (loading) {
        return <CustomText>Loading...</CustomText>;
    }

    if (error) {
        return <CustomText>Error {error.message}</CustomText>;
    }

    // placeholder received payees data
    // const fetchPayees = [
    //     {
    //         'id': '1',
    //         'bank': 'HSBC',
    //         'account_number': '00000000',
    //         'label': 'Jan Phillips',
    //     },
    //     {
    //         'id': '2',
    //         'bank': 'Citi',
    //         'account_number': '01010101',
    //         'label': 'Gunho Ryu',
    //     },
    // ];

    const renderPayees = payees.map((payee) => {
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
