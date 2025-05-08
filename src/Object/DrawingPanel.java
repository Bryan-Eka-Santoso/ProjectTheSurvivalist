package Object;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class DrawingPanel extends JPanel {

    private ArrayList<Rectangle> rectangles = new ArrayList<>();

    public void addRectangle(Rectangle r) {
        rectangles.add(r);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);
        for (Rectangle r : rectangles) {
            g2.fill(r);
        }
    }
     
}