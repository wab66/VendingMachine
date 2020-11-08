package com.domain;

import java.util.EnumSet;
import java.util.Set;

public enum Item {
    COKE("Coca Cola", 25), PEPSI("Pepsi", 35), SODA("SODA", 45);

    String item;
    long price;

    Item(String item, long price) {
        this.item = item;
        this.price = price;
    }

    public String getItemName() {
        return item;
    }

    public long getPrice() {
        return price;
    }

//    public Set getEnumItemList(){
//        return  EnumSet.allOf( Coin.class );
//    }
//
//    public boolean isItemValid(String itemName){
//        Set coinsList = getEnumItemList();
//        return coinsList.equals(itemName.toUpperCase());
//    }
}
