package com.company;

public abstract class Player implements Runnable {
    String name;
    Board board;
    int order;
    Game game;
    TimeKeeper timeKeeper;

    public Player(String name) {
        this.name = name;
    }

    public void Set(int order, Game game, TimeKeeper timeKeeper){
        this.board = game.board;
        this.order = order;
        this.game = game;
        this.timeKeeper = timeKeeper;
    }

    protected abstract int GetIndex();

    @Override
    public void run() {
        synchronized (this) {
            while (!game.IsEnd() && timeKeeper.isAlive()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (game.turn != order)
                    if (game.IsEnd())
                        return;
                if (!game.IsEnd()) {
                    if (order == 1) {
                        game.tokensPlayer1.add(board.Extract(this.GetIndex()));
                        //System.out.println("player1 " + game.tokensPlayer1);
                    }
                    else {
                        game.tokensPlayer2.add(board.Extract((int) (Math.random() * board.tokenList.size())));
                        //System.out.println("player2 " + game.tokensPlayer2);
                    }
                    game.turn = 3 - order;
                }
                else
                    return;
            }
        }
    }
}
