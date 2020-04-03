package app;

//import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Player implements ActionListener {
    double x, y;
    double vx = 3;
    double vy;
    int width, height;

    public Player(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width =  width;
        this.height =  height;
       // Timer t = new Timer(10, this);
    }
    public void paint(Graphics g) {
        g.fillRect((int) x, (int) y, width, height);
    }
    public void restrictMovement() {
        vx = 0;
    }
    public void move() {
        x += vx;
        y += vy;
    }
    public boolean collided(int ox, int oy, int ow, int oh) {
        Rectangle obs = new Rectangle(ox, oy, ow, oh);
        Rectangle player =  new Rectangle((int) x, (int) y, width, height);
        return obs.intersects(player);
        
        }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
       

    }

   
   
}