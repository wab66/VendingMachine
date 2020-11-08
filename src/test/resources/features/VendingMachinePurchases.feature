  # As a vending machine customer
  # I want to know what products are available
  # And how much they cost
  # So that I may purchase the products

  Feature: Vending Machine Purchases

    Scenario Outline: User can purchase any item in stock and receive the correct change
      Given my vending machine is stocked
        | inventoryType | inventory | amount |
        | item          | COKE      | 6      |
        | item          | PEPSI     | 7      |
        | item          | SODA      | 8      |
        | coin          | PENNY     | 5      |
        | coin          | NICKEL    | 12     |
        | coin          | DIME      | 20     |
        | coin          | QUARTER   | 5      |

      When the user requests an <itemName> item in stock for the nominated price and paying <coinsUsed> nominated payment
      Then the user will receive <itemName> item with <changeOwed> change owed

      Examples:
        | itemName | coinsUsed                                                | changeOwed                      |
        | COKE     | quarter                                                  | 0                               |
        | PEPSI    | quarter,nickel,penny,penny,penny,penny,penny             | 0                               |
        | SODA     | dime,nickel,quarter,dime                                 | 0                               |
        | COKE     | quarter,penny                                            | penny                           |
        | PEPSI    | nickel,nickel,nickel,nickel,nickel,dime,penny            | penny                           |
        | SODA     | penny,penny,penny,penny,penny,penny,quarter,dime,nickel  | penny                           |
        | COKE     | nickel,nickel,nickel,nickel,nickel,nickel                | nickel                          |
        | PEPSI    | dime,penny,penny,penny,penny,penny,quarter               | nickel                          |
        | SODA     | penny,penny,penny,penny,penny,nickel,nickel,dime,quarter | nickel                          |
        | COKE     | dime,dime,dime,nickel                                    | dime                            |
        | PEPSI    | quarter,dime,nickel,penny,penny,penny,penny,penny        | dime                            |
        | SODA     | dime,dime,nickel,nickel,quarter                          | dime                            |
        | COKE     | dime,dime,dime,nickel                                    | penny,penny                     |
        | PEPSI    | quarter,dime,nickel,penny,penny,penny,penny,penny        | nickel,penny,penny              |
        | SODA     | dime,dime,nickel,nickel,quarter                          | dime,penny,penny,penny          |
        | SODA     | dime,dime,nickel,nickel,quarter                          | quarter,penny,penny,penny,penny |

    #Scenario : User can purchase any product in stock and receive the correct change when one of the
    #denominations has insufficient stock
      #Given my vending machine is stocked
      #And my <denomination> denomination has a zero balance
      #When the user requests a <product> in stock with <payment> payment
      #Then the user will receive <product> with correct <changeOwed> change

    #Scenario : User cannot purchase a drink out of stock with any amount

    #Scenario : User can purchase a drink in stock when paying with a higher amount

    #Scenario : User cannot purchase a drink in stock when paying with a lesser amount

    #Scenario : User cannot purchase a drink in stock when paying with any amount, if there is not enough change left to cover the amount owed

    #Scenario : User cannot purchase a drink in stock, when paying with the any amount, when there is not enough change to be returned, that adds up to the due amount

    #Scenario : Successful stock replenish of vending machine
      #Given my <fromAccount> account has been credited <deposit>
      #When I withdraw <request>
      #Then <dispense> should be dispensed
      #And the balance of my <fromAccount> account should be <accountBalance>

