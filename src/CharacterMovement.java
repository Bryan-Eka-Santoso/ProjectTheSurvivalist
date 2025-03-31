import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class CharacterMovement extends JPanel implements ActionListener, KeyListener {
    // Ukuran window - dibuat lebih besar untuk menampung karakter besar
    private final int PANEL_WIDTH = 1920;
    private final int PANEL_HEIGHT = 1080;
    
    // Ukuran karakter
    private final int CHAR_WIDTH = 1600;
    private final int CHAR_HEIGHT = 1600;
    
    // Posisi karakter - posisi awal di tengah
    private int charX = PANEL_WIDTH / 2 - CHAR_WIDTH / 2;
    private int charY = PANEL_HEIGHT / 2 - CHAR_HEIGHT / 2;
    private final int SPEED = 10; // Kecepatan ditingkatkan untuk karakter besar
    
    // State pergerakan
    private boolean up, down, left, right;
    private boolean isMoving = false;
    private int frameCount = 0;
    private final int FRAME_DELAY = 15; // Delay untuk animasi langkah
    
    // Arah menghadap karakter (0=atas, 1=kanan, 2=bawah, 3=kiri)
    private int facing = 2; // Default menghadap ke bawah
    
    // Array untuk menyimpan sprite
    private BufferedImage[] sprites = new BufferedImage[8];
    // Indeks gambar: 0,1=atas, 2,3=kanan, 4,5=bawah, 6,7=kiri
    // Untuk setiap arah, indeks genap (0,2,4,6) adalah pose berdiri
    // Indeks ganjil (1,3,5,7) adalah pose melangkah
    
    private Timer timer;
    
    public CharacterMovement() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.WHITE);
        this.setFocusable(true);
        this.addKeyListener(this);
        
        // Load semua gambar sprite
        loadSprites();
        
        timer = new Timer(16, this); // Sekitar 60 FPS
        timer.start();
    }
    
    private void loadSprites() {
        try {
            // Menggunakan path file sesuai dengan yang diberikan dalam contoh
            sprites[0] = ImageIO.read(new File("../Character/Up.png"));
            sprites[1] = ImageIO.read(new File("../Character/Up.png"));
            sprites[2] = ImageIO.read(new File("../Character/Right.png"));
            sprites[3] = ImageIO.read(new File("../Character/Right.png"));
            sprites[4] = ImageIO.read(new File("../Character/Down.png"));
            sprites[5] = ImageIO.read(new File("../Character/Down.png"));
            sprites[6] = ImageIO.read(new File("../Character/Left.png"));
            sprites[7] = ImageIO.read(new File("../Character/Left.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading sprite images!");
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Menambahkan rendering yang lebih baik
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        
        // Menggambar karakter
        BufferedImage currentSprite;
        
        // Memilih sprite yang tepat berdasarkan arah dan apakah bergerak
        if (isMoving) {
            // Jika bergerak, alternatifkan antara pose berdiri dan melangkah
            if (frameCount < FRAME_DELAY) {
                currentSprite = sprites[facing * 2]; // Pose berdiri
            } else {
                currentSprite = sprites[facing * 2 + 1]; // Pose melangkah
            }
        } else {
            // Jika tidak bergerak, gunakan pose berdiri
            currentSprite = sprites[facing * 2];
        }
        
        // Jika gambar berhasil dimuat, gambar ke layar
        if (currentSprite != null) {
            g2d.drawImage(currentSprite, charX, charY, CHAR_WIDTH, CHAR_HEIGHT, this);
        } else {
            // Jika gambar gagal dimuat, gambar kotak merah sebagai placeholder
            g2d.setColor(Color.RED);
            g2d.fillRect(charX, charY, CHAR_WIDTH, CHAR_HEIGHT);
        }
        
        // Tambahkan informasi kontrol di layar
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 24)); // Font lebih besar untuk window besar
        g2d.drawString("Kontrol: Gunakan tombol panah untuk bergerak", 20, 30);
        g2d.drawString("Posisi: X=" + charX + ", Y=" + charY, 20, 60);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Update frame untuk animasi
        frameCount++;
        if (frameCount >= FRAME_DELAY * 2) {
            frameCount = 0;
        }
        
        // Update posisi karakter
        isMoving = false;
        
        if (up) {
            charY -= SPEED;
            facing = 0;
            isMoving = true;
        }
        if (down) {
            charY += SPEED;
            facing = 2;
            isMoving = true;
        }
        if (left) {
            charX -= SPEED;
            facing = 3;
            isMoving = true;
        }
        if (right) {
            charX += SPEED;
            facing = 1;
            isMoving = true;
        }
        
        // Batasi posisi karakter agar tidak keluar dari window
        if (charX < 0) charX = 0;
        if (charX > PANEL_WIDTH - CHAR_WIDTH) charX = PANEL_WIDTH - CHAR_WIDTH;
        if (charY < 0) charY = 0;
        if (charY > PANEL_HEIGHT - CHAR_HEIGHT) charY = PANEL_HEIGHT - CHAR_HEIGHT;
        
        // Refresh tampilan
        repaint();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_UP) {
            up = true;
        }
        if (key == KeyEvent.VK_DOWN) {
            down = true;
        }
        if (key == KeyEvent.VK_LEFT) {
            left = true;
        }
        if (key == KeyEvent.VK_RIGHT) {
            right = true;
        }
        
        // Tambahkan tombol escape untuk keluar
        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_UP) {
            up = false;
        }
        if (key == KeyEvent.VK_DOWN) {
            down = false;
        }
        if (key == KeyEvent.VK_LEFT) {
            left = false;
        }
        if (key == KeyEvent.VK_RIGHT) {
            right = false;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // Tidak digunakan
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Character Movement Test (1600x1600)");
        CharacterMovement game = new CharacterMovement();
        
        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set to fullscreen
        frame.setUndecorated(true); // Remove window borders for full screen
        frame.setVisible(true);
    }
}