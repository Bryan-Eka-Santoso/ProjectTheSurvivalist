package Object;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {
    GamePanel gp;
    Graphics2D g2;

    public UI (GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(new Font("Arial", Font.PLAIN, 40));
        g2.setColor(Color.white);

        if (gp.gameState == gp.PAUSE_STATE) {
            drawPauseScreen();
        }

    }

    public void drawPauseScreen() {
        String text = "PAUSED";

        int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.SCREEN_WIDTH / 2 - textLength / 2;
        int y = gp.SCREEN_HEIGHT / 2;

        g2.drawString(text, x, y);
    }
}
