Feature: Transfering money 
    In order to pay people easily
    As a bank user
    I want to be able to transfer money to others

@kind=happyPath
Scenario: Transfering money adjusts the account balances 
    Given an account 111 with 1000$ in it
    And an account 222 with 500$ in it
    When I create a transaction of 100$ from 111 to 222
    Then the account 111 has 900$ in it
    And the account 222 has 600$ in it
    
@kind=happyPath @focus
Scenario: Transfering money creates an accepted transaction log
    Given an account 333 with 1000$ in it
    And an account 444 with 500$ in it
    When I transfer 100$ from 333 to 444
    Then a transaction log is created for the amount of 100$

@focus
Scenario: Transfering money when the account doesn't have the funds creates a refused transaction
    Given an account 555 with 99$ in it
    And an account 666 with 500$ in it
    When I transfer 100$ from 555 to 666
    Then a transaction log shows that the transfer was refused
    