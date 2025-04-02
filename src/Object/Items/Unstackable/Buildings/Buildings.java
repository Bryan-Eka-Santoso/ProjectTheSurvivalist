package Object.Items.Unstackable.Buildings;
import Object.Items.Unstackable.Unstackable;

public class Buildings extends Unstackable {
    private int x, y; // Koordinat posisi bangunan di dunia game

    public Buildings(String name, int x, int y) {
        super(name);
        this.x = x; // Koordinat x bangunan
        this.y = y; // Koordinat y bangunan
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
}
