package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Panda extends Character {
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private Thread movementThread; 
    
    public Panda(double x, double y) {
        super(x, y, 200);  //  Set health to 200.
        
        walkLeftImages = new ArrayList<>();
        walkRightImages = new ArrayList<>();
        walkLeftImages.add(new ImageIcon("p2.png").getImage());
        walkRightImages.add(new ImageIcon("p3.png").getImage());
    }
// Added a getProjectiles() method to access projectiles.
    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }
    public void reset() {
        setHealth(200);  // Reset health
        x = 100;
        y = 400;
        isMovingLeft = false;  // Reset the left movement status
        isMovingRight = false; // Reset the movement status to the right
        projectiles.clear();
    }

    public void shoot() {
        double angle = Math.toRadians(290);  // Shooting angle
        double speed = 17;  // Bullet speed
        int damage = 10;  // Set the damage value for the bullet.
        
        double velocityX = Math.cos(angle) * speed;
        double velocityY = Math.sin(angle) * speed;
        double projectileX = x + 30;  // X position of the bullet
        double projectileY = y - 10;  // Y position of the bullet
        projectiles.add(new Projectile(projectileX, projectileY, velocityX, velocityY, new ImageIcon("ph1.png").getImage(),damage));   
    }
    public void handleKeyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_A) {
            isMovingLeft = true;
        } else if (keyCode == KeyEvent.VK_D) {
            isMovingRight = true;
        } else if (keyCode == KeyEvent.VK_W) {
            shoot();  // Call the fire function.
        }
    }

    @Override
    public void handleKeyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_A) {
            isMovingLeft = false;
        } else if (keyCode == KeyEvent.VK_D) {
            isMovingRight = false;
        }
    }
    public void decreaseHealth(int amount) {
        health -= amount;
    }
    // Update the position of the bullet.
    public void update() {
        if (isMovingLeft) {
        if (x > 0) {
            x -= 5;  // Decrease the x position to the left.
        } 
    }
    if (isMovingRight) {
        if (x < 800 - 125) {  // Check the rightmost boundary of the screen.
            x += 5;  // Incread the x position to the right.
        }    
    }
       // Update bullet position
        for (Projectile p : projectiles) {
            p.update();
        }
    }
    //Thread start function for movement
    public void startMovementThread() {
        if (movementThread != null && movementThread.isAlive()) {
            return; // If the thread is still running, do not restart.
        }
        movementThread = new Thread(() -> {
            while (true) {
               // Update movement
                update();
                // Wait 50 milliseconds before updating the next move.
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });
        movementThread.start(); // Start thread
    }
    @Override
    public void draw(Graphics g) {
        
        // Drawing Panda character
        if (isMovingLeft) {
            g.drawImage(walkLeftImages.get(currentWalkLeftIndex), (int) x, (int) y, 125, 125, null);
        } else if (isMovingRight) {
            g.drawImage(walkRightImages.get(currentWalkRightIndex), (int) x, (int) y, 125, 125, null);
        } else {
            g.drawImage(walkRightImages.get(0), (int) x, (int) y, 125, 125, null);
        }

        // Draw a bullet
        for (Projectile p : projectiles) {
            if (p.isActive()) {
                g.drawImage(p.getImage(), (int) p.getX(), (int) p.getY(), 30, 30, null); // Draw a bullet as a picture.
            }
        }
    }
    // Method to move left
    public void moveLeft() {
        x -= 5;
    }
   // Method to move right
    public void moveRight() {
        x += 5;
        
    }
    void setX(int x) {
       this.x = x;
    }
    void setY(int i) {
        this.y = y;
    } 
}
