package com.step_definitions;

import com.domain.Item;
import com.implementations.VendingMachineImpl;
import com.util.KnowsTheDomain;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.*;

import com.domain.Coin;
import org.junit.Assert;

public class VendingMachinePurchaseSteps {
    VendingMachineImpl vendingMachine;
    KnowsTheDomain helper;
    int numberOfCoinsEntered = 0;
    int expectedNumberOfCoinsEntered = 0;

    public VendingMachinePurchaseSteps(){
        vendingMachine = new VendingMachineImpl();
        helper = new KnowsTheDomain();
    }

    @Given("my vending machine is stocked")
    public void myVendingMachineIsStocked(DataTable dt) {

        List<Map<String, String>> list = dt.asMaps(String.class, String.class);
        for(int i=0; i<list.size(); i++) {

            String inventoryType = list.get(i).get("inventoryType").trim();
            String inventory = list.get(i).get("inventory").trim();
            String amount = list.get(i).get("amount").trim();
            int invAmount = Integer.parseInt(amount);
            if(inventoryType.equalsIgnoreCase("coin")){
                Coin coin = Coin.valueOf(inventory.toUpperCase());
                vendingMachine.addInventoryCoins(coin,invAmount);
            }
            else if(inventoryType.equalsIgnoreCase("item")){
                Item item = Item.valueOf(inventory.toUpperCase());
                vendingMachine.addInventoryItems(item,invAmount);
            }
        }
    }

    @When("^the user requests an (.*?) item in stock for the nominated price and paying (.*?) nominated payment?")
    public void theUserRequestsAnItemInStockForTheNominatedPriceUsingNominatedAndPayingPayment(String itemName, String amountPaid) {

        // Select item and get price
        Item item = Item.valueOf(itemName.toUpperCase());
        long actualItemPrice = vendingMachine.selectItemAndGetPrice(item);

        // Group coins to be used for payment
        String[] arrExpectedNumberOfCoinsEntered = amountPaid.trim().split(",");
        List<Coin> listOfInsertedCoins = new ArrayList<Coin>();
        for (String c : arrExpectedNumberOfCoinsEntered) {
            Coin coin = Coin.valueOf(c.toUpperCase());
            listOfInsertedCoins.add(coin);
            expectedNumberOfCoinsEntered ++;
        }

        // ------------- Before Insert Coins payment - Get all Inventory Qtys (Coins/Items) -------------
        int startingTotalCoinsBalance = vendingMachine.getInventoryCoinsBalance();
        int startingTotalItemsBalance = vendingMachine.getInventoryItemsBalance();
        System.out.println("WHEN - Before coins inserted - Initial Coins Balance: " + startingTotalCoinsBalance + ", Initial Items Balance: " + startingTotalItemsBalance);
        Enumeration<Coin> e = Collections.enumeration(listOfInsertedCoins);
        while(e.hasMoreElements()){
            vendingMachine.insertCoin(e.nextElement());
        }

        // ------------- After Insert Coins payment - Get all Inventory Qtys (Coins/Items) -------------
        int afterInsertTotalCoinsBalance = vendingMachine.getInventoryCoinsBalance();
        int afterInsertTotalItemsBalance = vendingMachine.getInventoryItemsBalance();
        System.out.println("WHEN - [Before receipt of payment] After ordering Product to buy: " + itemName + ", and Inserting Coins - New Coins Balance: " + afterInsertTotalCoinsBalance + ", New Items Balance (no change): " + afterInsertTotalItemsBalance);

        // Assert coins were added - updated the Coins Inv
        Assert.assertEquals(startingTotalCoinsBalance + numberOfCoinsEntered, afterInsertTotalCoinsBalance);
        // Assert no items dispatched yet
        Assert.assertEquals(startingTotalItemsBalance, afterInsertTotalItemsBalance);
    }

    @Then("^the user will receive (.*?) item with (.*?) change owed?")
    public void theUserWillReceiveItemWithChangeOwed(String itemName, String changeOwed) {

        // Get Expected coins for refund
        expectedNumberOfCoinsEntered = 0;
        String[] arrRefund = changeOwed.trim().split(",");
        List<Coin> listExpectedNumberOfCoinsEntered = new ArrayList<Coin>();
        for (String c : arrRefund) {
            Coin coin = Coin.valueOf(c.toUpperCase());
            listExpectedNumberOfCoinsEntered.add(coin);
            expectedNumberOfCoinsEntered ++;
        }

        //------------- Before we Collect Item and change - Get all Inventory Qtys (Coins/Items) -------------
        int nominatedItemBeforeQty = vendingMachine.getNominatedInventoryItemBalance(itemName);
        int totalCoinsBalanceBefore = vendingMachine.getInventoryCoinsBalance();
        // ToDo: Get list of coins due to be refunded

        vendingMachine.collectItemAndChange();

        //------------- After we Collect Item and change - Get all Inventory Qtys (Items/Coins/ChangeGiven) -------------
        int nominatedItemAfterQty = vendingMachine.getNominatedInventoryItemBalance(itemName);
        int totalCoinsBalanceAfter = vendingMachine.getInventoryCoinsBalance();
        System.out.println("THEN - after purchase of Item: " + itemName + ", Item Balance was: " + nominatedItemBeforeQty + ", now after purchase Items Balance: " + nominatedItemAfterQty);
        System.out.println("THEN - after purchase and entering Coins and receiving Product and any change - the Before Coins Balance was : " + totalCoinsBalanceBefore + ", after purchase, New Coins Balance: " + totalCoinsBalanceAfter);

        // Assert correct item was reduced
        Assert.assertEquals(nominatedItemBeforeQty - 1, nominatedItemAfterQty);

        // Assert correct coins were added to inventory and that the ones used as change owed were reduced
        Assert.assertEquals(totalCoinsBalanceBefore + numberOfCoinsEntered, totalCoinsBalanceAfter);
    }
























    // -----------------------------------------------------------------------------------------------------------
    // ToDo: For a differernt feature in the future
    public void getVMBalances(){
        // Code for another feature
        // ToDo: 2. Get stock & coin levels before items dispensed via ReturnBucket  - or do above???
        int startingTotalCoinsBalance = vendingMachine.getInventoryCoinsBalance();
        int startingTotalItemsBalance = vendingMachine.getInventoryItemsBalance();

        // ToDo: 4. Might need some DI to achieve this
        vendingMachine.collectItemAndChange();

        int currentTotalCoinsBalance = vendingMachine.getInventoryCoinsBalance();
        int currentTotalItemsBalance = vendingMachine.getInventoryItemsBalance();
        // ToDo: 3. Get stock levels after the purchase
        // ToDo: 5. Also may need to check if there is still any item/coins to dispense - this should be clear
        System.out.println("");
        // ToDo: need to know the total coins added for purchases
        // ToDo: In the Given we need to accumulate these and inject here
        Assert.assertEquals(startingTotalCoinsBalance, currentTotalCoinsBalance);
    }

}
