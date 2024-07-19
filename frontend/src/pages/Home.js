import { Box } from "@chakra-ui/react";
import { useNavigate } from 'react-router-dom';

import useFormatAmount from '../hooks/useFormatAmount';
import CustomButton from "../components/forms/CustomButton";
import CustomText from "../components/CustomText";
import Icon from "../components/Icon";
import Container from "../components/Container";
import InfoBlock from "../components/InfoBlock";


const fetchAccountData = [
    {
        'id': '1',
        'account_reference': '66f51c98-1ef8-4e48-97de-aac0353ba2b4',
        'bank': 'mynt',
        'label': 'Mynt Dollar Account',
        'balance': '1010234.0',
        'currency': 'dollar',
        'currencySymbol': '$'
    },
    {
        'id': '2',
        'account_reference': '66f51c98-1ef8-4e48-97de-aac0353ba2b4',
        'bank': 'others',
        'label': 'Mynt Pound Account',
        'balance': '1000.0',
        'currency': 'pound',
        'currencySymbol': '£'
    },
];

export default function Home() {
    const navigate = useNavigate(); 

    const accountKeyFn = (info) => info.id;
    const conversionKeyFn = (info) => info.id;

    const handleSendOnClick = (e) => {
        e.stopPropagation();
        console.log("SEND BUTTON CLICKED");
    }

    const accountData = fetchAccountData.map((data) => {
        return {
            ...data,
            render: () => {
                return (
                    <>
                        <Icon name={data.bank} />
                        <InfoBlock>
                            <CustomText gray small>{data.label}</CustomText>
                            <CustomText black big>{useFormatAmount(data.balance, data.currencySymbol)}</CustomText>
                        </InfoBlock>
                        <CustomButton side onClick={(e) => handleSendOnClick(e)}>Send</CustomButton>
                        
                    </>
                );
            },

            onClick: () => {
                navigate('/accounts/' + data.id);
            },
        }
    });

    return (
        <Box
            display="flex"
            flexDirection='column'
            justifyContent="center"
            alignItems="center"
            gap='1.3em'
            margin='auto'
        >
            <Container name='Conversion' keyFn={conversionKeyFn} />
            <Container name='Accounts' data={accountData} keyFn={accountKeyFn} />
        </Box>
    );
}
