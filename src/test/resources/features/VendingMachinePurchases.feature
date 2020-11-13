  # As a vending machine customer
  # I want to know what products are available
  # And how much they cost
  # So that I may purchase the products

  Feature: Vending Machine Purchases

    Background: Stock the vending machine
      Given the vending machine is stocked
        | inventoryType | inventory | amount |
        | item          | COKE      | 10     |
        | item          | PEPSI     | 7      |
        | item          | SODA      | 8      |
        | coin          | PENNY     | 5      |
        | coin          | NICKEL    | 12     |
        | coin          | DIME      | 20     |
        | coin          | QUARTER   | 5      |


    Scenario Outline: User can purchase any item in stock and receive the correct change
      When the user selects in stock <itemName> and enters <amountPaid>
      And the user accepts transaction and expects item
      Then the item <itemName> will be dispensed
      And any change owed <changeOwed> will be dispensed
      Examples:
        | itemName | amountPaid                                                      | changeOwed                                |
        | COKE     | dime,dime,nickel,penny,penny                                    | penny,penny                               |
        | COKE     | quarter                                                         | 0                                         |
        | PEPSI    | quarter,nickel,penny,penny,penny,penny,penny                    | 0                                         |
        | SODA     | dime,dime,quarter                                               | 0                                         |
        | COKE     | quarter,penny                                                   | penny                                     |
        | PEPSI    | nickel,nickel,nickel,nickel,nickel,dime,penny                   | penny                                     |
        | SODA     | penny,penny,penny,penny,penny,penny,dime,nickel,quarter         | penny                                     |
        | COKE     | nickel,nickel,nickel,nickel,nickel,nickel                       | nickel                                    |
        | PEPSI    | dime,penny,penny,penny,penny,penny,quarter                      | nickel                                    |
        | SODA     | penny,penny,penny,penny,penny,nickel,nickel,dime,quarter        | nickel                                    |
        | COKE     | dime,dime,dime,nickel                                           | dime                                      |
        | PEPSI    | quarter,dime,nickel,penny,penny,penny,penny,penny               | dime                                      |
        | SODA     | dime,dime,nickel,nickel,quarter                                 | dime                                      |
        | COKE     | dime,dime,quarter,nickel                                        | quarter                                   |
        | PEPSI    | quarter,quarter,nickel,penny,penny,penny,penny,penny            | quarter                                   |
        | SODA     | quarter,dime,nickel,nickel,quarter                              | quarter                                   |
        | COKE     | dime,dime,nickel,penny,penny                                    | penny,penny                               |
        | PEPSI    | quarter,dime,nickel,penny,penny                                 | nickel,penny,penny                        |
        | SODA     | dime,dime,nickel,nickel,quarter,penny,penny,penny               | dime,penny,penny,penny                    |
        | PEPSI    | dime,dime,nickel,nickel,quarter,quarter,penny,penny,penny,penny | quarter,dime,dime,penny,penny,penny,penny |

    Scenario Outline: User can purchase any item in stock and receive the correct change even when a denomination that we do not require is out of stock
      And the <denomination> denomination has the following <insufficientBalance> balance
      When the user selects in stock <itemName> and enters <amountPaid>
      And the user accepts transaction and expects item
      Then the item <itemName> will be dispensed
      And any change owed <changeOwed> will be dispensed
      Examples:
        | itemName | denomination | insufficientBalance | amountPaid                  | changeOwed |
        | COKE     | penny        | 0                   | quarter                     | 0          |
        | PEPSI    | dime         | 0                   | quarter,nickel,nickel,penny | penny      |
        | SODA     | quarter      | 0                   | quarter,quarter             | nickel     |
        | PEPSI    | penny        | 0                   | quarter,dime,quarter        | quarter    |

    Scenario Outline: User cannot purchase a drink in stock when paying with a lesser amount
      When the user selects in stock <itemName> and enters <lessMoneyPaid>
      And the user accepts transaction and expects no item
      # ToDo:
      Then the item <itemName> will not be dispensed if errors
      Examples:
        | itemName | lessMoneyPaid                     |
        | COKE     | dime,dime,penny,penny,penny,penny |
        | PEPSI    | quarter,nickel                    |
        | SODA     | quarter,dime                      |
        | SODA     | quarter,dime,nickel,penny         |


    Scenario Outline: User must be allowed to receive a refund before receiving product
      When the user selects in stock <itemName> and enters <amountPaid>
      And the user cancels the transaction
      Then the <amountPaid> will be refunded
      # ToDo:
      And the item <itemName> will not be dispensed if cancelled
      Examples:
        | itemName | amountPaid                        |
        | COKE     | dime,dime,penny,penny,penny,penny |
        | PEPSI    | quarter,dime                      |
        | SODA     | quarter,quarter                   |

    Scenario Outline: You cannot complete a purchase for an out of stock item
      Given the <itemName> is out of stock
      When the user selects out of stock <itemName> and enters <amountPaid>
      And the user accepts transaction and expects no item
      Then the item <itemName> will not be dispensed if errors
      And the <amountPaid> will still be available
      Examples:
        | itemName | amountPaid                        |
        | COKE     | dime,dime,penny,penny,penny,penny |
        | PEPSI    | quarter,dime                      |
        | SODA     | quarter,quarter                   |

#
# #ToDo:
##   As a vending machine supplier
##   I want to be able to reset the machine
##   So that I can update the items and coins inventory
#    Scenario Outline: Vending Machine <inventoryType> inventory can be reset by Supplier
#      Given the vending machines coin stock has been verified
#      When the vending machine <inventoryType> inventory is loaded with new <inventoryItemAndQuantities>
#      Then the total coin stock will be updated
#
#      Examples:
#        | inventoryType | inventoryItemAndQuantities           |
#        | coin          | penny=25,nickel=3,dime=10,quarter=40 |
#        | items         | coke=5,pepsi=10,soda=15              |
#
##    ToDo: also verify the individual coins have updated accordingly
##    Scenario Outline: Vending Machine coins Inventory can be reset by Supplier
#
#    Scenario Outline: Vending Machine items Inventory can be reset by Supplier
#

#    ToDo: also verify the individual itesm have updated accordingly
#    Scenario Outline: Vending Machine items Inventory can be reset by Supplier


#ToDo:
#    Scenario Outline: User must not receive a refund when selecting to cancel when no payment has been made

  # ToDo:
       #Scenario Outline: User cannot select an item out of stock and have their payment accepted

#Todo:
#    Scenario Outline: User must NOT be allowed to receive a full refund after receiving product




        # ToDo:
#    Scenario : User cannot purchase a drink in stock when paying with any amount, if there is not enough change left to cover the amount owed
#ToDo:
#    Scenario : User cannot purchase a drink in stock, when paying with the any amount, when there is not enough change to be returned, that adds up to the due amount
#ToDo:
    #Scenario : ???Changing your product choice when not enough money paid - Eg: paid 30,request pepsi, then change to coke


