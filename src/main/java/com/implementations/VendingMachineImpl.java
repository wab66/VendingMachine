package com.implementations;

import com.api.IVendingMachine;
import com.domain.Coin;
import com.domain.Inventory;
import com.domain.Item;
import com.exceptions.InsufficientChangeException;
import com.exceptions.InsufficientPaymentException;
import com.exceptions.OutOfStockException;
import com.util.ReturnBucket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

//
// import sun.jvm.hotspot.oops.ReceiverTypeData;

public class VendingMachineImpl implements IVendingMachine {

    final Inventory<Coin> cashInventory = new Inventory<>();
    final Inventory<Item> itemInventory = new Inventory<>();
    private long totalSales;
    private Item itemToDispense;
    private long totalAmountPaid;

    public VendingMachineImpl(){
        initialiseVendingMachine();
    }

    public Map<Coin,Integer> getAllInventoryCoinsBalances(){
        return cashInventory.getItemsList();
    }

    public Map<Item,Integer> getAllInventoryItemsBalances(){
        return itemInventory.getItemsList();
    }

    public boolean isItemReadyForDispense(){
        boolean result = true;
        try {
            if (itemToDispense.equals(null)) {
                result = false;
            }
        } catch (NullPointerException e) {
            result = false;
        }
        return result;
    }

    public Item getItemToDispense(){
        return itemToDispense;
    }

    //rc01a
    public int getNominatedInventoryCoinBalance(String coinName){
        Coin coin = Coin.valueOf(coinName.toUpperCase());
        return (cashInventory.getQuantity(coin));
    }

    //rc01a
    public int getNominatedInventoryItemBalance(String itemName){
        Item item = Item.valueOf(itemName.toUpperCase());
        return (itemInventory.getQuantity(item));
    }

    //rc01a
    public int getInventoryCoinsBalance(){
        return (cashInventory.getInventoryTotal());
    }

    //rc01a
    public int getInventoryItemsBalance(){
        return (itemInventory.getInventoryTotal());
    }

    //rc01a
    public void initialiseInventoryCoin(Coin coin, int coinQty){
        cashInventory.put(coin, coinQty);
    }

    public void intialiseInventoryItem(Item item, int itemQty){
        itemInventory.put(item, itemQty);
    }

//    private boolean isItemToDispenseEmpty() {
//        boolean result = false;
//        try {
//            if (itemToDispense.equals(null)) {
//                result = true;
//            }
//        } catch (NullPointerException e) {
//            result = true;
//        }
//        return result;
//    }

    private void initialiseVendingMachine(){
        //initialize machine with 5 coins of each denomination
        //and 5 cans of each Item
        for(Coin c : Coin.values()){
            cashInventory.put(c, 0);
        }

        for(Item i : Item.values()){
            itemInventory.put(i, 0);
        }
    }

    @Override
    public long selectItemAndGetPrice(Item item) {
        if(itemInventory.hasItem(item)){
            itemToDispense = item;
            return itemToDispense.getPrice();
        }
        throw new OutOfStockException("Sold Out, Please buy another item");
    }

    @Override
    public void insertCoin(Coin coin) {
        totalAmountPaid = totalAmountPaid + coin.getDenomination();
        cashInventory.add(coin);
    }

    @Override
    public ReturnBucket<Item, List<Coin>> collectItemAndChange() {
        Item itemToDispense = collectItem();
        totalSales = totalSales + this.itemToDispense.getPrice();

        List<Coin> changeToDispense = collectChange();
        // rc01a -  it was in the coins method this will empty the item
        dispenseItem();

        //rc01m
        //return new ReturnBucket<Item, List<Coin>>(item, change);
        return new ReturnBucket<Item, List<Coin>>(itemToDispense, changeToDispense);
    }

    private Item collectItem() throws InsufficientChangeException, InsufficientPaymentException {
        if(isFullPaid()) {
            if(hasSufficientChange()) {
                itemInventory.deduct(itemToDispense);
                return itemToDispense;
            }
            throw new InsufficientChangeException("Insufficient change in Inventory");

        }
        long remainingBalance = itemToDispense.getPrice() - totalAmountPaid;
        throw new InsufficientPaymentException("Item '" + itemToDispense.getItemName() + "' purchased for Price '" + itemToDispense.getPrice() + "' not fully paid, remaining : ", remainingBalance);
    }

    //rc01a
    private void dispenseItem(){
        itemToDispense = null;
        //return currentItem;
    }
    //rc01a

    //rc01a
    public long getTotalAmountPaid(){
        return totalAmountPaid;
    }

    private List<Coin> collectChange() {
        long changeAmount = totalAmountPaid - itemToDispense.getPrice();
        List<Coin> change = getChange(changeAmount);
        updateCashInventory(change);
        totalAmountPaid = 0;
        //rc01d
        //currentItem = null;
        return change;
    }

    @Override
    public List<Coin> refund(){
        List<Coin> refund = getChange(totalAmountPaid);
        updateCashInventory(refund);
        totalAmountPaid = 0;
        itemToDispense = null;
        return refund;
    }


    private boolean isFullPaid() {
        return totalAmountPaid >= itemToDispense.getPrice();
    }


    private List<Coin> getChange(long amount) throws InsufficientChangeException{
        List<Coin> changeToDispense = Collections.EMPTY_LIST;

        if(amount > 0){
            changeToDispense = new ArrayList<Coin>();
            long balance = amount;
            while(balance > 0){
                if(balance >= Coin.QUARTER.getDenomination()
                        && cashInventory.hasItem(Coin.QUARTER)){
                    changeToDispense.add(Coin.QUARTER);
                    balance = balance - Coin.QUARTER.getDenomination();

                }else if(balance >= Coin.DIME.getDenomination()
                        && cashInventory.hasItem(Coin.DIME)) {
                    changeToDispense.add(Coin.DIME);
                    balance = balance - Coin.DIME.getDenomination();

                }else if(balance >= Coin.NICKEL.getDenomination()
                        && cashInventory.hasItem(Coin.NICKEL)) {
                    changeToDispense.add(Coin.NICKEL);
                    balance = balance - Coin.NICKEL.getDenomination();

                }else if(balance >= Coin.PENNY.getDenomination()
                        && cashInventory.hasItem(Coin.PENNY)) {
                    changeToDispense.add(Coin.PENNY);
                    balance = balance - Coin.PENNY.getDenomination();

                }else{
                    throw new InsufficientChangeException("Insufficient Change please try another product");
                }
            }
        }

        return changeToDispense;
    }

    @Override
    public void reset(){
        cashInventory.clear();
        itemInventory.clear();
        totalSales = 0;
        itemToDispense = null;
        totalAmountPaid = 0;
    }

    public void printStats(){
        System.out.println("Total Sales : " + totalSales);
        System.out.println("Current Item Inventory : " + itemInventory);
        System.out.println("Current Cash Inventory : " + cashInventory);
    }


    private boolean hasSufficientChange(){
        return hasSufficientChangeForAmount(totalAmountPaid - itemToDispense.getPrice());
    }

    private boolean hasSufficientChangeForAmount(long amount){
        boolean hasChange = true;
        try{
            getChange(amount);
        }catch(InsufficientChangeException nsce){
            return hasChange = false;
        }

        return hasChange;
    }

    private void updateCashInventory(List<Coin> change) {
        for(Coin c : change){
            cashInventory.deduct(c);
        }
    }

    public long getTotalSales(){
        return totalSales;
    }

}