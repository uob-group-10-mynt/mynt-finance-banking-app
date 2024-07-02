describe('log in page', () => {
    it('can be found via /login, form accepts valid credentials', () => {
        cy.visit('/login')

        cy.get('[data-cy="emailInput"]').type("cypress@test.com")
        cy.get('[data-cy="passwordInput"]').type("password123")
        cy.get('[data-cy="submitButton"]').click()
    })
})