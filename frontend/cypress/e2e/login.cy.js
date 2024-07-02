describe('log in page', () => {
    it('can be found via home page, and form accepts valid credentials', () => {
        cy.visit('/')
        cy.get('[data-cy="navButton"]').click()
        cy.get('[data-cy="LoginLink"]').click()
        cy.get('[data-cy="emailInput"]').type("cypress@test.com")
        cy.get('[data-cy="passwordInput"]').type("password123")
        cy.get('[data-cy="submitButton"]').click()
    })
})