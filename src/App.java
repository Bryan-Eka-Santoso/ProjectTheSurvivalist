import javax.swing.JFrame;

import Objek.Controller.MainPanel;

public class App {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("The Survivalists");

        // Buat panel menu utama
        MainPanel mainMenu = new MainPanel(window);
        window.setContentPane(mainMenu);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}