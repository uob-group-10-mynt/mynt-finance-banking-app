describe('user must be logged in to access remittance page', () => {
    it("redirected to '/login' when not logged in and try to go to '/remittance'", () => {
        cy.visit('/remittance')
        cy.url().should('eq', 'http://localhost:9001/login')
    })

    it("can access '/remittance' when logged in", () => {
        cy.loginTestUser()
        cy.url().should('eq', 'http://localhost:9001/')
        cy.visit('/remittance')
        cy.url().should('eq', 'http://localhost:9001/remittance')


    })
})