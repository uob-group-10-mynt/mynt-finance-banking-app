/// <reference types="Cypress" />
const { defineConfig } = require("cypress");

module.exports = defineConfig({
  e2e: {
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
    baseUrl: "http://localhost:9001",
    specPattern: [
      "cypress/e2e/*.cy.js",
      "cypress/frontendOnly/*.cy.js"
    ]
  },
  watchForFileChanges: false,
  viewportWidth: 760

});
