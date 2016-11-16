Feature: Transfering money 

Scenario: Transfering money adjusts the account balances 
    Given an account 111 with 1000$ in it
    And an account 222 with 500$ in it
    When I transfer 100$ from 111 to 222
    Then the account 111 has 900$ in it
    And the account 222 has 600$ in it