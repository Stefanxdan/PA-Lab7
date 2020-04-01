package com.company;

import java.util.ArrayList;
import java.util.List;

public class SmartPlayer extends Player {

    int range;
    int llaaIndex;
    int llaaDif;

    List<Token> myTokens;
    List<Token> opponentTokens;

    public SmartPlayer(String name) {
        super(name);
    }


    public void Set(int order, Game game, TimeKeeper timeKeeper) {
        this.board = game.board;
        this.order = order;
        this.game = game;
        this.timeKeeper = timeKeeper;
        range = game.n / game.k;
        tokensSet.clear();
    }

    int NextBackLLAPValue() {
        // Create a table and initialize all
        // values as 2. The value ofL[i][j] stores
        // LLAP with set[i] and set[j] as first two
        // elements of AP. Only valid entries are
        // the entries where j>i
        int n = myTokens.size();
        int[][] L = new int[n][n];

        int llap = 2;
        int llapi = 0, diff = 1;

        for (int i = 0; i < n; i++)
            L[i][n - 1] = 2;

        for (int j = n - 2; j >= 1; j--) {
            int i = j - 1, k = j + 1;
            while (i >= 0 && k <= n - 1) {
                if (myTokens.get(i).getNumber() + myTokens.get(k).getNumber() < 2 * myTokens.get(j).getNumber())
                    k++;
                else if (myTokens.get(i).getNumber() + myTokens.get(k).getNumber() > 2 * myTokens.get(j).getNumber()) {
                    L[i][j] = 2;
                    i--;

                } else {
                    L[i][j] = L[j][k] + 1;
                    if (llap < L[i][j]) {
                        llap = L[i][j];
                        diff = myTokens.get(j).getNumber() - myTokens.get(i).getNumber();
                        llapi = i;
                    }
                    i--;
                    k++;
                }
            }
            while (i >= 0) {
                L[i][j] = 2;
                i--;
            }
        }
        int number = myTokens.get(llapi).getNumber();
        return number - diff;
    }

    private int NextFrontLLAPValue() {
        // Create a table and initialize all
        // values as 2. The value ofL[i][j] stores
        // LLAP with set[i] and set[j] as first two
        // elements of AP. Only valid entries are
        // the entries where j>i
        int n = myTokens.size();
        int[][] L = new int[n][n];

        int llap = 2;
        int llapi = 0, diff = 1;

        for (int i = 0; i < n; i++)
            L[i][n - 1] = 2;

        for (int j = n - 2; j >= 1; j--) {
            int i = j - 1, k = j + 1;
            while (i >= 0 && k <= n - 1) {
                if (myTokens.get(i).getNumber() + myTokens.get(k).getNumber() < 2 * myTokens.get(j).getNumber())
                    k++;
                else if (myTokens.get(i).getNumber() + myTokens.get(k).getNumber() > 2 * myTokens.get(j).getNumber()) {
                    L[i][j] = 2;
                    i--;

                } else {
                    L[i][j] = L[j][k] + 1;
                    if (llap < L[i][j]) {
                        llap = L[i][j];
                        diff = myTokens.get(j).getNumber() - myTokens.get(i).getNumber();
                        llapi = i;
                    }
                    i--;
                    k++;
                }
            }
            while (i >= 0) {
                L[i][j] = 2;
                i--;
            }
        }
        int number = myTokens.get(llapi).getNumber();
        return number + diff * llap;
    }

    protected int GetIndex() {

        int index = 0;
        int value;
        myTokens = new ArrayList<>(tokensSet);
        if (order == 1)
            opponentTokens = new ArrayList<>(game.player2.tokensSet);
        else
            opponentTokens = new ArrayList<>(game.player1.tokensSet);
        // la inceputul jocului
        if (myTokens.size() == 0)
            return (int) ((Math.random() * board.tokenList.size() / 3) + board.tokenList.size() / 3);
        // a 2a piesa
        if (myTokens.size() == 1) {
            value = myTokens.get(0).getNumber();
            value += (-1) * ((int) (Math.random() * 2)) * ((int) (Math.random() * range) + 1);
            if (opponentTokens.size() != 0)
                if (opponentTokens.get(0).getNumber() == value) {
                    if (value > 0) value++;
                    else value--;
                }
            int i = 0;
            while (i < board.tokenList.size()) {
                if(value == board.tokenList.get(i).getNumber())
                {index = i;}
                i++;
            }
                /*
            System.out.println(value + " " + board.tokenList.size() );
            while (value != board.tokenList.get(i).getNumber() && i-1 < board.tokenList.size())
                System.out.println(board.tokenList.get(i).getNumber() + " " + i++ );
                 */
            System.out.println(value + " " + index);
            return index;
        }
        // add backLLAP
        value = NextBackLLAPValue();
        int i = 0;
        while (value != board.tokenList.get(i).getNumber() && i + 1 < board.tokenList.size())
            i++;
        if (i < game.board.tokenList.size())
            return i;
        // add frontLLAP
        value = NextFrontLLAPValue();
        i = 0;
        while (value != board.tokenList.get(i).getNumber() && i < game.board.tokenList.size())
            i++;
        if (i < game.board.tokenList.size())
            return i;
        return (int) (Math.random() * board.tokenList.size());
    }
}