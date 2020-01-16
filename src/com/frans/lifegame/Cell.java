package com.frans.lifegame;

/**
 * @author franswoo
 *
 */
public class Cell {
    private LifeStatus status;
    private LifeStatus nextStatus;
    private int x;
    private int y;
    
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 使细胞进化到下一个状态
     * @return 状态是否改变过
     */
    public boolean evolve() {
        if (status == nextStatus)
            return false;
        status = nextStatus;
        return true;
    }
    
    public LifeStatus getNextStatus() {
        return nextStatus;
    }

    public void setNextStatus(LifeStatus nextStatus) {
        this.nextStatus = nextStatus;
    }

    public void setStatus(LifeStatus status) {
        this.status = status;
    }
    
    public LifeStatus getStatus() {
        return status;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public enum LifeStatus{
        SURVIVAL,
        DEATH,
    }
}

