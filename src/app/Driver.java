package app;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;
import java.awt.Graphics2D;
import javax.swing.Timer;
import java.awt.Toolkit;

public class Driver extends JPanel implements ActionListener, KeyListener {
    /**
     *
     */
    ArrayList<Wall> wallLeft = new ArrayList<Wall>();
    ArrayList<Wall> wallRight = new ArrayList<Wall>();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    boolean movingRight;
    boolean movingUp;
    boolean movingDown;
    Player p;
    double sandstormX;
    double sandstormY;;
    double x;
    double vx;
    double y;
    int translateVX;
    int translateVY;
    int lxPos;
    int lyPos;
    int rxPos;
    int ryPos;
    int flagxPos;
    int flagyPos;
    boolean gameOver;
    AffineTransform tx;
    String sandstormImg;
    Timer switchTimer;
    Timer t;
    private static final long serialVersionUID = 1L;
    JFrame frame;

    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception {
        Driver d = new Driver();
    }

    public Driver() {
        frame = new JFrame();
        // System.out.println(screenSize.width);
        // System.out.println(screenSize.height);
        // frame.setSize(screenSize.width, screenSize.height);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);
        frame.addKeyListener(this);
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        init();
        // System.out.println(tx.getScaleX());

    }

    public void init() {
        sandstormX = 0;
        sandstormY = 0;
        x = 250;
        vx = 1.5;
        y = 0;
        translateVX = 15;
        translateVY = 15;
        lxPos = -20;
        lyPos = 400;
        rxPos = lxPos;
        ryPos = 600;
        gameOver = false;
        p = new Player(30, 500, 10, 10);
        wallLeft = new ArrayList<Wall>();
        wallRight = new ArrayList<Wall>();
        movingRight = true;
        movingUp = false;
        movingDown = false;
        sandstormX -= screenSize.getWidth() - 50;
        tx = AffineTransform.getTranslateInstance(sandstormX, sandstormY);
        sandstormImg = "sandstorm_frame_1.png";
        generateRandomMap();
        t = new Timer(17, taskPerformer);
        t.start();
        switchTimer = new Timer((int) (Math.random() * 6000 + 1000), switchFrame);
        switchTimer.start();

    }

