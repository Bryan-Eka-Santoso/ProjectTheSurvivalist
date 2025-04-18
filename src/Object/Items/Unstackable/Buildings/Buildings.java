package Object.Items.Unstackable.Buildings;
import Object.Items.Unstackable.Unstackable;

public class Buildings extends Unstackable {
    private int x, y; // Koordinat posisi bangunan di dunia game
    int width, height; // Ukuran bangunan (lebar dan tinggi)
    

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    public Buildings(String name, int x, int y) {
        super(name);
        this.x = x; // Koordinat x bangunan
        this.y = y; // Koordinat y bangunan
        this.width = 1; // Default lebar bangunan
        this.height = 1; // Default tinggi bangunan
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
