import { useRef } from 'react';
import {
    Drawer,
    DrawerBody,
    DrawerHeader,
    DrawerOverlay,
    DrawerContent,
    DrawerCloseButton,
    Button,
    useDisclosure,
    VStack
} from '@chakra-ui/react'
import { GiHamburgerMenu } from "react-icons/gi";

function CustomDrawer({children, testId}) {
    const {isOpen, onOpen, onClose} = useDisclosure()
    const btnRef = useRef()

    return (
        <>
            <Button ref={btnRef} colorScheme='teal' onClick={onOpen} data-cy={testId}>
                <GiHamburgerMenu/>
            </Button>
            <Drawer
                isOpen={isOpen}
                placement='left'
                onClose={onClose}
                finalFocusRef={btnRef}
            >
                <DrawerOverlay/>
                <DrawerContent>
                    <DrawerCloseButton/>
                    <DrawerHeader>Navigation</DrawerHeader>
                    <DrawerBody>
                        <VStack spacing="3" onClick={onClose}>
                            {children}
                        </VStack>
                    </DrawerBody>
                </DrawerContent>

            </Drawer>
        </>
    )
}

export default CustomDrawer;