package com.domain;

import java.util.HashMap;
import java.util.Map;

public class Inventory<T> {
    private final Map<T, Integer> inventory = new HashMap<T, Integer>();

    public int getQuantity(T item){
        Integer value = inventory.get(item);
        return value == null? 0 : value ;
    }

    //rc01a - ToDo - maybe do in impl
    public Map<T, Integer> getItemsList(){
        return inventory;
    }

    public int getInventoryTotal(){
        int totalItemQty = 0;
        for (Map.Entry<T, Integer> entry : inventory.entrySet()) {
            totalItemQty += entry.getValue();
        }
        //Integer value = inventory.get(item);
        //return value == null? 0 : value ;
        return totalItemQty;
    }

    public void add(T item){
        int count = inventory.get(item);
        inventory.put(item, count+1);
    }

    public void deduct(T item) {
        if (hasItem(item)) {
            int count = inventory.get(item);
            inventory.put(item, count - 1);
        }
    }

    public boolean hasItem(T item){
        return getQuantity(item) > 0;
    }

    public void clear(){
        inventory.clear();
    }

    public void put(T item, int quantity) {
        inventory.put(item, quantity);
    }
}
