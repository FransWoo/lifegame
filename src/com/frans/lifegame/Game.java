package com.frans.lifegame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author franswoo
 *
 */
public class Game implements Runnable{
    
    private Cell[][] cells;
    private int width;
    private int height;
    private volatile boolean run;
    private int interval;
    
    private CellChangeListener cellChangeListener;
    private CellListener cellListener;
    
    public Game(int width, int height, int interval) {
        this.width = width;
        this.height = height;
        this.run = true;
        this.interval = interval;
        cells = new Cell[width][height];
    }
    
    /**
     * 初始化全部cell为death
     * @return this
     */
    public Game init() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new Cell(i, j);
                cells[i][j].setStatus(Cell.LifeStatus.DEATH);
            }
        }
        return this;
    }
    
    /**
     * @param x
     * @param y
     * @return 坐标中的cell
     */
    public Cell getCellByXY(int x, int y) {
        return cells[x][y];
    }
    
    /**
     * 随机初始化
     * @param probability
     * @return
     */
    public Game randomInit(int probability) {
        Random rand = new Random();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (cells[i][j] == null)
                    cells[i][j] = new Cell(i, j);
                if (probability >= rand.nextInt(100)) {
                    cells[i][j].setStatus(Cell.LifeStatus.SURVIVAL);
                } else {
                    cells[i][j].setStatus(Cell.LifeStatus.DEATH);
                }
            }
        }
        return this;
    }
    
    /**
     * 设置每隔多少毫秒运行一次
     * @param interval
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }
    
    /**
     * @param x 
     * @param y
     * @return 生存的邻居数量
     */
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

    public void setCellChangeListener(CellChangeListener cellChangeListener) {
        this.cellChangeListener = cellChangeListener;
    }

    public void setCellListener(CellListener cellListener) {
        this.cellListener = cellListener;
    }
    
    public void run() {
        while (true) {
            if (run) {
                List<Cell> changeCells = new ArrayList<Cell>();
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
                        if (cells[i][j].evolve())
                            changeCells.add(cells[i][j]);
                    }
                }
                if (cellListener != null)
                    cellListener.cellArray(cells, run);
                if (cellChangeListener != null)
                    cellChangeListener.cellChange(changeCells, run);
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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


    /**
     * @author franswoo
     * 监听所有的cells
     *
     */
    @FunctionalInterface
    public interface CellListener{
        public void cellArray(final Cell[][] cells, boolean run);
    }
    
    /**
     * @author franswoo
     * 监听改变过的cells
     *
     */
    @FunctionalInterface
    public interface CellChangeListener{
        public void cellChange(final List<Cell> changeCells, boolean run);
    }
    
    public static void main(String[] args) {
        Game game = new Game(10, 10, 500);
        game.randomInit(50);
        game.setCellListener((cells, run) -> {
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
