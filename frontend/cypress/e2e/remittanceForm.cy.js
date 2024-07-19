describe('remittance page', () => {
    it('can be found via /remittance, can edit form and click submit', () => {
        cy.loginTestUser()
        cy.get('[data-cy="navButton"]').click()
        cy.get('[data-cy="RemittanceLink"]').click()
        // need new tests for remittance because the page has changed
    })
})