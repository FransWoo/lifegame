package com.frans.lifegame;

public class Cell {
    private LifeStatus status;
    private LifeStatus nextStatus;
    
    public Cell() {}

    public void evolve() {
        status = nextStatus;
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

    public enum LifeStatus{
        SURVIVAL,
        DEATH,
    }
}

