describe('Error Page Routing Test', () => {
    it('Visits a non-existent page and verifies error page routing', () => {
        const invalidURL = "/invalidURL";
        cy.visit(invalidURL);

        cy.get('h2').should('contain', '404');
    });
});
