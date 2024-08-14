
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

Cypress.Commands.add('loginTestUser', () => {
    cy.visit('login')
    cy.get('[data-cy="navButton"]').click()
    cy.get('[data-cy="LoginLink"]').click()
    cy.get('[data-cy="emailInput"]').type(testUser.email)
    cy.get('[data-cy="passwordInput"]').type(testUser.password)
    cy.get('[data-cy="submitButton"]').click()
})