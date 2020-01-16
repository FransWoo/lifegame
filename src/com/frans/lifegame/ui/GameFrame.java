package com.frans.lifegame.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

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
    private JButton randomBtn;
    private JButton clearBtn;
    private Thread gameThread;
    private int width = 80;
    private int height = 80;
    private int randomProbability = 30;
    private boolean run = false;;
    
    public GameFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        game = new Game(width, height, 100).init();
        game.setCellChangeListener((cells, run) -> {
            cells.forEach((cell) -> {
                updateCellBtnUI(cell);
            });
            leftPanel.updateUI();
        });
        gameThread = new Thread(game);
        initUI();
    }
    
    /**
     * 根据cell更新cellButton的ui
     * @param cell
     */
    public void updateCellBtnUI(final Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        Component c = leftPanel.getComponentAtXY(x, y);
        if (cell.getStatus() == Cell.LifeStatus.SURVIVAL) {
            c.setBackground(CellButton.SURVIVAL_COLOR);
        } else {
            c.setBackground(CellButton.DEATH_COLOR);
        }
    }
    
    public void startGame() {
        run = true;
        if (!gameThread.isAlive())
            gameThread.start();
        else {
            game.setRun(run);
        }
    }
    
    public void pauseGame() {
        run = false;
        game.setRun(run);
    }
    
    public void initUI() {
        leftPanel = new CellsPanel(width, height);
        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(0, 1));
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        leftPanel.setPreferredSize(new Dimension(800, 800));
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
        pauseBtn.setEnabled(false);
        rightPanel.add(pauseBtn);
        randomBtn = new JButton("random");
        randomBtn.addActionListener((e) -> {
            if (!run) {
                game.randomInit(randomProbability);
                for (int i = 0; i < game.getWidth(); ++i) {
                    for (int j = 0; j < game.getHeight(); ++j) {
                        updateCellBtnUI(game.getCellByXY(i, j));
                    }
                }
            }
        });
        rightPanel.add(randomBtn);
        clearBtn = new JButton("clear");
        clearBtn.addActionListener((e) -> {
            if (!run) {
                game.init();
                for (int i = 0; i < game.getWidth(); ++i) {
                    for (int j = 0; j < game.getHeight(); ++j) {
                        updateCellBtnUI(game.getCellByXY(i, j));
                    }
                }
            }
        });
        rightPanel.add(clearBtn);
        
        for (int j = 0; j < height; ++j) {
            for (int i = 0; i < width; ++i) {
                CellButton btn = new CellButton(i, j);
                btn.addActionListener((e) -> {
                    if (!run) {
                        if (btn.getBackground() == CellButton.DEATH_COLOR) {
                            btn.setBackground(CellButton.SURVIVAL_COLOR);
                            game.getCellByXY(btn.getxInCellPanel(), btn.getyInCellPanel()).setStatus(Cell.LifeStatus.SURVIVAL);
                        } else {
                            btn.setBackground(CellButton.DEATH_COLOR);
                            game.getCellByXY(btn.getxInCellPanel(), btn.getyInCellPanel()).setStatus(Cell.LifeStatus.DEATH);
                        }
                    }
                });
                leftPanel.add(btn);
            }
        }
        pack();
    }

}
