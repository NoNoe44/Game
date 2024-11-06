
package game;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameControl extends KeyAdapter {
    private Panda panda;
    private Hippo hippo;
    private boolean gameStarted;   // เก็บสถานะเกม

    public GameControl(Panda panda, Hippo hippo, boolean gameStarted) {
        this.panda = panda;
        this.hippo = hippo;
         this.gameStarted = gameStarted;  // เก็บค่า gameStarted
    }
    // Method เพื่ออัพเดทค่า gameStarted
    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }
    @Override
    public void keyPressed(KeyEvent e) {
    if (gameStarted) {
        // Handle key presses for both characters
        if (e.getKeyCode() == KeyEvent.VK_A) {  // กด 'A' เพื่อเดินซ้าย
            panda.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {  // กด 'D' เพื่อเดินขวา
            panda.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_W) {  // กด 'W' เพื่อยิง
            panda.shoot();
        }
        
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {  // กดลูกศรซ้ายให้ฮิปโปเดินซ้าย
            hippo.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {  // กดลูกศรขวาให้ฮิปโปเดินขวา
            hippo.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {  // กดลูกศรขึ้นเพื่อให้ฮิปโปยิง
            hippo.shoot();
        }
    }
}

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // เมื่อปล่อยปุ่ม จะหยุดการเคลื่อนที่
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_D) {
            panda.stopMoving();  // แพนด้าหยุดเดิน
        }

        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
            hippo.stopMoving(); // ฮิปโปหยุดเดิน
        }
    }
    
}
