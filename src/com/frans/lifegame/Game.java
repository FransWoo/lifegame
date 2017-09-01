package com.frans.lifegame;

import java.util.Random;


public class Game implements Runnable{
    
    private Cell[][] cells;
    private int width;
    private int height;
    private boolean run;
    private int interval;
    private StartListener listener;
    
    public Game(int width, int height, int interval) {
        this.width = width;
        this.height = height;
        this.run = true;
        this.interval = interval;
        cells = new Cell[width][height];
    }
    
    public Game init() {
        return this;
    }
    
    public Game randomInit(int probability) {
        Random rand = new Random();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new Cell();
                if (probability >= rand.nextInt(100)) {
                    cells[i][j].setStatus(Cell.LifeStatus.SURVIVAL);
                }
            }
        }
        return this;
    }
    
    public void setInterval(int interval) {
        this.interval = interval;
    }
    
    public int getNeighbor(int x, int y) {
        int sum = 0;
        for (int i = x - 1; i <= x + 1; ++i) {
            for (int j = y - 1; j <= y + 1; ++j) {
                if (i >= 0 && i < width && j >= 0 && j < height
                        && !(i == x && j == y) 
                        && cells[i][j].getStatus() == Cell.LifeStatus.SURVIVAL) {
                    ++sum;
                }
            }
        }
        return sum;
    }

    public void setGameListener(StartListener listener) {
        this.listener = listener;
    }
    
    public void run() {
        while (run) {
            for (int j = 0; j < height; ++j) {
                for (int i = 0; i < width; ++i){
                    int num = getNeighbor(i, j);
                    if (num == 2)
                        cells[i][j].setNextStatus(cells[i][j].getStatus());
                    else if (num == 3)
                        cells[i][j].setNextStatus(Cell.LifeStatus.SURVIVAL);
                    else
                        cells[i][j].setNextStatus(Cell.LifeStatus.DEATH);
                }
            }
            for (int j = 0; j < height; ++j) {
                for (int i = 0; i < width; ++i) {
                    cells[i][j].evolve();
                }
            }
            if (listener != null)
                listener.gameRun(cells, run);
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void setRun(boolean run) {
        this.run = run;
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }



    public interface StartListener{
        public void gameRun(final Cell[][] cells, boolean run);
    }
    
    public static void main(String[] args) {
        Game game = new Game(10, 10, 500);
        game.randomInit(50);
        game.setGameListener((cells, run) -> {
            for (int i = 0; i < game.getWidth(); ++i) {
                for (int j = 0; j < game.getHeight(); ++j) {
                    System.out.print(cells[i][j].getStatus() == Cell.LifeStatus.SURVIVAL ?
                            " " : "@");
                }
                System.out.println();
            }
        });
        new Thread(game).start();
    }
}
