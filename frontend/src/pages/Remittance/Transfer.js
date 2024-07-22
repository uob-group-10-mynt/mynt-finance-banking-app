import Icon from "../../components/util/Icon";
import InfoBlock from "../../components/util/InfoBlock";
import CustomText from "../../components/CustomText";
import {useLocation, useNavigate} from "react-router-dom";
import CustomHeading from "../../components/CustomHeading";
import Container from "../../components/container/Container";
import CustomButton from "../../components/forms/CustomButton";
import {Box, useToast} from "@chakra-ui/react";

export default function Transfer() {
    const navigate = useNavigate();
    const toast = useToast();
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
                            <CustomText gray
                                        small>{payee.label + ' + £' + parseFloat(payee.transfer_amount).toFixed(2)}</CustomText>
                            <CustomText black big>{payee.account_number}</CustomText>
                        </InfoBlock>
                    </>
                );
            },
        }
    });

    const handleConfirm = () => {
        // Add validation and submission logic here
        toast({
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
        <Box
            display="flex"
            flexDirection='column'
            justifyContent="center"
            alignItems="center"
            gap='1.3em'
            margin='auto'
        >
            <CustomHeading>Confirm your payment details:</CustomHeading>
            <Container name='Selected Payee & Transfer Amount' data={renderSelectedPayee} keyFn={(info) => info.id}/>
            <CustomButton standard onClick={handleConfirm}>Confirm Payment</CustomButton>
        </Box>
    );
}
