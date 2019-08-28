package com.example.davincicode;

class Card {
    int number;
    double index;  // White: -2, 0, 2...22; Black: -1, 1, 3...23
    private boolean visibilityToAll = false;
    private Player belongTo;

    Card(int number, boolean isBlack) {
        this.number = number;
        if (isBlack) {
            index = number * 2 + 1;
        } else {
            index = number * 2;
        }
    }

    Card(int index) {
        this.index = index;
        this.number = ((index + 2) / 2) - 1;
    }

    boolean isBlack() {
        return this.index % 2 != 0;
    }
}
