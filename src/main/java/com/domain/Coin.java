package com.domain;

import java.util.EnumSet;
import java.util.Set;

public enum Coin {
    PENNY("penny",1), NICKEL("nickel",5), DIME("dime",10), QUARTER("quarter",25);
    String coinName;
    int denomination;

    Coin(String coinName, int denomination){
        this.denomination = denomination;
        this.coinName = coinName;
    }

    public int getDenomination(){
        return denomination;
    }

    public String getCoinName(){
        return coinName;
    }
//
//    public Set getEnumCoinsList(){
//        return  EnumSet.allOf( Coin.class );
//    }
//
//    public boolean isCoinValid(String coinName){
//        Set coinsList = getEnumCoinsList();
//        return coinsList.equals(coinName.toUpperCase());
//    }
}
