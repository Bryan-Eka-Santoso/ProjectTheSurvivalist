package Objek.Environment;

import java.awt.Graphics2D;

import Objek.Controller.GamePanel;

public class EnvironmentManager {
    GamePanel gp;
    public Lighting lighting;

    public EnvironmentManager(GamePanel gp) {
        this.gp = gp;
    }

    public void setup() {
        lighting = new Lighting(gp);
    }

    public void draw(Graphics2D g2) {
        lighting.draw(g2);
    }
}
