import React from 'react';
import {
    Drawer,
    DrawerBody,
    DrawerHeader,
    DrawerOverlay,
    DrawerContent,
    DrawerCloseButton,
    Button,
    useDisclosure,
    VStack, Center,
} from '@chakra-ui/react'

function CustomDrawer({text, children}) {
    const {isOpen, onOpen, onClose} = useDisclosure()
    const btnRef = React.useRef()

    return (
        <>
            <Center>
                <Button ref={btnRef} colorScheme='teal' onClick={onOpen}>
                    {text}
                </Button>
            </Center>
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