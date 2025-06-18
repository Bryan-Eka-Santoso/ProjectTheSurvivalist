package Objek.Controller;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class ShopEffect {
    public String name;
    public String filePath;
    public BufferedImage img;
    public int price;
    public int category;
    
    public ShopEffect(String name, String filePath, int price, int category) {
        this.name = name;
        this.filePath = filePath;
        this.price = price;
        this.category = category;
        try {
            this.img = ImageIO.read(getClass().getResource(filePath));
        } catch (IOException e) {
            System.out.println("Error loading image: " + filePath);
            e.printStackTrace();
        }
    }

    public ShopEffect(String name, BufferedImage img, int price, int category) {
        this.name = name;
        this.img = img;
        this.price = price;
        this.category = category;
    }
}