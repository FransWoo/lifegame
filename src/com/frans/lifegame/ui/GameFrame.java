package com.frans.lifegame.ui;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.frans.lifegame.Cell;
import com.frans.lifegame.Game;

public class GameFrame extends JFrame {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Game game;
    private CellsPanel leftPanel;
    private JPanel rightPanel;
    private JButton startBtn;
    private JButton pauseBtn;
    private Thread gameThread;
    private int width = 70;
    private int height = 70;
    
    public GameFrame() {
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        game = new Game(width, height, 100).randomInit(50);
        game.setGameListener((cells, run) -> {
            for (int i = 0; i < game.getWidth(); ++i) {
                for (int j = 0; j < game.getHeight(); ++j) {
                    if (cells[i][j].getStatus() == Cell.LifeStatus.SURVIVAL) {
                        leftPanel.getComponentAtXY(i, j).setBackground(CellButton.SURVIVAL_COLOR);
                    } else {
                        leftPanel.getComponentAtXY(i, j).setBackground(CellButton.DEATH_COLOR);
                    }
                        
                }
            }
            leftPanel.updateUI();
        });
        gameThread = new Thread(game);
        initUI();
    }
    
    public void startGame() {
        gameThread.start();
    }
    
    public void pauseGame() {
        game.setRun(false);
    }
    
    public void initUI() {
        leftPanel = new CellsPanel(width, height);
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        this.getContentPane().add(leftPanel, BorderLayout.CENTER);
        this.getContentPane().add(rightPanel, BorderLayout.EAST);
        startBtn = new JButton("start");
        startBtn.addActionListener((e) -> {
            startGame();
            startBtn.setEnabled(false);
            pauseBtn.setEnabled(true);
        });
        rightPanel.add(startBtn);
        pauseBtn = new JButton("pause");
        pauseBtn.addActionListener((e) -> {
            pauseGame();
            pauseBtn.setEnabled(false);
            startBtn.setEnabled(true);
        });
        rightPanel.add(pauseBtn);
        
        for (int j = 0; j < width; ++j) {
            for (int i = 0; i < height; ++i) {
                leftPanel.add(new CellButton());
            }
        }
    }

}
