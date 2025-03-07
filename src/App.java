import java.awt.Color;
import java.awt.event.*;
// import javax.swing.ImageIcon;
// import javax.swing.JFrame;
// import javax.swing.JLabel;
import javax.swing.*; // Mostly including ImageIcon, JFrame, JLabel

public class App {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame();
        JLabel label = new JLabel("Institut Sains dan Teknologi Terpadu Surabaya");
        ImageIcon imgIcon = new ImageIcon("logoistts.png");
        label.setForeground(Color.white);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.TOP);

        label.setIcon(imgIcon);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);

        frame.setTitle("The Survivalists");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 620);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setIconImage(imgIcon.getImage());
        frame.getContentPane().setBackground(new Color(0xffffff));
        frame.add(label);

        frame.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                System.out.println("Mouse Coordinates: X=" + e.getX() + " Y=" + e.getY());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // Optional: Handle dragging if needed
            }
        });

    }
}
