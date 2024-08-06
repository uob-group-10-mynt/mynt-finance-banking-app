import Icon from "../../components/util/Icon";
import InfoBlock from "../../components/util/InfoBlock";
import CustomText from "../../components/CustomText";
import {useLocation, useNavigate} from "react-router-dom";
import CustomHeading from "../../components/CustomHeading";
import Container from "../../components/container/Container";
import {useToast, VStack} from "@chakra-ui/react";
import CustomButton from "../../components/forms/CustomButton";

export default function Transfer() {
    const navigate = useNavigate();
    const toast = useToast();
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
                            <CustomText gray small>{payee.currency + " " + payee.account_number}</CustomText>
                            <CustomText black medium>
                                {`+${selectedCurrencyAccount.currency} ${parseFloat(payee.transfer_amount).toFixed(2).toLocaleString()}`}
                            </CustomText>
                        </InfoBlock>
                    </>
                );
            },
        }
    });

    const handleConfirm = () => {
        // Add validation and submission logic here
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
        }, 2000);
    };

    return (
        <VStack>
            <CustomHeading>Confirm your payment details:</CustomHeading>
            <Container name='Selected Payee & Transfer Amount' data={renderSelectedPayee}
                       keyFn={(info) => info.id}/>
            <CustomButton confirm onClick={handleConfirm} data-cy='confirmPayment'>
                Confirm Payment
            </CustomButton>
        </VStack>
    );
}
