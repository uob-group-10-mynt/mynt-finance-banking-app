describe('hello word page', () => {
  it('loads', () => {
    cy.visit('/')
    cy.get('h1').contains('Hello world!! - body!')
    cy.get('#root').contains('Hello world1!! - within react')
  })
})