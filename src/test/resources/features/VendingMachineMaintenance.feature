  # As a vending machine caretaker
  # I want the vending machine to communicate vital information
  # So that I may perform all required maintenance
  Feature: Vending Machine Maintenance

  # As a vending machine caretaker
  # I want to know what Inventory Types (items and Coins) needs replenishing
  # So that I may re-stock the vending machine with Inventory (items and/or coins)
#    Scenario : Reset the vending machine for the Items inventory
#      Given the vending machine count <vendingInventoryCount> for inventory <inventory> has been taken from vending machine
#      And the physical count <physicalInventoryCount> for inventory <inventory> has been taken
#      When the machine inventory <inventory> count <machineInventoryCount> does not match <physicalInventoryCount>
#      Then the vending machine inventory <inventory> can be reset to match <physicalInventoryCount>
#
#
#    Scenario : Reset the vending machine for the Coins inventory
#      Given the vending machine count <vendingInventoryCount> for inventory <inventory> has been taken from vending machine
#      And the physical count <physicalInventoryCount> for inventory <inventory> has been taken
#      When the machine inventory <inventory> count <machineInventoryCount> does not match <physicalInventoryCount>
#      Then the vending machine inventory <inventory> can be reset to match <physicalInventoryCount>
#
#
#    Scenario : Reset the vending machine for all Inventory
#      Given the vending machine count <vendingInventoryCount> for inventory <inventory> has been taken from vending machine
#      And the physical count <physicalInventoryCount> for inventory <inventory> has been taken
#      When the machine inventory <inventory> count <machineInventoryCount> does not match <physicalInventoryCount>
#      Then the vending machine inventory <inventory> can be reset to match <physicalInventoryCount>
#
#      Examples:
#        | inventoryType | inventory | physicalInventoryCount | machineInventoryCount |
#        | item          | COKE      | 10     |                    |
#
#    Scenario : Reset the vending machine for the products and coins
#
#    Scenario : Add a new stock line item to the vending machine list
#
#    Scenario : Remove an existing stock line item from vending machine list
#
#    Scenario : Add a new monetary type to be used by the vending machine
#
#    Scenario : Replenish any of the products in the vending machine
#
#    Scenario : Remove any of the products in the vending machine
#
#    Scenario : Replenish monetary payments in the vending machine
#
#    Scenario : Remove monetary payments from the vending machine
#
#    Scenario : Successful stock replenish of vending machine
