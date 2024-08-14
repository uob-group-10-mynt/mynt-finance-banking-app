describe('Navigation Drawer Test', () => {
    it('can be found access via most pages and routes to the correct page', () => {
        cy.visit('/')

        cy.get('[data-cy="navButton"]').click()
        cy.get('[data-cy="LoginLink"]').click()
        cy.url().should('include', '/login');


    })
})