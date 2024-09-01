describe("expired access and refresh tokens are handled", () => {
    it("user logged out when both tokens are invalid", () => {
        cy.loginTestUser()
        cy.get('[data-cy="navButton"]').click()
        cy.get('[data-cy="DetailsLink"]').click()

        //set new details
        cy.get('[data-cy="EditButton"]').click()
        cy.get('[data-cy="saveDetailsButton"]').should('be.visible')
        cy.get('[data-cy="firstnameInput"]').clear()
        cy.get('[data-cy="firstnameInput"]').type("tokens have been cleared")
        cy.clearAllSessionStorage()
        cy.get('[data-cy="saveDetailsButton"]').click()

        cy.url().should("include", "login")
    })
    it("new tokens are used when access token is invalid", () => {
        cy.loginTestUser()
        cy.get('[data-cy="navButton"]').click()
        cy.get('[data-cy="DetailsLink"]').click()

        //set new details
        cy.get('[data-cy="EditButton"]').click()

        //make access token invalid
        cy.window().then((win) => {
            win.sessionStorage.setItem("access", null);
          });

        cy.get('[data-cy="saveDetailsButton"]').should('be.visible')
        cy.get('[data-cy="firstnameInput"]').clear()
        cy.get('[data-cy="firstnameInput"]').type("get new tokens test")
        cy.get('[data-cy="saveDetailsButton"]').click()
        cy.get('[data-cy="EditButton"]').should('be.visible')

        //assert
        cy.get('[data-cy="firstnameInput"]').should('have.value', "get new tokens test").should('have.attr', 'readonly')
    })
})