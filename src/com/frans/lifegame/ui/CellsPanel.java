package com.frans.lifegame.ui;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class CellsPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int rows;
    private int cols;
    
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public CellsPanel(int rows, int cols) {
        setLayout(new GridLayout(rows, cols, 0, 0));
        this.rows = rows;
        this.cols = cols;
    }
    
    public Component getComponentAtXY(int x, int y) {
       return getComponents()[x + y * cols];
    }
}
