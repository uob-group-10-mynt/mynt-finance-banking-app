import Icon from "../../components/util/Icon";
import InfoBlock from "../../components/util/InfoBlock";
import CustomText from "../../components/CustomText";
import {useLocation, useNavigate} from "react-router-dom";
import CustomHeading from "../../components/CustomHeading";
import Container from "../../components/container/Container";
import {useToast, VStack} from "@chakra-ui/react";
import CustomButton from "../../components/forms/CustomButton";
import {createPayment} from "../../utils/APIEndpoints";
import {useState} from "react";

export default function Transfer() {
    const navigate = useNavigate();
    const toast = useToast();
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [response, setResponse] = useState('')

    const location = useLocation();
    const selectedCurrencyAccount = location.state.selectedCurrencyAccount;
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
                            <CustomText black big>{payee.beneficiary_id}</CustomText>
                            <CustomText gray small>{`${payee.currency} ${payee.account_number}`}</CustomText>
                            <CustomText black medium>
                                {`+${Intl.NumberFormat("en-GB").format(payee.transfer_amount)} ${payee.currency} `}
                            </CustomText>
                        </InfoBlock>
                    </>
                );
            },
        }
    });

    async function handleConfirm() {
        try {
            const response = await fetch(createPayment, {
                method: 'POST',
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${sessionStorage.getItem('access')}`
                },
                body: JSON.stringify({
                    "beneficiary_id": selectedPayee.id,
                    "currency": selectedCurrencyAccount.currency,
                    "amount": selectedPayee.transfer_amount,
                })
            });

            if (!response.ok) {
                const error = new Error(await response.text());
                setError(error);
                setLoading(false);
                return;
            }

            setResponse(await response.json());
        } catch (error) {
            setError(error);
        } finally {
            setLoading(false);
        }

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
            }, 10000);
        }
    }

    return (
        <VStack>
            <CustomHeading>Confirm your payment details:</CustomHeading>
            <Container name='Selected Payee & Transfer Amount' data={renderSelectedPayee}
                       keyFn={(info) => info.id}/>
            <CustomButton confirm onClick={handleConfirm} data-cy='confirmPayment'>
                Confirm Payment
            </CustomButton>
            {loading && <CustomText>Processing...</CustomText>}
            {error && <CustomText>Error {error.message}</CustomText>}
        </VStack>
    );
}
