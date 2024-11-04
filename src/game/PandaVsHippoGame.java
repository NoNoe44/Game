import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class PandaVsHippoGame extends JPanel implements ActionListener, KeyListener {

    private int pandaX = 100, pandaY = 300, hippoX = 500, hippoY = 300;
    private int pandaHealth = 100, hippoHealth = 100;
    private ArrayList<Projectile> pandaProjectiles = new ArrayList<>();
    private ArrayList<Projectile> hippoProjectiles = new ArrayList<>();
    private Image backgroundImage;
    private ArrayList<Image> pandaWalkLeftImages = new ArrayList<>();
    private ArrayList<Image> pandaWalkRightImages = new ArrayList<>();
    private ArrayList<Image> hippoWalkLeftImages = new ArrayList<>();
    private ArrayList<Image> hippoWalkRightImages = new ArrayList<>();
    private int pandaWalkLeftIndex = 0;
    private int pandaWalkRightIndex = 0;
    private int hippoWalkLeftIndex = 0;
    private int hippoWalkRightIndex = 0;
    private boolean pandaMovingLeft = false;
    private boolean pandaMovingRight = false;
    private boolean hippoMovingLeft = false;
    private boolean hippoMovingRight = false;
    private Timer timer;
    private final double GRAVITY = 0.5;

    public PandaVsHippoGame() {
    
        backgroundImage = new ImageIcon("black ground3.png").getImage();



        pandaWalkLeftImages.add(new ImageIcon("p2.png").getImage());
        

        pandaWalkRightImages.add(new ImageIcon("p3.png").getImage());

  

        hippoWalkLeftImages.add(new ImageIcon("h1.png").getImage());
        hippoWalkRightImages.add(new ImageIcon("h2.png").getImage());

        timer = new Timer(10, this);
        timer.start();
        setFocusable(true);
        addKeyListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        

        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);


        if (pandaMovingLeft) {
            g.drawImage(pandaWalkLeftImages.get(pandaWalkLeftIndex), pandaX, pandaY, 50, 50, this);
        } else if (pandaMovingRight) {
            g.drawImage(pandaWalkRightImages.get(pandaWalkRightIndex), pandaX, pandaY, 50, 50, this);
        } else {
            g.drawImage(pandaWalkRightImages.get(0), pandaX, pandaY, 50, 50, this); 
        }
        

        if (hippoMovingLeft) {
            g.drawImage(hippoWalkLeftImages.get(hippoWalkLeftIndex), hippoX, hippoY, 50, 50, this);
        } else if (hippoMovingRight) {
            g.drawImage(hippoWalkRightImages.get(hippoWalkRightIndex), hippoX, hippoY, 50, 50, this);
        } else {
            g.drawImage(hippoWalkRightImages.get(0), hippoX, hippoY, 50, 50, this); 
        }


        g.setColor(Color.RED);
        g.fillRect(50, 50, pandaHealth, 10);
        g.fillRect(450, 50, hippoHealth, 10);

 
        g.setColor(Color.ORANGE);
        for (Projectile p : pandaProjectiles) {
            g.fillOval((int)p.x, (int)p.y, 10, 10);
        }
        g.setColor(Color.GREEN);
        for (Projectile p : hippoProjectiles) {
            g.fillOval((int)p.x, (int)p.y, 10, 10);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (pandaMovingLeft) {
            pandaWalkLeftIndex = (pandaWalkLeftIndex + 1) % pandaWalkLeftImages.size();
        }
        if (pandaMovingRight) {
            pandaWalkRightIndex = (pandaWalkRightIndex + 1) % pandaWalkRightImages.size();
        }
        if (hippoMovingLeft) {
            hippoWalkLeftIndex = (hippoWalkLeftIndex + 1) % hippoWalkLeftImages.size();
        }
        if (hippoMovingRight) {
            hippoWalkRightIndex = (hippoWalkRightIndex + 1) % hippoWalkRightImages.size();
        }


        updateProjectiles();
        checkCollisions();
        
  
        if (pandaHealth <= 0 || hippoHealth <= 0) {
            timer.stop();
            String winner = pandaHealth <= 0 ? "Hippo Wins!" : "Panda Wins!";
            JOptionPane.showMessageDialog(this, winner);
        }

        repaint();
    }

    private void updateProjectiles() {
        for (Projectile p : pandaProjectiles) p.move();
        for (Projectile p : hippoProjectiles) p.move();
    }

    private void checkCollisions() {
        pandaProjectiles.removeIf(p -> {
            if (Math.abs(p.x - hippoX) < 20 && Math.abs(p.y - hippoY) < 20) {
                hippoHealth -= 10;
                return true;
            }
            return false;
        });

        hippoProjectiles.removeIf(p -> {
            if (Math.abs(p.x - pandaX) < 20 && Math.abs(p.y - pandaY) < 20) {
                pandaHealth -= 10;
                return true;
            }
            return false;
        });
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            pandaX -= 10;
            pandaMovingLeft = true; 
            pandaMovingRight = false; 
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            pandaX += 10;
            pandaMovingRight = true;
            pandaMovingLeft = false; 
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            double angle = Math.toRadians(60);
            double speed = 10;
            pandaProjectiles.add(new Projectile(pandaX + 50, pandaY, speed * Math.cos(angle), -speed * Math.sin(angle)));
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            hippoX -= 10;
            hippoMovingLeft = true; 
            hippoMovingRight = false; 
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            hippoX += 10;
            hippoMovingRight = true; 
            hippoMovingLeft = false; 
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            double angle = Math.toRadians(60);
            double speed = 10;
            hippoProjectiles.add(new Projectile(hippoX - 10, hippoY, -speed * Math.cos(angle), -speed * Math.sin(angle)));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_A) {
            pandaMovingLeft = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            pandaMovingRight = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            hippoMovingLeft = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            hippoMovingRight = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Panda vs Hippo Game");
        PandaVsHippoGame game = new PandaVsHippoGame();
        frame.add(game);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class Projectile {
    double x, y, vx, vy;

    public Projectile(double x, double y, double vx, double vy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    public void move() {
        x += vx;
        y += vy;
        vy += 0.5; 
    }
}
