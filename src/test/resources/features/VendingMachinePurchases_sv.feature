  # As a vending machine customer
  # I want to know what products are available
  # And how much they cost
  # So that I may purchase the products

  Feature: Vending Machine Usage

    Scenario Outline: User can purchase any product in stock and receive the correct change
      Given my vending machine is stocked
      #| stock | amount |
      #| COKE  | 5      |
      #| PEPSI | 5      |
      #| SODA  | 5      |
      When the user requests a <product> in stock for the nominated price <toPay> using <coinsUsed> payment
      Then the user will receive <product> with correct <changeOwed> change owed

      Examples:
        | product | toPay | coinsUsed           | changeOwed |
        | COKE    | 25    | 25                  | 0          |
        | PEPSI   | 35    | 25,5,5              | 0          |
        | SODA    | 45    | 10,5,               | 0          |
        | COKE    | 25    | 25,1                | 1          |
        | PEPSI   | 35    | 5,5,5,5,5,10,1      | 1          |
        | SODA    | 45    | 1,1,1,1,1,1,25,10,5 | 1          |
        | COKE    | 25    | 5,5,5,5,5,5         | 5          |
        | PEPSI   | 35    | 10,1,1,1,1,1,25     | 5          |
        | SODA    | 45    | 1,1,1,1,1,5,5,10,25 | 5          |
        | COKE    | 25    | 10,10,10,5          | 10         |
        | PEPSI   | 35    | 25,10,5,1,1,1,1,1   | 10         |
        | SODA    | 45    | 10,10,5,5,25        | 10         |

    Scenario : User can purchase any product in stock and receive the correct change when one of the
    denominations has insufficient stock
      Given my vending machine is stocked
      And my <denomination> denomination has a zero balance
      When the user requests a <product> in stock with <payment> payment
      Then the user will receive <product> with correct <changeOwed> change

    Scenario : User cannot purchase a drink out of stock with any amount

    Scenario : User can purchase a drink in stock when paying with a higher amount

    Scenario : User cannot purchase a drink in stock when paying with a lesser amount

    Scenario : User cannot purchase a drink in stock when paying with any amount, if there is not enough change left to cover the amount owed

    Scenario : User cannot purchase a drink in stock, when paying with the any amount, when there is not enough change to be returned, that adds up to the due amount

    Scenario : Successful stock replenish of vending machine
      Given my <fromAccount> account has been credited <deposit>
      When I withdraw <request>
      Then <dispense> should be dispensed
      And the balance of my <fromAccount> account should be <accountBalance>

