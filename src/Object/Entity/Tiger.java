package Object.Entity;
import Object.Player.*;

public class Tiger extends Animal {
    private String name;
    public int x, y, radius;

    public Tiger(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        radius = 5; // Default radius for tiger
    }

    public void chasePrey(Player player) {
        if (isPreyNearby(player)) {
            player.island.world[y][x] = ' '; // Display tiger on the island
            if (x + 1 < player.x) {
                x++;
            } else if (x - 1 > player.x) {
                x--;
            }
            if (y + 1 < player.y) {
                y++;
            } else if (y - 1 > player.y) {
                y--;
            }
            player.island.world[y][x] = 'T'; // Display tiger on the island
            System.out.println("Chasing prey at (" + player.x + ", " + player.y + ")");
        } else {
            System.out.println("No prey nearby to chase.");
        }
    }

    public boolean isPreyNearby(Player player) {
        if (Math.pow((player.x - this.x), 2) + Math.pow((player.y - this.y), 2) <= Math.pow(radius, 2)) {
            return true;
        } else {
            return false;
        }
    }
    
} 
