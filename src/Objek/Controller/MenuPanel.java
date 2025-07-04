package Objek.Controller;

import java.awt.Dimension;
import java.awt.Image;
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
            img = ImageIO.read(getClass().getResource("/res/ui/play.png"));
            Image scaledImage = img.getScaledInstance(250, 50, Image.SCALE_SMOOTH);
            play = new ImageIcon(scaledImage);

            img = ImageIO.read(getClass().getResource("/res/ui/guide.png"));
            scaledImage = img.getScaledInstance(250, 50, Image.SCALE_SMOOTH);
            guide = new ImageIcon(scaledImage);

            img = ImageIO.read(getClass().getResource("/res/ui/exit.png"));
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
        ImageIcon rawIcon = new ImageIcon(getClass().getResource("/res/ui/bgMenu.gif"));
        Image image = rawIcon.getImage().getScaledInstance(1125, 765, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon = new ImageIcon(image);
        JLabel backgroundLabel = new JLabel(scaledIcon);
        backgroundLabel.setBounds(0, 0, 1125, 765);
        add(backgroundLabel);

        ImageIcon titleIcon = new ImageIcon(getClass().getResource("/res/ui/title.png"));
        JLabel titleLabel = new JLabel(titleIcon);
        titleLabel.setBounds(200, 50, 750, 150);
        add(titleLabel);

        JButton playButton = new JButton(play);
        playButton.setBounds(450, 300, 250, 50);
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setFocusPainted(false);
        playButton.addActionListener(e -> startGame());
        add(playButton);

        JButton guideButton = new JButton(guide);
        guideButton.setBounds(450, 370, 250, 50);
        guideButton.setBorderPainted(false);
        guideButton.setContentAreaFilled(false);
        guideButton.setFocusPainted(false);
        guideButton.addActionListener(e -> showMenu());
        add(guideButton);

        JButton exitButton = new JButton(exit);
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
        
        gamePanel.startgameThread();
        gamePanel.setupGame();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
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
