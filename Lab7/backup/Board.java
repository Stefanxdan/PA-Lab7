package com.company;

import java.util.ArrayList;
import java.util.List;

public class Board {
    List<Token> tokenList = new ArrayList<>();

    public Board( int n)
    {
        tokenList.clear();
        for( int i = 1; i <=n; i++)
            tokenList.add(new Token(i));
    }

    public synchronized Token Extract(int index)
    {
        return tokenList.remove(index);
    }

    public boolean EmptyList() {
        return tokenList.isEmpty();
    }

    @Override
    public String toString() {
        return "Board{" +
                "tokenList=" + tokenList +
                '}';
    }
}
