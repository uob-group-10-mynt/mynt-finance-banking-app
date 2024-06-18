-- Drop tables if they exist to avoid errors when creating them
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS CurrencyCloud;

-- Create the User table
CREATE TABLE User (
    id INT AUTO_INCREMENT,
    firstName VARCHAR(100),
    surname VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(200),
    PRIMARY KEY (id)  -- Corrected primary key definition
);

-- Create the CurrencyCloud table
CREATE TABLE CurrencyCloud (
    contactID INT AUTO_INCREMENT,
    userID INT,
    PRIMARY KEY (contactID),  -- Corrected primary key definition
    FOREIGN KEY (userID) REFERENCES User(id)  -- Corrected foreign key definition
);
