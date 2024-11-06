
package game;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameControl extends KeyAdapter {
    private Panda panda;
    private Hippo hippo;
    private boolean gameStarted;   // Save game state

    public GameControl(Panda panda, Hippo hippo, boolean gameStarted) {
        this.panda = panda;
        this.hippo = hippo;
         this.gameStarted = gameStarted;  // Collect gameStarted value
    }
    // Method to update gameStarted value.
    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }
    @Override
    public void keyPressed(KeyEvent e) {
    if (gameStarted) {
        // Handle key presses for both characters
        if (e.getKeyCode() == KeyEvent.VK_A) { // Press 'A' to move left.
            panda.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_D) { // Press 'D' to move right.
            panda.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_W) {  // Press 'W' to shoot.
            panda.shoot();
        }
        
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {  // Press the left arrow to make the hippo walk left.
            hippo.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {  // Press the right arrow to make the hippo walk right.
            hippo.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {  // Press the up arrow to make the hippo shoot.
            hippo.shoot();
        }
    }
}

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // When the button is released, the movement will stop.
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_D) {
            panda.stopMoving();  // The panda stopped walking.
        }

        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
            hippo.stopMoving(); // The hippo stopped walking.
        }
    }
    
}
