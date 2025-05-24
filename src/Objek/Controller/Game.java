package Objek.Controller;

import javax.swing.*;

public class Game { 

    char key = ' ';
    final int HEIGHT = 1080;
    final int WIDTH = 2160;
    final int SPEED = 50;
    int posX = 0;
    int posY = 0;

    public Game() {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("The Surivalists");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startgameThread();
    }       
}