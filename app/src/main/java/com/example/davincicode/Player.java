package com.example.davincicode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Player {
    private String name;
    ArrayList<Card> handPool = new ArrayList<>();
    boolean lost = false;
    // range from 0 to 2
    int hasHyphen = 0;

    Player() {}

    Player(String name) {
        this.name = name;
    }

    void sortHandPool() {
        Collections.sort(this.handPool, new Comparator<Card>() {
            @Override
            public int compare(Card card, Card t1) {
                if (card.index > t1.index) {
                    return 1;
                } else if (card.index < t1.index) {
                    return -1;
                }
                return 0;
            }
        });
    }

    void addAndSort(Card toBeAdded) {
        for (int i = 0; i < handPool.size(); i++) {
            if (toBeAdded.index > handPool.get(i).index) {
                handPool.add(i, toBeAdded);
            }
        }
    }
}
