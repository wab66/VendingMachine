package com.step_definitions;

import com.domain.Coin;
import com.domain.Item;
import com.exceptions.InsufficientChangeException;
import com.exceptions.InsufficientPaymentException;
import com.exceptions.OutOfStockException;
import com.implementations.VendingMachineImpl;
import com.util.KnowsTheDomain;
import com.util.ReturnBucket;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.graalvm.compiler.debug.CSVUtil;

public class VendingMachinePurchaseSteps {
    VendingMachineImpl vendingMachine;
    KnowsTheDomain helper;
    int expectedCountOfCoinsToEnter = 0;
    int expectedNumberOfCoinsForChange = 0;


    int expectedCurrentTotalCoins = 0;
    int expectedCurrentTotalItems = 0;

    int expectedTotalAmountPaid;
    int expectedTotalChangeAmountDue;

    int vmCurrentTotalCoins = 0;
    int vmCurrentTotalItems = 0;

    Item itemToPurchase;
    List<Coin> expectedListOfCoinsForPayment;
    ReturnBucket<Item, List<Coin>> returnBucket;
    List<Coin> coinRefundedList;

    public VendingMachinePurchaseSteps() {
        vendingMachine = new VendingMachineImpl();
        helper = new KnowsTheDomain();
    }

    //ToDo:
    @Given("the vending machine is stocked")
    public void theVendingMachineIsStocked(DataTable dt) {
        // Parse data table - And load vending machine stock
        dtLoadVendingMachineStock(dt);
    }

    @Given("the vending machines (.*?) stock has been verified")
    public void theVendingMachineCoinStockVerified(String inventoryType) {
        System.out.println("Total coin count: " + expectedCurrentTotalCoins);
    }

    @Given("^the (.*?) denomination has the following (.*?) balance?")
    public void theDenominationHasTheFollowingBalance(String denomination, String denominationBalance) {
        int qty = Integer.parseInt(denominationBalance);
        try {
            Coin coin = Coin.valueOf(denomination.toUpperCase());
            loadVendingMachineDenomination(coin, qty);
        } catch (IllegalArgumentException e) {
            Assert.fail("Invalid Coin Type:" + denomination);
        }
    }

    // Given the <item> is out of stock
    @Given("^the (.*?) is out of stock?")
    public void theItemIsOutOfStock(String itemName) {
        Item item = getItemObjectByName(itemName);
        initialiseItem(item, 0);
    }

    //ToDo:
    @When("^the vending machine (.*?) inventory is loaded with new (.*?)?")
    public void theVendingMachineIsLoadedWithNewItemsQty(String inventoryType, String inventoryItemAndQuantities) {

    }

    @When("^the user accepts transaction and expects (n?o? ?)item?")
    public void theUserAcceptsTransaction(String expectItem) {
        boolean isItemExpected = true;
        if (expectItem.trim().equals("no")) {
            isItemExpected = false;
            Assert.assertFalse("Expecting no transaction, Actual: there is a transaction", acceptTransaction(isItemExpected));
        // Put order through
        } else {
            Assert.assertTrue("Expecting transaction, Actual: there is no transaction", acceptTransaction(isItemExpected));
        }
    }

    @When("^the user cancels the transaction?")
    public void theUserSelectsToCancelTransaction() {
        coinRefundedList = vendingMachine.refund();
    }

    // WHEN001
    //the user selects <itemName> in stock and enters <moneyPaid> - [i?o?][n?u?]?t? ?o?f?
    @When("^the user selects (in|out of) stock (.*?) and enters (.*?)?")
    public void theUserSelectsStockItemAndEntersAmount(String isInStockItem, String itemName, String amountPaid) {

        // Item not passed
        if (!isInStockItem.trim().isEmpty()) {
            // In Stock
            String temp = isInStockItem.trim();
            if (isInStockItem.trim().equalsIgnoreCase("in")) {
                // ToDo: Not in stock - expecting item - look for out of stoc item exception
                getItemForPurchase(itemName);
            } else if (isInStockItem.trim().equalsIgnoreCase("out of")) {
            }
        }

        getCoinsForPurchase(amountPaid);

        // ------------- Before Insert Coins payment - Get all Inventory Qtys (Coins/Items) -------------
        vmCurrentTotalCoins = vendingMachine.getInventoryCoinsBalance();
        vmCurrentTotalItems = vendingMachine.getInventoryItemsBalance();
        vmInsertCoins(expectedListOfCoinsForPayment);

        // ------------- After Insert Coins payment - Get all Inventory Qtys (Coins/Items) -------------
        int afterInsertTotalCoinsBalance = vendingMachine.getInventoryCoinsBalance();
        int afterInsertTotalItemsBalance = vendingMachine.getInventoryItemsBalance();

        //ToDo: these need to be moved out of here to a THEN step
        // Assert coins were added - updated the Coins Inv
        Assert.assertTrue("", (vmCurrentTotalCoins + expectedCountOfCoinsToEnter) == afterInsertTotalCoinsBalance);
        // Assert no items dispatched yet
        Assert.assertTrue("", vmCurrentTotalItems == afterInsertTotalItemsBalance);
    }

