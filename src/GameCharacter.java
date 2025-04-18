import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class GameCharacter extends JFrame implements KeyListener {
    
    // Deklarasi variabel untuk gambar
    private ImageIcon mapImage;
    private ImageIcon diamKanan;
    private ImageIcon diamKiri;
    private ImageIcon diamAtas;
    private ImageIcon diamBawah;
    private ImageIcon jalanKanan;
    private ImageIcon jalanKiri;
    private ImageIcon jalanAtas;
    private ImageIcon jalanBawah;
    
    // Posisi karakter
    private int characterX = 200;
    private int characterY = 200;
    private int characterSpeed = 5;
    
    // Status pergerakan
    private boolean isMovingUp = false;
    private boolean isMovingDown = false;
    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;
    
    // Arah terakhir karakter (untuk menentukan gambar statis yang digunakan)
    private String lastDirection = "right";
    
    // Komponen untuk menggambar
    private GamePanel gamePanel;
    
    public GameCharacter() {
        // Inisialisasi frame
        setTitle("The Survivalist");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Memuat gambar
        loadImages();
        
        // Membuat panel game
        gamePanel = new GamePanel();
        add(gamePanel);
        
        // Menambahkan key listener
        addKeyListener(this);
        
        // Memusatkan jendela di layar
        setLocationRelativeTo(null);
        
        // Membuat dan memulai thread game
        Thread gameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                gameLoop();
            }
        });
        gameThread.start();
        
        // Menampilkan frame
        setVisible(true);
    }
    
    private void loadImages() {
        try {
            // Memuat gambar peta
            mapImage = new ImageIcon("../res/map/cobaMap.png");
            
            // Memuat gambar karakter diam
            diamKanan = new ImageIcon("../res/character/diamKanan.png");
            diamKiri = new ImageIcon("../res/character/diamKiri.png");
            diamAtas = new ImageIcon("../res/character/diamAtas.png");
            diamBawah = new ImageIcon("../res/character/diamBawah.png");
            
            // Memuat GIF karakter berjalan
            jalanKanan = new ImageIcon("../res/character/jalanKanan.gif");
            jalanKiri = new ImageIcon("../res/character/jalanKiri.gif");
            jalanAtas = new ImageIcon("../res/character/jalanAtas.gif");
            jalanBawah = new ImageIcon("../res/character/jalanBawah.gif");
        } catch (Exception e) {
            System.err.println("Error loading images: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void gameLoop() {
        while (true) {
            // Update posisi karakter
            updateCharacterPosition();
            
            // Redraw the game panel
            gamePanel.repaint();
            
            // Delay untuk mengontrol FPS
            try {
                Thread.sleep(16); // Sekitar 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void updateCharacterPosition() {
        if (isMovingUp) {
            characterY -= characterSpeed;
            lastDirection = "up";
        }
        if (isMovingDown) {
            characterY += characterSpeed;
            lastDirection = "down";
        }
        if (isMovingLeft) {
            characterX -= characterSpeed;
            lastDirection = "left";
        }
        if (isMovingRight) {
            characterX += characterSpeed;
            lastDirection = "right";
        }
        
        // Membatasi karakter agar tidak keluar dari layar
        characterX = Math.max(0, Math.min(characterX, getWidth() - 50));
        characterY = Math.max(0, Math.min(characterY, getHeight() - 50));
    }
    
    // Inner class untuk panel game
    class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            // Menggambar peta
            mapImage.paintIcon(this, g, 0, 0);
            
            // Menggambar karakter
            if (isMovingRight) {
                jalanKanan.paintIcon(this, g, characterX, characterY);
            } else if (isMovingLeft) {
                jalanKiri.paintIcon(this, g, characterX, characterY);
            } else if (isMovingUp) {
                jalanAtas.paintIcon(this, g, characterX, characterY);
            } else if (isMovingDown) {
                jalanBawah.paintIcon(this, g, characterX, characterY);
            } else {
                // Jika tidak bergerak, gunakan gambar diam sesuai arah terakhir
                switch (lastDirection) {
                    case "right":
                        diamKanan.paintIcon(this, g, characterX, characterY);
                        break;
                    case "left":
                        diamKiri.paintIcon(this, g, characterX, characterY);
                        break;
                    case "up":
                        diamAtas.paintIcon(this, g, characterX, characterY);
                        break;
                    case "down":
                        diamBawah.paintIcon(this, g, characterX, characterY);
                        break;
                }
            }
        }
    }
    
    // Implementasi KeyListener
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        switch (keyCode) {
            case KeyEvent.VK_W:
                isMovingUp = true;
                break;
            case KeyEvent.VK_S:
                isMovingDown = true;
                break;
            case KeyEvent.VK_A:
                isMovingLeft = true;
                break;
            case KeyEvent.VK_D:
                isMovingRight = true;
                break;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        switch (keyCode) {
            case KeyEvent.VK_W:
                isMovingUp = false;
                break;
            case KeyEvent.VK_S:
                isMovingDown = false;
                break;
            case KeyEvent.VK_A:
                isMovingLeft = false;
                break;
            case KeyEvent.VK_D:
                isMovingRight = false;
                break;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // Tidak digunakan, tapi harus diimplementasikan
    }
    
    public static void main(String[] args) {
        // Memulai aplikasi
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameCharacter();
            }
        });
    }
}