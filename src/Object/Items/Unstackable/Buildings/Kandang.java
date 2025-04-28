package Object.Items.Unstackable.Buildings;
import Object.Player.Island;
import Object.Player.Player;

public abstract class Kandang extends Buildings {
    public Kandang(String name, int x, int y) {
        super(name, x, y);
       
    }
    public abstract int getCurrentCapacity();
    public abstract int getMaxCapacity();
    public abstract void interact(Player player, Island island);
}