    //ToDo:
    @Then("^the total (.*?) stock will be updated?")
    public void theTotalStockWillBeUpdated(Item inventoryType) {

    }

    // Check we have no item to be dispensed - [ce]?[ar]?[nr]?[co]?[er]?[ls]?[l]?[e]?[d]?
    @Then("^the item (.*?) will not be dispensed if (errors|cancelled)?")
    public void theItemWillNotBeDispensed(String itemName, String reasonForNonDispense) {

        // If cancelled no itemToBeDispensed any more - error if still there
        if (reasonForNonDispense.equalsIgnoreCase("cancelled")) {
            // When cancelling order item not dispensed
            //isItemReadyForDispense

            // If cancelled no itemToBeDispensed any more - error if still there
            if (vendingMachine.isItemReadyForDispense()) {
                Item item = vendingMachine.getItemToDispense();
                Assert.assertFalse("Expected no item to be dispensed after cancel.  Actual: the item to " +
                        "dispense field is now empty, which means it has been dispensed in error. '" +
                        item.getItemName() + "'.", true);
            }
        } else if (reasonForNonDispense.equalsIgnoreCase("errors")) {
            // there will be no item to dispense, only once the item has been dispensed,
            // otherwise if item not yet dispensed, then it will still have the item in there
            if (vendingMachine.isItemReadyForDispense()) {
                Item item = vendingMachine.getItemToDispense();
                if (item == null) {
                    Assert.assertFalse("Expected no item to be dispensed after an error (no stock, " +
                            "insufficient money, no change), still on list to purchase.  Actual: the item to " +
                            "dispense field is now empty, removed from the order." +
                            item.getItemName() + "'.", true);
                }
            }


        }
//
//        // there will be no item to dispense, only once the item has been dispensed,
//        // otherwise if item not yet dispensed, then it will still have the item in there
//        Item item = vendingMachine.getItemToDispense();
//        if(item==null){
//            Assert.assertFalse("Expected no item to be dispensed.  Actual: the item to " +
//                    "dispense field is now empty, which means it has been dispensed in error. '" +
//                            item.getItemName() + "'.", true);
//        }
    }

    @Then("^the item (.*?) will be dispensed?")
    public void theItemWillBeDispensed(String itemName) {

        Item itemPurchased = returnBucket.getItem();
        int actualChangeDispensed = 0;
        Assert.assertTrue("Item ordered '" + itemToPurchase.getItemName() +
                "', does NOT match Item delivered '" + itemPurchased.getItemName()
                + "'.", itemToPurchase.getItemName().equals(itemPurchased.getItemName()));
    }

    @Then("^any change owed (.*?) will be dispensed?")
    public void anyChangeOwedWillBeDispensed(String changeOwed) {

        getExpectedCoinsForChange(changeOwed);
        List<Coin> bucketCoinList = returnBucket.getCoin();
        int actualChangeDispensed = 0;
        for (Coin coin : bucketCoinList) {
            int returnCoinValue = coin.getDenomination();
            String returnCoinName = coin.getCoinName();
            actualChangeDispensed = actualChangeDispensed + returnCoinValue;
        }
        Assert.assertTrue("Item '" + itemToPurchase.getItemName() +
                "' - The Change owed '" + expectedTotalChangeAmountDue +
                "', does NOT match dispensed '" + actualChangeDispensed +
                "'.", expectedTotalChangeAmountDue == actualChangeDispensed);
    }

    @Then("^any changed owed (.*?) will not be dispensed?")
    public void anyChangeOwedWillNotBeDispensed(String changeOwed) {

        getExpectedCoinsForChange(changeOwed);
        List<Coin> bucketCoinList = returnBucket.getCoin();
        int actualChangeDispensed = 0;
        for (Coin coin : bucketCoinList) {
            int returnCoinValue = coin.getDenomination();
            String returnCoinName = coin.getCoinName();
            actualChangeDispensed = actualChangeDispensed + returnCoinValue;
        }
        Assert.assertTrue("Item '" + itemToPurchase.getItemName() +
                "' - The Change owed '" + expectedTotalChangeAmountDue +
                "', does NOT match dispensed '" + actualChangeDispensed +
                "'.", expectedTotalChangeAmountDue != actualChangeDispensed);
    }

