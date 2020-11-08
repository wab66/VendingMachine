package com.util;

import java.util.List;

public class ReturnBucket<Item, Coin> {
    private final Coin coin;
    //private final List<Coin> coin;
    private final Item item;

    public ReturnBucket(Item item, Coin coin){
        this.item = item;
        this.coin = coin;
    }

    public Item getItem(){
        return item;
    }

    public Coin getCoin(){
        return coin;
    }
}