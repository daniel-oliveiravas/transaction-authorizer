# Transaction Authorizer
Transaction Authorizer is a application to authorize a transaction of a given account following some predefined rules.

## Operations
The application supports two kinds of operations, being:
- Account Creation
- Transaction

An account can be created by providing the following json structure:

`{ "account": { "activeCard": true, "availableLimit": 100 }}`

A transaction can be performed with:

`{ "transaction": { "merchant": "Burger King", "amount": 20, "time": "2019-02-13T10:00:00.000Z" }}`

Note: Only one account can be created per execution

# Usage

To use the application you first need to build it by running:

`./mvnw package`

After that you can use a simple java -jar command, for example:

`java -jar target/transaction-authorizer-1.0-SNAPSHOT.jar operations`

# Tests

To run the tests you can simply run:

`./mnvw verify`

This will execute both unit and integration tests

