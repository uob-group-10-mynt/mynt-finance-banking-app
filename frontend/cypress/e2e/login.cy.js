import testUser from '../fixtures/testUser.json';

describe('log in page', () => {
    it('user can log in and out', () => {
        cy.visit('/')
        cy.get('[data-cy="navButton"]').click()
        cy.get('[data-cy="LoginLink"]').click()
        cy.get('[data-cy="emailInput"]').type(testUser.email)
        cy.get('[data-cy="passwordInput"]').type(testUser.password)
        cy.get('[data-cy="submitButton"]').click()
        cy.url().should('eq', 'http://localhost:9001/')
        cy.get('[data-cy="navButton"]').click()
        cy.get('[data-cy="LogOutLink"]').click()
        cy.url().should('eq', 'http://localhost:9001/login')
    })
})