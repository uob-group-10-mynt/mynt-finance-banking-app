import testUser from '../fixtures/testUser.json';


let date = new Date();
let seconds = date.getTime();



describe('log in page', () => {
    it('can sign up and log a user in', () => {
        cy.visit('/signup')
        
        cy.get('[data-cy="emailInput"]').type("test"+seconds+"@bristol.ac.uk") 
        cy.get('[data-cy="firstnameInput"]').type("firstName") 
        cy.get('[data-cy="lastnameInput"]').type("sername") 
        cy.get('[data-cy="dobInput"]').type("2020-01-01") 
        cy.get('[data-cy="addressInput"]').type("BS8 1HB") 
        cy.get('[data-cy="phoneNumberInput"]').type("+44 782479472") 
        cy.get('[data-cy="passwordInput"]').type("abc")
        cy.get('[data-cy="confirmPasswordInput"]').type("abc")
        cy.get('[data-cy="submitButton"]').click()

        cy.visit('/kyc')
        cy.get('#buttonKyc').should('be.visible').click()

        cy.get('[data-cy="emailInput"]').should('be.visible').type("test"+seconds+"@bristol.ac.uk")
        cy.get('[data-cy="passwordInput"]').type("abc")
        cy.get('[data-cy="submitButton"]').click()
        cy.get('[data-cy="navButton"]').click()
        cy.get('[data-cy="RemittanceLink"]').contains('Transfer')

    })

})