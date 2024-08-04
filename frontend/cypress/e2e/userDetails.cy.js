describe('user details page', ()  => {
    it('can view and update user details', () => {
        let timestamp = Date.now()
        const firstname = 'Cypress' + timestamp
        const lastname = 'Test' + timestamp
        const dob = new Date().toISOString().split("T")[0]
        const address = 'Bristol' + timestamp
        const phoneNumber = '07123123123' + timestamp

        cy.loginTestUser()
        cy.get('[data-cy="navButton"]').click()
        cy.get('[data-cy="DetailsLink"]').click()

        //set new details
        cy.get('[data-cy="saveDetailsButton"]').should('not.be.visible')
        cy.get('[data-cy="EditButton"]').click({force: true})
        cy.get('[data-cy="EditButton"]').click({force: true})
        cy.get('[data-cy="saveDetailsButton"]').should('be.visible')
        cy.get('[data-cy="firstnameInput"]').clear().type(firstname)
        cy.get('[data-cy="lastnameInput"]').clear().type(lastname)
        cy.get('[data-cy="dobInput"]').should('have.attr', 'readonly')
        cy.get('[data-cy="addressInput"]').clear().type(address)
        cy.get('[data-cy="phoneNumberInput"]').clear().type(phoneNumber)
        cy.get('[data-cy="saveDetailsButton"]').click()
        cy.get('[data-cy="EditButton"]').should('be.visible')
        cy.get('[data-cy="saveDetailsButton"]').should('not.be.visible')

        //navigate away and back to page
        cy.get('[data-cy="navButton"]').click()
        cy.get('[data-cy="RemittanceLink"]').click()
        cy.get('[data-cy="navButton"]').click()
        cy.get('[data-cy="DetailsLink"]').click()

        //assert changes
        cy.get('[data-cy="firstnameInput"]').should('have.value', firstname).should('have.attr', 'readonly')
        cy.get('[data-cy="lastnameInput"]').should('have.value', lastname).should('have.attr', 'readonly')
        cy.get('[data-cy="dobInput"]').should('have.value', dob).should('have.attr', 'readonly')
        cy.get('[data-cy="addressInput"]').should('have.value', address).should('have.attr', 'readonly')
        cy.get('[data-cy="phoneNumberInput"]').should('have.value', phoneNumber).should('have.attr', 'readonly')
    })
})