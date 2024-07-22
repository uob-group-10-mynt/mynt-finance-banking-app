import testUser from '../fixtures/testUser.json';


let date = new Date();
let seconds = date.getUTCSeconds();



describe('log in page', () => {
    it('can log a user in and out', () => {
        cy.visit('/signup')
        
        cy.get('[data-cy="emailInput"]').type("bristoldsfasfdasfasfas@bristol.ac.uk")
        cy.get('[data-cy="firstNameInput"]').type("firstName")
        cy.get('[data-cy="surnameInput"]').type("sername")
        cy.get('[data-cy="DOBInput"]').type("2020-01-01")
        cy.get('[data-cy="AddressInput"]').type("BS8 1HB")
        cy.get('[data-cy="PhoneNumberInput"]').type("+44 782479472")
        cy.get('[data-cy="passwordInput"]').type("abc")
        cy.get('[data-cy="confirmPasswordInput"]').type("abc")
        cy.get('[data-cy="submitButton"]').click()

    })

})