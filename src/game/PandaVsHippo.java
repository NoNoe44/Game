package game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class PandaVsHippo extends JPanel implements ActionListener, KeyListener {

    private Panda panda;
    private Hippo hippo;
    private ArrayList<Projectile> pandaProjectiles = new ArrayList<>();
    private ArrayList<Projectile> hippoProjectiles = new ArrayList<>();
    private Image backgroundImage;
    private Timer timer;
    private final double GRAVITY = 0.5;
    private int countdownTime = 3;
    private Timer countdownTimer;
    private boolean countdownStarted = false;
    private JComboBox<String> timeComboBox;
    private JComboBox<String> mapComboBox;
    private JButton startButton;
    private boolean gameStarted = false;
    private GameControl gameControl;
    private boolean winMessageDisplayed = false;
    private int poleX = 370, poleY = 300;
    private int poleWidth = 20, poleHeight = 200;
    

    public PandaVsHippo() {
        panda = new Panda(100, 400);
        hippo = new Hippo(500, 400);
        backgroundImage = new ImageIcon("black ground3.png").getImage();
        gameControl = new GameControl(panda, hippo, gameStarted);
        addKeyListener(gameControl);
        setupUI();
        setupGameTimers();
        setFocusable(true);  // Give JPanel focus to listen to key
        addKeyListener(this);  // Make JPanel accept the key
        
    }

    private void setupUI() {
        String[] times = {"1 minute", "2 minute", "3 minute"};
        timeComboBox = new JComboBox<>(times);
        add(timeComboBox);

        String[] maps = {"Map 1", "Map 2", "Map 3"};
        mapComboBox = new JComboBox<>(maps);
        add(mapComboBox);

        startButton = new JButton("Start Game");
        startButton.addActionListener(e -> startGame());
        add(startButton);
    }

    private void setupGameTimers() {
        timer = new Timer(0, this);
        timer.start();

        countdownTimer = new Timer(1000, e -> handleCountdown());
    }

    private void handleCountdown() {
        if (countdownTime > 0) {
            countdownTime--;
        } else {
            countdownTimer.stop();
            timer.stop();
            endGame();
        }
    }

    private void startGame() {
        String selectedTime = (String) timeComboBox.getSelectedItem();
        String selectedMap = (String) mapComboBox.getSelectedItem();
        
        setCountdownTime(selectedTime);
        loadMap(selectedMap);
        
        hideUIElements();
        gameStarted = true;
        countdownStarted = true;
        // Reset state and add new KeyListener
        removeKeyListener(gameControl);
        gameControl = new GameControl(panda, hippo, gameStarted);
        addKeyListener(gameControl);
        countdownTimer.start();
        timer.start();  // Start the timer again.  
    }

    private void setCountdownTime(String selectedTime) {
        switch (selectedTime) {
            case "1 minute":
                countdownTime = 60;
                break;
            case "2 minute":
                countdownTime = 120;
                break;
            case "3 minute":
                countdownTime = 180;
                break;
        }
    }

    private void loadMap(String selectedMap) {
        switch (selectedMap) {
            case "Map 1":
                backgroundImage = new ImageIcon("black ground3.png").getImage();
                break;
            case "Map 2":
                backgroundImage = new ImageIcon("black ground 4.png").getImage();
                break;
            case "Map 3":
                backgroundImage = new ImageIcon("black ground1.png").getImage();
                break;
        }
    }

    private void hideUIElements() {
        timeComboBox.setVisible(false);
        mapComboBox.setVisible(false);
        startButton.setVisible(false);
    }

    private void endGame() {
        String winnerMessage = determineWinner();
        JOptionPane.showMessageDialog(this, winnerMessage);
        resetGame();
    }

    private String determineWinner() {
        if (panda.getHealth() == hippo.getHealth()) {
            return "Draw!";
        } else if (panda.getHealth() > hippo.getHealth()) {
            return "Panda Wins!";
        } else {
            return "Hippo Wins!";
        }
    }

    private void resetGame() {
        panda.reset();
        hippo.reset();
        pandaProjectiles.clear();
        hippoProjectiles.clear();
        countdownTime = 3;
        gameStarted = false;
        countdownStarted = false;
        
        removeKeyListener(gameControl);
        gameControl = new GameControl(panda, hippo, gameStarted);
        addKeyListener(gameControl);
        
        showUIElements();
        repaint();
    }

    private void showUIElements() {
        timeComboBox.setVisible(true);
        mapComboBox.setVisible(true);
        startButton.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        drawHealthBar(g);
        
        g.setColor(Color.BLACK);  // Color of the pole
        g.fillRect(poleX, poleY, poleWidth, poleHeight);
        
        if (countdownStarted && countdownTime > 0) {
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.setColor(Color.RED);
        String countdownText = String.valueOf(countdownTime);
        int textWidth = g.getFontMetrics().stringWidth(countdownText);
        int textHeight = g.getFontMetrics().getHeight();
        g.drawString(countdownText, (getWidth() - textWidth) / 2, textHeight);
        
       }
        drawGameObjects(g);
        drawHealthBar(g);
    }
    
    private void drawGameObjects(Graphics g) {
        panda.draw(g);
        hippo.draw(g);

        for (Projectile p : pandaProjectiles) {
            if (p.isActive()) {
                g.drawImage(p.getImage(), (int) p.getX(), (int) p.getY(), 30, 30, this);
            }
        }
        // Draw the hippo's bullets.
        for (Projectile p : hippoProjectiles) {
            if (p.isActive()) {
                g.drawImage(p.getImage(), (int) p.getX(), (int) p.getY(), 30, 30, this);
            }
        }
    }

    private void drawHealthBar(Graphics g) {
        // Draw the panda's bloodline.
        g.setColor(Color.GRAY);
        g.fillRect(20, 20, 200, 20);
        g.setColor(Color.GREEN);
        g.fillRect(20, 20, (int) (100 * ((double) panda.getHealth() / 100)), 20);

        // Draw the hippo's bloodline.
        g.setColor(Color.GRAY);
        g.fillRect(getWidth() - 220, 20, 200, 20);
        g.setColor(Color.GREEN);
        g.fillRect(getWidth() - 220, 20, (int) (100 * ((double) hippo.getHealth() / 100)), 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameStarted) {
            panda.update();
            hippo.update();
            updateProjectiles();
            checkCollisions();
            repaint();
            checkGameOver();
        }
    }
    private void checkGameOver() {
    // If the panda runs out of blood
    if (panda.getHealth() <= 0) {
        String winnerMessage = "Hippo Wins!";  
        JOptionPane.showMessageDialog(this, winnerMessage);
        endGame();     
    }
    // If the hippo runs out of blood
    else if (hippo.getHealth() <= 0) {
        String winnerMessage = "Panda Wins!"; 
        JOptionPane.showMessageDialog(this, winnerMessage);
        endGame();
    }
}
   
    private void updateProjectiles() {
        for (Projectile p : pandaProjectiles) {
            p.update();
        }
        for (Projectile p : hippoProjectiles) {
            p.update();
        }
    }

    public void checkCollisions() {
        // Check the collision between Panda and Hippo bullets.
        for (Projectile p : panda.getProjectiles()) {
            if (p.isActive()) {
                if (p.getX() > hippo.getX() && p.getX() < hippo.getX() + 100 &&
                    p.getY() > hippo.getY() && p.getY() < hippo.getY() + 100) {
                    hippo.decreaseHealth(p.getDamage());  // Reduces Hippo's life force.
                    p.setActive(false);  // Disable bullets
                }
            }
        }
        for (Projectile p : hippo.getProjectiles()) {
            if (p.isActive()) {
                if (p.getX() > panda.getX() && p.getX() < panda.getX() + 100 &&
                    p.getY() > panda.getY() && p.getY() < panda.getY() + 100) {
                    panda.decreaseHealth(p.getDamage());  // Reduces Panda's life force.
                    p.setActive(false);  // Disable bullets
                }
            }
        }
        // Check for collision with center pillar and stop movement.
    if (checkCollisionWithPole((int) panda.getX(), (int) panda.getY(), 100, 100)) {
        if (panda.getX() + 100 > poleX && panda.getX() < poleX + poleWidth) {
            if (panda.getX() < poleX) {
                panda.setX(poleX - 100);  // Push out the left side of the pillar.
            } else {
                panda.setX(poleX + poleWidth);  // Push out the right side of the pole.
            }
        }  
    }

    if ((int) panda.getY() + 100 > (int) poleY && (int) panda.getY() < (int) poleY + (int) poleHeight) {
        if (hippo.getX() + 100 > poleX && hippo.getX() < poleX + poleWidth) {
            if (hippo.getX() < poleX) {
                hippo.setX(poleX - 100);  // Push out the left side of the pillar.
            } else {
                hippo.setX(poleX + poleWidth);  // Push out the right side of the pole.
            }
        }
    }
    // Check screen bounds for Panda
    if (panda.getX() < 0) panda.setX(0);
    if (panda.getX() + 100 > getWidth()) panda.setX(getWidth() - 100);
    
    // Check screen bounds for Hippo
    if (hippo.getX() < 0) hippo.setX(0);
    if (hippo.getX() + 100 > getWidth()) hippo.setX(getWidth() - 100);
    }
 
    private boolean checkCollisionWithPole(int x, int y, int width, int height) {
    return x < poleX + poleWidth && x + width > poleX &&
           y < poleY + poleHeight && y + height > poleY;
}
    @Override
    public void keyPressed(KeyEvent e) {
        if (gameStarted) {   
            // Handle key presses for both characters
            panda.handleKeyPressed(e);
            hippo.handleKeyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
       if (gameStarted) {  // Check if gameStarted is true
        panda.handleKeyReleased(e);
        hippo.handleKeyReleased(e);
    }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Panda vs Hippo");
        PandaVsHippo game = new PandaVsHippo();
        frame.add(game);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null); // Setting to start in the middle of the screen.
        frame.setResizable(false);
    }
}