    // the <amount paid> will still be available
    @Then("^the (.*?) will still be available?")
    public void theAmountPaidWillStillBeAvailable(String coinsUsed) {
        long actualTotalAmountPaid = vendingMachine.getTotalAmountPaid();
        Assert.assertTrue("Expected Amount paid '" + expectedTotalAmountPaid + "' does not match actual amount '" + actualTotalAmountPaid + "' paid.", expectedTotalAmountPaid == actualTotalAmountPaid);
    }

    //Then the <amountPaid> will be returned
    @Then("^the (.*?) will be refunded?")
    public void theAmountPaidWillBeRefunded(String coinsUsed) {

        int actualChangeDispensed = 0;
        for (Coin coin : coinRefundedList) {
            int returnCoinValue = coin.getDenomination();
            String returnCoinName = coin.getCoinName();
            actualChangeDispensed += returnCoinValue;
        }
        Assert.assertTrue("Amount paid '" + expectedTotalAmountPaid +
                "', does not match the amount dispensed ' " + actualChangeDispensed +
                "'.", expectedTotalAmountPaid == actualChangeDispensed);
    }

    // the item will be dispensed
    // and any change owed will be dispensed
//    @Then("^the user will receive (.*?) and the change owed?")
//    public void theUserWillReceiveItemWithChangeOwed(String itemName, String changeOwed) {


