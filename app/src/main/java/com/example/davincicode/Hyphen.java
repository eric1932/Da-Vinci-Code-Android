package com.example.davincicode;

public final class Hyphen extends Card {
    boolean isBlack;

    Hyphen(int index) {
        super(index);
//        this.number = -1;
        this.isBlack = (index == -1);
    }

    void setIndex(double index) {
        this.index = index;
    }
}
