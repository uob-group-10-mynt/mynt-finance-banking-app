describe('Sign Up Page', () => {
    it('can be found via /signup, and form accepts valid credentials', () => {
        cy.visit('/signup')
        
        cy.get('[data-cy="firstnameInput"]').type("James")

        cy.get('[data-cy="lastnameInput"]').type("Love")
        
        cy.get('[data-cy="emailInput"]').type("james@jameslove.com")
        
        cy.get('[data-cy="passwordInput"]').type("password123")

        cy.get('[data-cy="confirmPasswordInput"]').type("password123")
        
        cy.get('[data-cy="submitButton"]').click()
    })
})