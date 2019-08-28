package com.example.davincicode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game {
    private ArrayList<Player> playerList;
    // [-2W, -1B, 0W, 1B, 2W, 3B...22W, 23B]
    private Card[] allCards = new Card[26];
    private ArrayList<Card> cardPool;

    Game(int numOfPlayers) {
        allCards[0] = new Hyphen(-2);
        allCards[1] = new Hyphen(-1);
        for (int i = 0; i < 24; i++) {
            allCards[i + 2] = new Card(i);
        }
        cardPool.addAll(Arrays.asList(allCards));

        for (int i = 0; i < numOfPlayers; i++) {
            playerList.add(new Player());
        }

        // 每个人发 4 张手牌
        for (Player x: playerList) {
            for (int i = 0; i < 4; i++) {
                x.addAndSort(cardPool.remove(randInt(0, cardPool.size() - 1)));
            }
        }

        // 如果有人拿到 -
        for (Player x: playerList) {
            for (Card y: x.handPool) {
                if (y instanceof Hyphen) {
                    x.hasHyphen++;
                }
            }
        }
    }

    /**
     * 设置某个玩家的连字符的位置到某个 index 的右侧
     * @param player 玩家
     * @param hyphenCard 白/黑连字符
     * @param index 右侧增加连字符
     */
    void setHyphen(Player player, Card hyphenCard, int index) {
        //TODO
    }

    private static int randInt(int min, int max) {
        // https://github.com/giantray/stackoverflow-java-top-qa/blob/master/contents/generating-random-integers-in-a-range-with-Java.md
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
