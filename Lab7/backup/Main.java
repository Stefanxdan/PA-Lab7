package com.company;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Player player1 = new RandomPlayer("player1");
        Player player2 = new RandomPlayer("player2");
        while (true) {
            Game game = new Game(150, 12, player1, player2,1);
            game.Simulate();
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