    // -----------------------------------------------------------------------------------------------------------
    // ToDo: For a differernt feature in the future
    public void getVMBalances() {
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

    private void dtLoadVendingMachineStock(DataTable dt) {

        List<Map<String, String>> dtLine = dt.asMaps(String.class, String.class);
        for (int i = 0; i < dtLine.size(); i++) {
            //String inventoryType = dtLine.get(i).get("inventoryType").trim();
            String inventory = dtLine.get(i).get("inventory").trim();
            String amount = dtLine.get(i).get("amount").trim();
            int invAmount = Integer.parseInt(amount);
            if (getInventoryType(inventory).equals("coin")) {
                Coin coin = Coin.valueOf(inventory.toUpperCase());
                initialiseCoin(coin, invAmount);
            } else if (getInventoryType(inventory).equals("item")) {
                Item item = Item.valueOf(inventory.toUpperCase());
                initialiseItem(item, invAmount);
            } else {
                Assert.fail("** FEATURE DATA ERROR - Invalid Vending Machine Inventory Type: '" + inventory + "'.");
            }
        }
    }

    // ToDo:
    private void initialiseItem(Item item, int amount) {
        if (isValidItemName(item.getItemName().toUpperCase())) {
            vendingMachine.intialiseInventoryItem(item, amount);
            expectedCurrentTotalItems += amount;
        }
    }

    private void initialiseCoin(Coin coin, int amount) {
        if (isValidCoinName(coin.getCoinName().toUpperCase())) {
            vendingMachine.initialiseInventoryCoin(coin, amount);
            expectedCurrentTotalCoins += amount;
        }
    }
    //

    private String getInventoryType(String inventoryName) {
        String isValid = "";
        if (isValidItemName(inventoryName)) {
            isValid = "item";
        } else if (isValidCoinName(inventoryName)) {
            isValid = "coin";
        } else {
            isValid = "";
        }
        return isValid;
    }

    private boolean isValidItemName(String itemName) {
        boolean isValid = false;
        try {
            Item item = Item.valueOf(itemName.toUpperCase());
            isValid = true;
            ;
        } catch (IllegalArgumentException e) {
            //System.out.println("Not a valid Item: '" + itemName + "'.");
        }
        return isValid;
    }

    private boolean isValidCoinName(String coinName) {
        boolean isValid = false;
        try {
            Coin coin = Coin.valueOf(coinName.toUpperCase());
            isValid = true;
            ;
        } catch (IllegalArgumentException e) {
            //System.out.println("Not a valid Coin: '" + itemName + "'.");
        }
        return isValid;
    }


    private Item getItemObjectByName(String itemName) {
        try {
            return Item.valueOf(itemName.toUpperCase());
        } catch (Exception e) {
            Assert.assertTrue("Failed to get Item object for item name '" + itemName + "'.", false);
            return null;
        }
    }

    private Item getCoinObjectByName(String coinName) {
        try {
            return Item.valueOf(coinName.toUpperCase());
        } catch (Exception e) {
            Assert.assertTrue("Failed to get Coin object for coin name '" + coinName + "'.", false);
            return null;
        }
    }


    private boolean isValidItem(Item item) {
        boolean isValid = false;
        try {
            List<Item> list = Arrays.asList(Item.values());
            if (list.equals(item)) {
                isValid = true;
            }
        } catch (NullPointerException e) {
            Assert.assertTrue(e.getMessage(), false);

        } catch (IllegalArgumentException e) {
            Assert.assertTrue("Not a valid Item:" + item.getItemName(), false);
        }
        return isValid;
    }

    // ToDo:
    private boolean isValidCoin(Coin coin) {
        boolean isValid = false;
        try {
            List<Item> list = Arrays.asList(Item.values());
            if (list.equals(coin)) {
                isValid = true;
            }
        } catch (NullPointerException e) {
            Assert.assertTrue(e.getMessage(), false);

        } catch (IllegalArgumentException e) {
            Assert.assertTrue("Not a valid Item:" + coin.getCoinName(), false);
        }
        return isValid;
    }

    // ToDo:
//    private boolean isValidVMInventory(Coin coin){
//        String inventoryType = list.get(i).get("inventoryType").trim();
//        String inventory = list.get(i).get("inventory").trim();
//        String amount = list.get(i).get("amount").trim();
//        int invAmount = Integer.parseInt(amount);
//        if(inventoryType.equalsIgnoreCase("coin")){
//    }

    private int loadVendingMachineDenomination(Coin coin, int coinQty) {
        try {
            vendingMachine.initialiseInventoryCoin(coin, coinQty);
        } catch (IllegalArgumentException e) {
            Assert.fail("** FEATURE DATA ERROR - Invalid Coin :" + coin.getCoinName());
        }
        return 0;
    }

    private int loadVendingMachineItem(Item item, int itemQty) {
        try {
            vendingMachine.intialiseInventoryItem(item, itemQty);
        } catch (IllegalArgumentException e) {
            Assert.fail("*** FEATURE DATA ERROR - Invalid Item :" + item.getItemName());
        }
        return 0;
    }

    //ToDo: may need to nominate which exception we need
    private boolean acceptTransaction(boolean expectItem) {
        boolean isThereATransaction = false;
        if (expectItem) {
            // Product is expected
            returnBucket = vendingMachine.collectItemAndChange();
            return true;
        }

        if (!vendingMachine.isItemReadyForDispense()) {
            System.out.println("check");
            return false;
        }
        return false;
    }






    private void getItemForPurchase(String itemName){
        try{
            itemToPurchase = Item.valueOf(itemName.toUpperCase());
        }catch(IllegalArgumentException e){
            Assert.assertTrue("** FEATURE DATA ERROR - Item [" + itemName + "], does not exist", false);
        }

        try{
            vendingMachine.selectItemAndGetPrice(itemToPurchase);
        }catch(OutOfStockException e){
            Assert.assertTrue("Item [" + itemName + "], out of stock", false);
        }
    }

    private void getCoinsForPurchase(String amountPaid){

        // Group coins to be used for payment into a list - this will give us the total number of coins to enter
        String[] arrExpectedNumberOfCoinsForPayment = amountPaid.trim().split(",");
        expectedListOfCoinsForPayment = new ArrayList<Coin>();
        if(arrExpectedNumberOfCoinsForPayment.length == 0 ) {
            expectedCountOfCoinsToEnter = 0;
        }else {
            for (String c : arrExpectedNumberOfCoinsForPayment) {
                try{
                    Coin coin = Coin.valueOf(c.toUpperCase());
                    expectedListOfCoinsForPayment.add(coin); // Add to array list
                    expectedTotalAmountPaid += coin.getDenomination();
                }catch(IllegalArgumentException e){
                    Assert.assertFalse("**DATA INPUT ERROR - Trying to enter an invalid coin type: " +
                            c, true);
                }
            }
            expectedCountOfCoinsToEnter = expectedListOfCoinsForPayment.size();
        }
    }

    private void vmInsertCoins(List<Coin> expectedListOfCoinsForPayment){
        int totalValueOfCoinsToInsert = 0;
        int numberOfCoinsInserted = 0;
        for (Coin coinToInsert : expectedListOfCoinsForPayment) {
            vendingMachine.insertCoin(coinToInsert);
            numberOfCoinsInserted ++;
            totalValueOfCoinsToInsert += coinToInsert.getDenomination();
        }
    }

    private void getExpectedCoinsForChange(String changeOwed){

        String[] arrExpectedNumberOfCoinsOwed = changeOwed.trim().split(",");
        // if array empty or value = 0
        if(arrExpectedNumberOfCoinsOwed.length == 0 ) {
            expectedNumberOfCoinsForChange = 0;
        }else{
            List<Coin> listExpectedNumberOfCoinsOwed = new ArrayList<Coin>();
            for (String c : arrExpectedNumberOfCoinsOwed) {
                if(c.trim().equals("0") || c.trim().equals("")){
                }else{
                    try{
                        Coin coin = Coin.valueOf(c.toUpperCase());
                        listExpectedNumberOfCoinsOwed.add(coin);
                        expectedTotalChangeAmountDue += coin.getDenomination();
                    }catch(IllegalArgumentException e){
                        Assert.assertFalse("**DATA INPUT ERROR - Expecting change returned of an invalid coin type: " +
                                c, true);
                    }
                }
            }
        }
    }
}
