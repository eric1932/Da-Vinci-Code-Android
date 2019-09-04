package com.example.davincicode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Player {
    private String name;
    private ArrayList<Card> handPool = new ArrayList<>();
    boolean lost = false;
    // range: [0,2]
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
                return;
            }
        }
        handPool.add(toBeAdded);
    }

    ArrayList<Card> getHandPool() {
        return new ArrayList<>(handPool);
    }

    Card getHandPool(int index) {
        return handPool.get(index);
    }

    Card popHandPool(int index) {
        return handPool.remove(index);
    }
}
