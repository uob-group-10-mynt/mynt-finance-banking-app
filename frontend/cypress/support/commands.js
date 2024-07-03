/// <reference types="Cypress" />
import testUser from '../fixtures/testUser.json';

Cypress.Commands.add('createTestUser', () => {
    const authEndPoint = 'http://127.0.0.1:8080/api/v1/auth/register';
    const accountDetails = {
        firstname: testUser.firstName,
        lastname: testUser.lastname,
        password: testUser.password,
        email: testUser.email,
        role: testUser.role
    };

    cy.request({
        method: 'POST',
        url: authEndPoint,
        headers: {
            'Content-Type': 'application/json'
        },
        body: accountDetails,
        failOnStatusCode: false // This ensures that the test doesn't fail if the user already exists
    }).then((response) => {
        if (response.status !== 201) {
            cy.log('Test user might already exist or there was another issue');
        }
    });
})