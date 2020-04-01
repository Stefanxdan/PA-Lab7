package com.company;

public class Token implements Comparable {
    private int number;

    public Token(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }

    @Override
    public int compareTo(Object o) {
        Token t = (Token) o;
        if (this.getNumber() > t.getNumber())
            return 1;
        if (this.getNumber() == t.getNumber())
            return 0;
        return -1;
    }
}
