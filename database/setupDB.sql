-- Create the User table
CREATE TABLE "User" (
    id SERIAL PRIMARY KEY,
    "firstName" VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(200) NOT NULL
);

-- Create the CurrencyCloud table
CREATE TABLE "CurrencyCloud" (
    "contactID" SERIAL PRIMARY KEY,
    "userID" INT,
    FOREIGN KEY ("userID") REFERENCES "User"(id)
);
