package game;

import java.awt.Image;
import java.awt.List;

public class Projectile {
    private double x, y;
    private double velocityX, velocityY;
    private boolean isActive;
    private Image image;  // Image of a bullet
    private boolean active; // active variable is private
    private int damage; //  Field for storing bullet damage values.
    
    // Constructor gets initial position, velocity, and bullet image.
    public Projectile(double startX, double startY, double speedX, double speedY, Image image, int damage) {
        this.x = startX;
        this.y = startY;
        this.velocityX = speedX;
        this.velocityY = speedY;
        this.isActive = true;
        this.image = image;
        this.damage = damage; // Field for storing bullet damage values.
    }

    // Update the position of the bullet.
    public void update() {
        if (isActive) {
            x += velocityX;  // Increment the X position value according to the speed.
            y += velocityY;  // Increment Y position value according to speed.
            velocityY += 0.5;   // Gravity (increases Y speed every time)

            // If the bullet leaves the screen (800x600 area), cancel the rendering.
            if (x < 0 || x > 800 || y > 600) {
                isActive = false;
            }
        }
    }
    
    // Function to check the status of the bullets.
    public boolean isActive() {
        return isActive;
    }

    // Function to set ammo to inactive
    public void setActive(boolean active) {
        this.isActive = active;
    }
    // Function to get the image value of the bullet.
    public Image getImage() {
        return image;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    // เพิ่ม getter สำหรับดาเมจของกระสุน
    public int getDamage() {
        return damage;
    }
    
}


  
   

