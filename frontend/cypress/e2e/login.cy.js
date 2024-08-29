import testUser from '../fixtures/testUser.json';

describe('log in page', () => {
    it('can log a user in and out', () => {
        cy.visit('/')
        //login 
        cy.get('[data-cy="navButton"]').click()
        cy.get('[data-cy="LoginLink"]').click()
        cy.get('[data-cy="emailInput"]').type(testUser.email)
        cy.get('[data-cy="passwordInput"]').type(testUser.password)
        cy.get('[data-cy="submitButton"]').click()
        
        cy.url().should('eq', 'http://localhost:9001/#/accounts')

        cy.get('[data-cy="navButton"]').click()
        cy.get('[data-cy="RemittanceLink"]').contains('Transfer')

        //log out
        cy.get('[data-cy="LogOutLink"]').click()
        cy.url().should('eq', 'http://localhost:9001/#/login')
    })

    it('displays an error message when incorrect email is used', () => {
        cy.visit('/')
        cy.get('[data-cy="navButton"]').click()
        cy.get('[data-cy="LoginLink"]').click()
        cy.get('[data-cy="emailInput"]').type("invalid@email.com")
        cy.get('[data-cy="passwordInput"]').type(testUser.password)
        cy.get('[data-cy="submitButton"]').click()
        cy.get('[data-cy="errorMessage"]').should('contain', 'Incorrect email or password')
    })
})