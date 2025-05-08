package Object.Items.Unstackable.Buildings;
import Object.Player.Island;
import Object.Player.Player;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Object.GamePanel;

public abstract class Kandang extends Buildings {
    GamePanel gp;
    Rectangle solidArea;
    BufferedImage img;

    public Kandang(String name, int x, int y, GamePanel gp, Rectangle solidArea) {
        super(name, x, y);
    }
    public abstract int getCurrentCapacity();
    public abstract int getMaxCapacity();
    public abstract void interact(Player player, Island island);
}
