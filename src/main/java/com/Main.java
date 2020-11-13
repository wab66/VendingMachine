package com;

import com.domain.Coin;
import com.implementations.VendingMachineImpl;
import com.domain.Item;
import org.eclipse.jetty.io.ByteBufferPool;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
//        VendingMachineImpl vendingMachine = new VendingMachineImpl();
//
//        // Add stock
//        //dtLoadVendingMachineStock(dt);
//        Coin coin = coin.PENNY;
//        vendingMachine.addInventoryCoins(coin,10);
//        coin = coin.NICKEL;
//        vendingMachine.addInventoryCoins(coin,10);
//        coin = coin.DIME;
//        vendingMachine.addInventoryCoins(coin,10);
//        coin = coin.QUARTER;
//        vendingMachine.addInventoryCoins(coin,10);
//
//        Item inItem = inItem.COKE;
//        vendingMachine.addInventoryItems(inItem,10);
//        inItem = inItem.PEPSI;
//        vendingMachine.addInventoryItems(inItem,10);
//        inItem = inItem.SODA;
//        vendingMachine.addInventoryItems(inItem,10);
//
//        Item purchaseItem = Item.COKE;
//        long itemPrice = vendingMachine.selectItemAndGetPrice(purchaseItem);
//        System.out.println("Selected Item:" + purchaseItem.getItemName());
//        System.out.println("Selected Item Price:" + itemPrice);
//
//        // Purchase item - money paid > money owed
//        if (itemPrice != 0) {
//            //Coin coin insertedCoin = Coin.QUARTER;
//            List<Coin> listOfInsertedCoins = new ArrayList<Coin>();
//            listOfInsertedCoins.add(Coin.QUARTER);
//            listOfInsertedCoins.add(Coin.NICKEL);
//
//            // Insert Coins from list into the Vending Machine
//            Enumeration<Coin> e = Collections.enumeration(listOfInsertedCoins);
//            while(e.hasMoreElements()){
//                vendingMachine.insertCoin(e.nextElement());
//            }
//
//            // Collect selected item
//            vendingMachine.collectItemAndChange();
////
//
//        }
    }
//
//    public static void displayInsertedCoinValue(List<Coin> list) {
//        Optional<Integer> insertedCoinValue = list.stream().map(e -> e.getDenomination()).collect(Collectors.toList()).stream().reduce(Integer::sum);
//        int insertedValue = insertedCoinValue.get().intValue();
//        System.out.println("Return Coin value: " + insertedValue);
//    }
}