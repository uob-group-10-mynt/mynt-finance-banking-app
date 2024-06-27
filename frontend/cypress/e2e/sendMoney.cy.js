describe('Sign Up Page', () => {
    it('can be found via end point /remittance, and form accepts valid credentials', () => {
        cy.visit('/remittance')
        
        cy.get('[data-cy="fromInput"]').type("James")

        cy.get('[data-cy="toInput"]').type("Jan")
        
        cy.get('[data-cy="amountInput"]').type("1000")
        
        cy.get('[class="chakra-button css-7ftacb"]').click()
    })
})