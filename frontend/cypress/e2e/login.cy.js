describe('log in page', () => {
    it('can be found via home page, and form POSTs correct body', () => {
        cy.intercept('POST', '/api/login').as('submission')
        cy.visit('/')
        cy.get('[data-cy="LoginLink"]').click()
        cy.get('[data-cy="emailInput"]').type("cypress@test.com")
        cy.get('[data-cy="passwordInput"]').type("password123")
        cy.get('[data-cy="submitButton"]').click()
        
        cy.wait('@submission').then((interception) => {
            cy.inspectFormSubmission(interception, {
                email: "cypress@test.com",
                password: "password123"
            })
        })
    })
})