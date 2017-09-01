package com.frans.lifegame.ui;

import java.awt.Color;

import javax.swing.JButton;


public class CellButton extends JButton{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static final Color SURVIVAL_COLOR = Color.GREEN;
    public static final Color DEATH_COLOR = Color.BLACK;
    public CellButton() {
        setSize(10, 10);
        setBackground(DEATH_COLOR);
    }
}
