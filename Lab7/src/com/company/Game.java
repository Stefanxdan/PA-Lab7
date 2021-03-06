package com.company;

import com.sun.org.apache.bcel.internal.generic.ObjectType;

import java.util.Set;
import java.util.TreeSet;

public class Game {
    int n, k;
    int turn = 1;
    int timeLimit;
    Board board;
    Player player1;
    Player player2;
    TimeKeeper timeKeeper;
    int scorePlayer1 = 0;
    int scorePlayer2 = 0;

    public Game(int n, int k, Player player1, Player player2 , int timeLimit) {
        this.n = n;
        this.k = k;
        board = new Board(n);
        this.timeLimit= timeLimit;
        timeKeeper = new TimeKeeper("Tk", timeLimit);
        this.player1 = player1;
        this.player2 = player2;
        this.player1.Set(1, this, timeKeeper);
        this.player2.Set(2, this, timeKeeper);
        System.out.println(this);
    }

    // afiseaza cea mai lunga progresie aritmetica
    void DisplayLLAP(Player player) {
        // Create a table and initialize all
        // values as 2. The value ofL[i][j] stores
        // LLAP with set[i] and set[j] as first two
        // elements of AP. Only valid entries are
        // the entries where j>i
        int n = player.tokensSet.size();
        int[][] L = new int[n][n];
        Token[] set = player.tokensSet.toArray(new Token[0]);

        int llap = 2;
        int llapi = 0, diff = 1;

        for (int i = 0; i < n; i++)
            L[i][n - 1] = 2;

        for (int j = n - 2; j >= 1; j--) {
            int i = j - 1, k = j + 1;
            while (i >= 0 && k <= n - 1) {
                if (set[i].getNumber() + set[k].getNumber() < 2 * set[j].getNumber())
                    k++;
                else if (set[i].getNumber() + set[k].getNumber() > 2 * set[j].getNumber()) {
                    L[i][j] = 2;
                    i--;

                } else {
                    L[i][j] = L[j][k] + 1;
                    if (llap < L[i][j]) {
                        llap = L[i][j];
                        diff = set[j].getNumber() - set[i].getNumber();
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
        System.out.print(Thread.currentThread().getName() + ": LLAP " + player.name + " AP(" + llap + "): ");
        int number = set[llapi].getNumber();
        for (int i = 0; i < llap; i++) {
            System.out.print(number + " ");
            number += diff;
        }
        System.out.println();
    }

    // returneaza lungimea celei mai lungi progrese aritmtice ( LLAP Length of the Longest Arithmetic Progression)
    synchronized public int LLAP(Set<Token> tokensPlayer) {
        int n = tokensPlayer.size();
        int[][] L = new int[n][n];
        Token[] set = tokensPlayer.toArray(new Token[0]);

        int llap = 2;

        for (int i = 0; i < n; i++)
            L[i][n - 1] = 2;

        for (int j = n - 2; j >= 1; j--) {
            int i = j - 1, k = j + 1;
            while (i >= 0 && k <= n - 1) {
                if (set[i].getNumber() + set[k].getNumber() < 2 * set[j].getNumber())
                    k++;
                else if (set[i].getNumber() + set[k].getNumber() > 2 * set[j].getNumber()) {
                    L[i][j] = 2;
                    i--;

                } else {
                    L[i][j] = L[j][k] + 1;
                    if (llap < L[i][j]) {
                        llap = L[i][j];
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
        return llap;
    }

    // verifia daca s-au terminat numerele de tabla sau daca s-a castigat jocul
    public boolean IsEnd() {
        if (board.EmptyList())
            return true;

        // daca playerul1 are mai multe piese decat player2 atunci verific daca player1 a castigat
        if (player1.tokensSet.size() > player2.tokensSet.size()) {
            if (player1.tokensSet.size() < k)
                return false;
            return (LLAP(player1.tokensSet) >= k);
        } else { /// altfel: player1 si player2 au acelasi numar de token, verific daca castigat
            if (player1.tokensSet.size() < k)
                return false;
            return (LLAP(player1.tokensSet) >= k);
        }
    }

    // se ocupa de afisarile de la sf jocului
    private void EndGame() {
        System.out.println("End Game: ");
        System.out.println("Board: " + board);
        System.out.println("tokensPlayer1=" + player1.tokensSet);
        System.out.println("tokensPlayer2=" + player2.tokensSet);

        if (LLAP(player1.tokensSet) >= k) {
            System.out.print("Winner:");
            DisplayLLAP(player1);
            scorePlayer1 += n;
        } else if (LLAP(player2.tokensSet) >= k) {
            System.out.print("Winner:");
            DisplayLLAP(player2);
            scorePlayer2 += n;
        } else {
            System.out.println("No winner");
            scorePlayer1 += LLAP(player1.tokensSet);
            scorePlayer2 += LLAP(player2.tokensSet);
            DisplayLLAP(player1);
            DisplayLLAP(player2);
        }
        System.out.println("Scor player1: " + scorePlayer1);
        System.out.println("Scor player2: " + scorePlayer2);
    }

    public void Simulate() throws InterruptedException {

        System.out.println( Thread.currentThread().getName() + ": simulating...");
        timeKeeper.start(); // optional
        Thread t1 = new Thread(player1, "First Thread");
        t1.start();
        Thread t2 = new Thread(player2, "Second Thread");
        t2.start();
        System.out.println(Thread.currentThread().getName() + ": ...wainting");
        // astept terminare joclui prin terminare threadurilor
        t1.join();
        t2.join();
        //optional
        if(!timeKeeper.isAlive())
            System.out.println(Thread.currentThread().getName() + ": Joc Oprit");
        timeKeeper.running = false;
        //
        EndGame();


    }

    @Override
    public String toString() {
        return "\nGame{ n=" + n + ", " + player1.name + ", " + player2.name + ", Time limit=" + timeLimit + "}";
    }
}
