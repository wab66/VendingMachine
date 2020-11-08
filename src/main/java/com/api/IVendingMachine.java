package com.api;

import com.domain.Coin;
import com.domain.Item;
import com.util.ReturnBucket;

import java.util.List;

public interface IVendingMachine {
    long selectItemAndGetPrice(Item item);
    void insertCoin(Coin coin);
    List<Coin> refund();
    ReturnBucket<Item, List<Coin>> collectItemAndChange();
    void reset();
}
