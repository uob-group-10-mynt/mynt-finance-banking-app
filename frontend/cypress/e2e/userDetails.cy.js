describe('user details page', ()  => {
    it('can view and update user details', () => {
        const firstname = 'Cypress'
        const lastname = 'Test'
        const dob = '1999-10-19'
        const address = 'Bristol'
        const phoneNumber = '07123123123'

        //updated data
        const newFirstname = 'William'
        const newLastname = 'Shakespeare'
        const newDob = '1999-04-23'
        const newAddress = 'Stoke'
        const newPhoneNumber = '07321321321'

        cy.loginTestUser()
        //check starting values
        cy.get('[data-cy="navButton"]').click()
        cy.get('[data-cy="DetailsLink"]').click()
        cy.get('[data-cy="firstnameInput"]').should('have.value', firstname)
        cy.get('[data-cy="lastnameInput"]').should('have.value', lastname)
        cy.get('[data-cy="dobInput"]').should('have.value', dob)
        cy.get('[data-cy="addressInput"]').should('have.value', address)
        cy.get('[data-cy="phoneNumberInput"]').should('have.value', phoneNumber)
        
        //update
        cy.get('[data-cy="EditButton"]').click()
        cy.get('[data-cy="firstnameInput"]').clear().type(newFirstname)
        cy.get('[data-cy="lastnameInput"]').clear().type(newLastname)
        cy.get('[data-cy="dobInput"]').type(newDob)
        cy.get('[data-cy="addressInput"]').clear().type(newAddress)
        cy.get('[data-cy="phoneNumberInput"]').clear().type(newPhoneNumber)
        cy.get('[data-cy="saveDetailsButton"]').click()

        //navigate away and back to page
        cy.get('[data-cy="navButton"]').click()
        cy.get('[data-cy="RemittanceLink"]').click()
        cy.get('[data-cy="navButton"]').click()
        cy.get('[data-cy="DetailsLink"]').click()

        //assert changes
        cy.get('[data-cy="firstnameInput"]').should('have.value', newFirstname).should('have.attr', 'readonly')
        cy.get('[data-cy="lastnameInput"]').should('have.value', newLastname).should('have.attr', 'readonly')
        cy.get('[data-cy="dobInput"]').should('have.value', newDob).should('have.attr', 'readonly')
        cy.get('[data-cy="addressInput"]').should('have.value', newAddress).should('have.attr', 'readonly')
        cy.get('[data-cy="phoneNumberInput"]').should('have.value', newPhoneNumber).should('have.attr', 'readonly')

        //reset form
        cy.get('[data-cy="EditButton"]').click()
        cy.get('[data-cy="firstnameInput"]').clear().type(firstname)
        cy.get('[data-cy="lastnameInput"]').clear().type(lastname)
        cy.get('[data-cy="dobInput"]').type(dob)
        cy.get('[data-cy="addressInput"]').clear().type(address)
        cy.get('[data-cy="phoneNumberInput"]').clear().type(phoneNumber)
        cy.get('[data-cy="saveDetailsButton"]').click()
    })
})