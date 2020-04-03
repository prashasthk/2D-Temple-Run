package app;
import java.awt.Graphics;

public class Wall {
    int x, y;
    int width, height;
    public Wall(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }
    public void paint(Graphics g) {
        g.fillRect(x, y, width, height);
        
    }
    public void translate(int vx, int vy) {
        x += vx;
        y += vy;
    }
}