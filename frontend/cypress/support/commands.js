/// <reference types="Cypress" />
import testUser from '../fixtures/testUser.json';

Cypress.Commands.add('createTestUser', () => {
    const authEndPoint = 'http://localhost:8080/api/v1/auth/register'
    const accountDetails = {
        "firstname": testUser.firstName,
        "lastname": testUser.lastname,
        "password": testUser.password,
        "email": testUser.email,
        "role": testUser.role
    }
    try {
        fetch(authEndPoint, {
            method: 'POST',
            headers: { "Content-type": "application/json" },
            body: JSON.stringify(accountDetails)
        })
    } catch(error) {
        console.info("test user already exists")
    }
})