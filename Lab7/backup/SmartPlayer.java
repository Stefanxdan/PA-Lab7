package com.company;

import java.util.List;
import java.util.Map;

public class SmartPlayer extends Player {

    int range;
    int llaaIndex;
    int llaaDif;

    List<Token> myTokens;
    List<Token> opponentTokens ;

    public SmartPlayer(String name) {
        super(name);
    }

    public void Set(int order, Game game, TimeKeeper timeKeeper){
        this.board = game.board;
        this.order = order;
        this.game = game;
        this.timeKeeper = timeKeeper;
        range = game.n/game.k;
    }

    protected int GetIndex() {

        int index;
        int value;
        if (order == 1)
        myTokens = tokenListPlayer
        // la inceputul jocului
        if(order == 1)
        {
            if(game.tokensPlayer1.size() == 0){
                return (int)( (Math.random() * board.tokenList.size()/3 ) + board.tokenList.size()/3);
            }
            else if(game.tokensPlayer1.size() == 1){
                value = game.tokensPlayer1.
                return (int)
            }


        }
        else
        {
            if(game.tokensPlayer2.size() == 0)
                return (int)( (Math.random() * board.tokenList.size()/3 ) + board.tokenList.size()/3);;
        }
    }
}