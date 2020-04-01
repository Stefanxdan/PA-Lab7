package com.company;

public class RandomPlayer extends Player {

    public RandomPlayer(String name) {
        super(name);
    }

    protected int GetIndex() {
        return (int) (Math.random() * board.tokenList.size());
    }
}
