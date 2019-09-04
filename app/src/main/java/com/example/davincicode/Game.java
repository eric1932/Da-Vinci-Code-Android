package com.example.davincicode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game {
    private ArrayList<Player> playerList;
    // [-2W, -1B, 0W, 1B, 2W, 3B...22W, 23B]
    private Card[] allCards = new Card[26];
    private ArrayList<Card> cardPool;
    Player winner;

    Game(int numOfPlayers) {
        // 创建所有牌和连字符的实例并存到 cardPool
        allCards[0] = new Hyphen(-2);
        allCards[1] = new Hyphen(-1);
        for (int i = 0; i < 24; i++) {
            allCards[i + 2] = new Card(i);
        }
        cardPool.addAll(Arrays.asList(allCards));

        // 新建玩家
        for (int i = 0; i < numOfPlayers; i++) {
            playerList.add(new Player());
        }

        // 每个人发 4 张手牌
        for (Player x: playerList) {
            for (int i = 0; i < 4; i++) {
                x.addAndSort(cardPool.remove(randInt(0, cardPool.size() - 1)));
            }
        }

        // 如果有人拿到 - (连字符)
        for (Player x: playerList) {
            for (Card y : x.getHandPool()) {
                if (y instanceof Hyphen) {
                    x.hasHyphen++;
                }
            }
        }
        for (Player x: playerList) {
            int index = 1;  // from UI Selection
            if (x.hasHyphen == 1) {
                Hyphen theHyphen = (Hyphen) x.popHandPool(0);
                //TODO UI Select
                setHyphen(x, theHyphen, index);
            } else if (x.hasHyphen == 2) {
                Hyphen smallerH = (Hyphen) x.popHandPool(0);
                Hyphen largerH = (Hyphen) x.popHandPool(0);
                //TODO UI Select
                setHyphen(x, smallerH, index);
                //TODO UI Select
                setHyphen(x, largerH, index);
            }
        }

        // 回合开始
        boolean endGame = false;
        while (!endGame) {
            for (Player x: playerList) {
                if (x.lost) {
                    continue;
                }
                // 抽一张牌
                Card tmp = cardPool.remove(randInt(0, cardPool.size() - 1));
                // 插入排序
                if (tmp instanceof Hyphen) {
                    // TODO UI Select
                    int index = 1;
                    setHyphen(x, (Hyphen) tmp, index);
                } else {
                    x.addAndSort(tmp);
                }
                // 猜
                boolean canGuess = true;
                while (canGuess) {
                    // TODO UI Select
                    // 不能是自己
                    Player playerToBeGuessed = playerList.get(0);
                    int guessIndex = 0, guessNumber = 0;

                    boolean hit = guess(x, playerToBeGuessed, guessIndex, guessNumber, tmp);
                    if (hit) {
                        // Win or other lose
                        endGame = updatePlayerStatus();
                        if (endGame) {
                            break;
                        }
                        // TODO UI Select
                        boolean continueToGuess = false;
                        canGuess = continueToGuess;
                    } else {
                        canGuess = false;
                    }
                }
                if (endGame) {
                    break;
                }
            }
        }

        // TODO 赢了之后的操作
    }

    /**
     * 设置某个玩家的连字符的位置到某个 index 的右侧
     * @param player 玩家
     * @param hyphenCard 白/黑连字符
     * @param index 右侧增加连字符 (连字符自增 0.25)
     */
    void setHyphen(Player player, Hyphen hyphenCard, double index) {
        //TODO
        if (index == -999) {
            hyphenCard.setIndex(player.getHandPool(0).index - 0.5);
            player.addAndSort(hyphenCard);
        } else {
            hyphenCard.setIndex(index + 0.25);
            player.addAndSort(hyphenCard);
        }
    }

    boolean guess(Player currentPlayer, Player playerToBeGuessed, int guessIndex, int guessNumber,
                  Card justDrawnCard) {
        Card cardToBeGuessed = playerToBeGuessed.getHandPool(guessIndex);
        // 连字符是 -1，不影响
        if (cardToBeGuessed.getNumber() == guessNumber) {
            cardToBeGuessed.setVisibleToAll();
            return true;
        } else {
            justDrawnCard.setVisibleToAll();
            return false;
        }
    }

    boolean updatePlayerStatus() {
        int lostCount = 0;
        for (Player x: playerList) {
            if (x.lost) {
                lostCount++;
                continue;
            }
            boolean mayLose = true;
            for (Card y: x.getHandPool()) {
                if (!y.isVisibleToAll()) {
                    mayLose = false;
                    break;
                }
            }
            x.lost = mayLose;
            if (mayLose) {
                lostCount++;
            }
        }
        if (lostCount == playerList.size() - 1) {
            for (Player x: playerList) {
                if (!x.lost) {
                    winner = x;
                    return true;
                }
            }
        }
        return false;
    }

    private static int randInt(int min, int max) {
        // https://github.com/giantray/stackoverflow-java-top-qa/blob/master/contents/generating-random-integers-in-a-range-with-Java.md
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
