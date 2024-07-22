import testUser from '../fixtures/testUser.json';


let date = new Date();
let seconds = date.getTime();



describe('log in page', () => {
    it('can sign up and log a user in', () => {
        cy.visit('/signup')
        
        cy.get('[data-cy="emailInput"]').type("test"+seconds+"@bristol.ac.uk") 
        cy.get('[data-cy="firstNameInput"]').type("firstName") 
        cy.get('[data-cy="surnameInput"]').type("sername") 
        cy.get('[data-cy="DOBInput"]').type("2020-01-01") 
        cy.get('[data-cy="AddressInput"]').type("BS8 1HB") 
        cy.get('[data-cy="PhoneNumberInput"]').type("+44 782479472") 
        cy.get('[data-cy="passwordInput"]').type("abc")
        cy.get('[data-cy="confirmPasswordInput"]').type("abc")
        cy.get('[data-cy="submitButton"]').click()

        cy.visit('/kyc')
        cy.get('#buttonKyc').should('be.visible').click()

        cy.get('[data-cy="emailInput"]').should('be.visible').type("test"+seconds+"@bristol.ac.uk")
        cy.get('[data-cy="passwordInput"]').type("abc")
        cy.get('[data-cy="submitButton"]').click()

    })

})