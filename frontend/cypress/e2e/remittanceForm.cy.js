describe('remittance page', () => {
    it('can be found via /remittance, can edit form and click submit', () => {
        cy.loginTestUser()
        cy.get('[data-cy="navButton"]').click()
        cy.get('[data-cy="RemittanceLink"]').click()
        cy.get('[data-cy="fromInput"]').type("Vincent Xu")
        cy.get('[data-cy="toInput"]').type("Jan Philips")
        cy.get('[data-cy="amountInput"]').type("100")
        cy.get('[data-cy="submitTransfer"]').click()
    })
})