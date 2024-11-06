package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public abstract class Character {
    protected double x, y;
    protected int health;
    protected ArrayList<Image> walkLeftImages;
    protected ArrayList<Image> walkRightImages;
    protected int currentWalkLeftIndex = 0;
    protected int currentWalkRightIndex = 0;
    protected boolean isMovingLeft = false;
    protected boolean isMovingRight = false;
    protected Image currentImage;

    public Character(double x, double y, int health) {
        this.x = x;
        this.y = y;
        this.health = health;
    }
    
    // Getter methods for x and y
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
     // เมธอดลดพลังชีวิต
    public void decreaseHealth(double amount) {
        health -= amount;
        if (health < 0) {
            health = 0;  // If life power drops below 0, set to 0.
        }
    }
    
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    
    // Move to the left
    public void moveLeft() {
        if (x > 0) {
            x -= 10;  // Decrease the x position to the left.   
        }
    }
    // Move right
    public void moveRight() {
        if (x < 800 - 125) {  // Check the rightmost boundary of the screen.
            x += 10;  // Increment the x position value to the right.
        }
    }
    // Stop movement
    public void stopMoving() {
        isMovingLeft = false;
        isMovingRight = false;
    }
    
    // Abstract method to handle key presses
    public abstract void handleKeyReleased(KeyEvent e);
    public abstract void handleKeyPressed(KeyEvent e);
    public abstract void update();  // Update method
    public abstract void draw(Graphics g);  // Draw method
}
