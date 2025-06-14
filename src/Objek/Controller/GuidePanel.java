package Objek.Controller;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GuidePanel extends JPanel {

    private Font pixelFont;
    private JPanel movementContainer;
    private JPanel interactionContainer;
    private JPanel mekanismeContainer;
    private JFrame parentFrame; // Reference ke frame utama

    public GuidePanel() {
        setPreferredSize(new Dimension(1125, 765));
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        loadPixelFont();

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(null);
        containerPanel.setPreferredSize(new Dimension(1100, 1280)); // Tambah tinggi untuk tombol back
        containerPanel.setBackground(Color.BLACK);

        // Container gerak (lebih kecil)
        movementContainer = createGuideContainer("Petunjuk Bergerak", 30, 200);
        containerPanel.add(movementContainer);
        isiPetunjukGerak();

        // Container interaksi (lebih tinggi karena banyak baris)
        interactionContainer = createGuideContainer("Petunjuk Interaksi", 250, 520);
        containerPanel.add(interactionContainer);
        isiPetunjukInteraksi();

        // Mekanisme Game Container
        mekanismeContainer = createGuideContainer("Mekanisme Game", 790, 370);
        containerPanel.add(mekanismeContainer);
        isiMekanismeGame();

        // Tambahkan tombol back
        JButton backButton = createBackButton();
        backButton.setBounds(475, 1200, 150, 50); // Posisi di bawah semua container
        containerPanel.add(backButton);

        JScrollPane scrollPane = new JScrollPane(containerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Constructor dengan parameter untuk parent frame
    public GuidePanel(JFrame parentFrame) {
        this();
        this.parentFrame = parentFrame;
    }

    private JButton createBackButton() {
        JButton backButton = new JButton("BACK TO MENU");
        backButton.setFont(pixelFont.deriveFont(Font.BOLD, 18f));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(60, 60, 60));
        backButton.setBorder(new LineBorder(Color.WHITE, 2));
        backButton.setFocusPainted(false);
        
        // Hover effect
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(100, 100, 100));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(60, 60, 60));
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBackButton();
            }
        });

        return backButton;
    }

    // Method untuk handle back button - kembali ke MenuPanel
    private void handleBackButton() {
        if (parentFrame != null) {
            // Buat MenuPanel baru dan set sebagai content pane
            MenuPanel mainMenu = new MenuPanel(parentFrame);
            parentFrame.setContentPane(mainMenu);
            parentFrame.revalidate();
            parentFrame.repaint();
        } else {
            // Jika parentFrame null, cari frame dari parent component
            Container parent = this.getParent();
            while (parent != null && !(parent instanceof JFrame)) {
                parent = parent.getParent();
            }
            
            if (parent instanceof JFrame) {
                JFrame frame = (JFrame) parent;
                MenuPanel mainMenu = new MenuPanel(frame);
                frame.setContentPane(mainMenu);
                frame.revalidate();
                frame.repaint();
            }
        }
    }

    private JPanel createGuideContainer(String title, int yPosition, int height) {
        JPanel container = new JPanel();
        container.setLayout(null);
        container.setBounds(50, yPosition, 1000, height);
        container.setOpaque(false); // transparan
        container.setBorder(new LineBorder(Color.WHITE, 2)); // outline putih 2px

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(pixelFont.deriveFont(20f));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(30, 15, 600, 30);
        container.add(titleLabel);

        return container;
    }

    private void isiPetunjukGerak() {
        if (movementContainer == null) return;
        movementContainer.removeAll();

        JLabel titleLabel = new JLabel("Petunjuk Bergerak");
        titleLabel.setFont(pixelFont.deriveFont(20f));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(30, 15, 600, 30);
        movementContainer.add(titleLabel);

        String[] petunjuk = {
            "W → Berjalan ke atas",
            "A → Berjalan ke kiri",
            "S → Berjalan ke bawah",
            "D → Berjalan ke kanan"
        };

        for (int i = 0; i < petunjuk.length; i++) {
            JLabel label = new JLabel(petunjuk[i]);
            label.setFont(pixelFont.deriveFont(16f));
            label.setForeground(Color.WHITE);
            label.setBounds(50, 60 + (i * 30), 600, 25); // Spasi antar baris
            movementContainer.add(label);
        }

        movementContainer.revalidate();
        movementContainer.repaint();
    }

    private void isiPetunjukInteraksi() {
        if (interactionContainer == null) return;
        interactionContainer.removeAll();

        JLabel titleLabel = new JLabel("Petunjuk Interaksi");
        titleLabel.setFont(pixelFont.deriveFont(20f));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(30, 15, 600, 30);
        interactionContainer.add(titleLabel);

        String[] petunjuk = {
            "F → Berpindah antara wilayah daratan dan lautan",
            "C → Membuka menu crafting untuk membuat item dan peralatan",
            "I → Membuka inventory untuk melihat dan mengelola barang",
            "1 - 9 → Memilih item sesuai slot",
            "S → Memindahkan item saat membuka chest/inventory",
            "E → Menyerang, menebang pohon, menghancurkan bangunan",
            "T → Masuk dan keluar kandang",
            "G → Mengangkat dan memindahkan hewan",
            "Q → Menjatuhkan item ke world (stackable bisa pilih jumlah)",
            "Shift + C → Membuka menu achievement",
            "Arrow Down → Memilih aksi saat mode kandang: hapus, kawin, panen",
            "Enter → Konfirmasi aksi saat mode kandang",
            "Spasi (Spacebar) → Masuk/keluar toko dan gua"
        };

        for (int i = 0; i < petunjuk.length; i++) {
            JLabel label = new JLabel(petunjuk[i]);
            label.setFont(pixelFont.deriveFont(16f));
            label.setForeground(Color.WHITE);
            label.setBounds(50, 60 + (i * 30), 900, 25); // Konsisten rapi kiri
            interactionContainer.add(label);
        }

        interactionContainer.revalidate();
        interactionContainer.repaint();
    }

    private void loadPixelFont() {
        try {
            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, new File("ProjectTheSurvivalist/res/htp/pixel-font.ttf"));
            pixelFont = baseFont.deriveFont(Font.PLAIN, 14);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(baseFont);
        } catch (Exception e) {
            e.printStackTrace();
            pixelFont = new Font("Monospaced", Font.PLAIN, 14);
        }
    }

    private void isiMekanismeGame() {
        if (mekanismeContainer == null) return;
        mekanismeContainer.removeAll();

        JLabel titleLabel = new JLabel("Mekanisme Game");
        titleLabel.setFont(pixelFont.deriveFont(20f));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(30, 15, 600, 30);
        mekanismeContainer.add(titleLabel);

        String[] sinopsis = {
            "Dalam game ini, Anda akan memulai petualangan dengan sebuah kapak sederhana di tangan.",
            "Tujuan utama Anda adalah bertahan hidup selama mungkin sambil meningkatkan level karakter",
            "dengan membunuh makhluk hidup yang tersebar di dunia.",
            "",
            "Dunia tidak hanya dihuni oleh makhluk jinak, tetapi juga oleh predator buas seperti serigala",
            "yang aktif memburu Anda. Di kedalaman gua, monster-monster berbahaya menanti",
            "untuk menguji kemampuan bertahan hidup Anda.",
            "",
            "Manfaatkan kekayaan alam seperti kayu, batu, dan tumbuhan untuk menciptakan alat, senjata,",
            "dan struktur yang mendukung kelangsungan hidup. Bangun, bertarung, dan jelajahi dunia untuk",
            "mencapai tujuan utama: menjadi penyintas terkuat di antara yang hidup."
        };

        for (int i = 0; i < sinopsis.length; i++) {
            JLabel baris = new JLabel(sinopsis[i]);
            baris.setFont(pixelFont.deriveFont(14f));
            baris.setForeground(Color.WHITE);
            baris.setBounds(30, 60 + (i * 25), 940, 25);
            mekanismeContainer.add(baris);
        }

        mekanismeContainer.revalidate();
        mekanismeContainer.repaint();
    }
}