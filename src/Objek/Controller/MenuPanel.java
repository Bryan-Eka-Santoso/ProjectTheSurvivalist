package Objek.Controller;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {
    JFrame window;
    JLabel backgroundLabel;
    Image img;
    ImageIcon play, guide, exit;
    
    public MenuPanel(JFrame window) {
        try {
            img = ImageIO.read(new File("ProjectTheSurvivalist/res/ui/play.png"));
            Image scaledImage = img.getScaledInstance(250, 50, Image.SCALE_SMOOTH);
            play = new ImageIcon(scaledImage);

            img = ImageIO.read(new File("ProjectTheSurvivalist/res/ui/guide.png"));
            scaledImage = img.getScaledInstance(250, 50, Image.SCALE_SMOOTH);
            guide = new ImageIcon(scaledImage);

            img = ImageIO.read(new File("ProjectTheSurvivalist/res/ui/exit.png"));
            scaledImage = img.getScaledInstance(250, 50, Image.SCALE_SMOOTH);
            exit = new ImageIcon(scaledImage);
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        this.window = window;
        setPreferredSize(new Dimension(1125, 765));
        setLayout(null); // supaya bisa atur posisi elemen secara manual

        // === Tambahkan background GIF ===
        ImageIcon rawIcon = new ImageIcon("ProjectTheSurvivalist/res/ui/bgMenu.gif");
        Image image = rawIcon.getImage().getScaledInstance(1125, 765, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon = new ImageIcon(image);
        JLabel backgroundLabel = new JLabel(scaledIcon);
        backgroundLabel.setBounds(0, 0, 1125, 765);
        add(backgroundLabel);

        ImageIcon titleIcon = new ImageIcon("ProjectTheSurvivalist/res/ui/title.png");
        JLabel titleLabel = new JLabel(titleIcon);
        titleLabel.setBounds(200, 50, 750, 150);
        add(titleLabel);

        JButton playButton = new JButton(play);
        playButton.setFont(new Font("Arial", Font.PLAIN, 24));
        playButton.setBounds(450, 300, 250, 50);
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setFocusPainted(false);
        playButton.addActionListener(e -> startGame());
        add(playButton);

        JButton guideButton = new JButton(guide);
        guideButton.setFont(new Font("Arial", Font.PLAIN, 24));
        guideButton.setBounds(450, 370, 250, 50);
        guideButton.setBorderPainted(false);
        guideButton.setContentAreaFilled(false);
        guideButton.setFocusPainted(false);
        playButton.addActionListener(e -> showMenu());
        add(guideButton);

        JButton exitButton = new JButton(exit);
        exitButton.setFont(new Font("Arial", Font.PLAIN, 24));
        exitButton.setBounds(450, 440, 250, 50);
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);

        setComponentZOrder(backgroundLabel, getComponentCount() - 1);
    }

    public void startGame() {
        GamePanel gamePanel = new GamePanel();
        window.setContentPane(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startgameThread();
    }

    public void showMenu() {
        GuidePanel guidePanel = new GuidePanel();
        window.setContentPane(guidePanel);
        guidePanel.setFocusable(true);
        guidePanel.requestFocusInWindow();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