    ActionListener taskPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            update();
            repaint();
        }
    };

    public void generateRandomMap() {
        // Timer timer = new Timer(1000, taskPerformer);
        // timer.start();
        for (int i = 0; i < (int) (Math.random() * 50 + 30); i++) {
            lxPos += 20;
            rxPos += 20;
            wallLeft.add(new Wall(lxPos, lyPos, 20, 20));
            wallRight.add(new Wall(rxPos, ryPos, 20, 20));

        }

        int cntr = 0;
        // OPTIONS WHEN MOVING RIGHT
        while (cntr < 30) {
            int z = (int) ((Math.random() * 2) + 1);
            // System.out.println(z);
            switch (z) {
                // UP
                case 1:
                    // System.out.println("Going up");
                    for (int i = 0; i < 7; i++) {
                        rxPos += 20;
                        wallRight.add(new Wall(rxPos, ryPos, 20, 20));
                    }
                    up();
                    right();
                    for (int i = 0; i < 7; i++) {
                        lxPos += 20;
                        wallLeft.add(new Wall(lxPos, lyPos, 20, 20));
                    }
                    break;

                // DOWN
                case 2:
                    for (int i = 0; i < 7; i++) {
                        lxPos += 20;
                        wallLeft.add(new Wall(lxPos, lyPos, 20, 20));
                    }
                    down();
                    right();
                    for (int i = 0; i < 7; i++) {
                        rxPos += 20;
                        wallRight.add(new Wall(rxPos, ryPos, 20, 20));
                    }
                    break;

            }

            cntr++;
        }

    }

    public void up() {

        for (int i = 0; i < (int) (Math.random() * 50 + 3); i++) {
            lyPos -= 20;
            ryPos -= 20;
            wallLeft.add(new Wall(lxPos, lyPos, 20, 20));
            wallRight.add(new Wall(rxPos, ryPos, 20, 20));
        }

    }

    public void down() {
        for (int i = 0; i < (int) (Math.random() * 50 + 3); i++) {
            lyPos += 20;
            ryPos += 20;
            wallLeft.add(new Wall(lxPos, lyPos, 20, 20));
            wallRight.add(new Wall(rxPos, ryPos, 20, 20));
        }
    }

    public void right() {
        for (int i = 0; i < (int) (Math.random() * 50 + 3); i++) {
            lxPos += 20;
            rxPos += 20;
            wallLeft.add(new Wall(lxPos, lyPos, 20, 20));
            wallRight.add(new Wall(rxPos, ryPos, 20, 20));

        }
    }

    private Image getImage(String path) {
        Image tempImage = null;
        try {
            URL imageURL = Driver.class.getResource(path);
            tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempImage;
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        g.fillRect(0, 0, screenSize.width, screenSize.height);
        g.setColor(Color.WHITE);
        for (Wall e : wallLeft) {
            e.paint(g);
            if (p.collided(e.x, e.y, e.width, e.height)) {
                p.vx = 0;
                p.vy = 0;
                vx = 0;
                translateVX = 0;
                translateVY = 0;
                sandstormX += 5;
                if (p.collided((int) sandstormX, (int) sandstormY, (int) screenSize.getWidth() - 100,
                        (int) screenSize.getHeight())) {
                    t.stop();
                    switchTimer.stop();
                    init();

                }
            }
        }
        for (Wall e : wallRight) {
            e.paint(g);
            if (p.collided(e.x, e.y, e.width, e.height)) {
                p.vx = 0;
                p.vy = 0;
                vx = 0;
                translateVX = 0;
                translateVY = 0;
                sandstormX += 5;
                if (p.collided((int) sandstormX, (int) sandstormY, (int) screenSize.getWidth() - 100,
                        (int) screenSize.getHeight())) {
                    t.stop();
                    switchTimer.stop();
                    init();
                }
            }
        }
        g.setColor(Color.RED);
        p.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(getImage(sandstormImg), tx, null);
        g2.drawImage(getImage("finish-flag1.png"), flagxPos, flagyPos, null);
        if (p.collided(flagxPos, flagyPos, 200, 200)) {
            t.stop();
            switchTimer.stop();
            init();
        }
        g.setColor(Color.BLACK);
        g.fillRect((int) x, (int) y, screenSize.width - (int) x, screenSize.height);

    }

    ActionListener switchFrame = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            switch ((int) (Math.random() * 5 + 1)) {
                // System.out.println("updating frame");
                case 1:
                    sandstormImg = "sandstorm_frame_1.png";
                    break;
                case 2:
                    sandstormImg = "sandstorm_frame_2.png";
                    break;
                case 3:
                    sandstormImg = "sandstorm_frame_3.png";
                    break;
                case 4:
                    sandstormImg = "sandstorm_frame_4.png";
                    break;
                case 5:
                    sandstormImg = "sandstorm_frame_5.png";
                    break;

            }
        }
    };

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

    public void update() {
        if (x > screenSize.width - 25) {
            vx = 0;
        }
        if (p.x > x - 100) {
            p.restrictMovement();
        } else {
            p.move();
        }

        // if (sandstormX + screenSize.getWidth() < p.x - 75) {
        // sandstormX += 0.5;
        // tx.setToTranslation(sandstormX, sandstormY);
        // } else {
        // sandstormX += 0;
        // tx.setToTranslation(sandstormX, sandstormY);
        // }
        sandstormX += 1;
        tx.setToTranslation(sandstormX, sandstormY);
        tx.scale(screenSize.getWidth() / 1536, screenSize.getHeight() / 864);

        if (movingRight) {
            for (Wall e : wallLeft) {
                e.translate(-translateVX, 0);
            }
            for (Wall e : wallRight) {
                e.translate(-translateVX, 0);
            }
            flagxPos = wallLeft.get(wallLeft.size() - 1).x + 150;
            flagyPos = (wallLeft.get(wallLeft.size() - 1).y + wallRight.get(wallRight.size() - 1).y) / 2 - 50;

        }
        if (movingDown) {
            for (Wall e : wallLeft) {
                e.translate(0, -translateVY);
            }
            for (Wall e : wallRight) {
                e.translate(0, -translateVY);
            }
            flagxPos = wallLeft.get(wallLeft.size() - 1).x + 150;
            flagyPos = (wallLeft.get(wallLeft.size() - 1).y + wallRight.get(wallRight.size() - 1).y) / 2 - 50;

        }
        if (movingUp) {
            for (Wall e : wallLeft) {
                e.translate(0, translateVY);
            }
            for (Wall e : wallRight) {
                e.translate(0, translateVY);
            }
            flagxPos = wallLeft.get(wallLeft.size() - 1).x + 150;
            flagyPos = (wallLeft.get(wallLeft.size() - 1).y + wallRight.get(wallRight.size() - 1).y) / 2 - 50;
        }
        x += vx;

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 83) {
            p.vx = 0;
            p.vy = 3;
            movingDown = true;
            movingRight = false;
            movingUp = false;
        }
        if (e.getKeyCode() == 87) {
            p.vx = 0;
            p.vy = -3;
            movingUp = true;
            movingDown = false;
            movingRight = false;
        }
        if (e.getKeyCode() == 68) {
            p.vy = 0;
            p.vx = 3;
            movingRight = true;
            movingUp = false;
            movingDown = false;
        }
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // if(e.getKeyCode() == 83) {
        // p.vy = 0;
        // }
        // if(e.getKeyCode() == 87) {
        // p.vy = 0;
        // }

    }

}