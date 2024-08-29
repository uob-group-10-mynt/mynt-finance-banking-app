describe('user must be logged in to access a protected page', () => {
    it("redirected to '/login' when not logged in and try to go to a protected route", () => {
        let protectedRoutes = ['remittance', 'dashboard', 'accounts']
        protectedRoutes.forEach((route) => {
            cy.visit(`/${route}`)
            cy.url().should('eq', 'http://localhost:9001/login')

        })
    })

    it("can access transfer page when logged in", () => {
        cy.loginTestUser()
        cy.url().should('eq', 'http://localhost:9001/accounts')
    })
})