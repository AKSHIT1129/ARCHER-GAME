import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ArcheryGame extends JPanel implements ActionListener, KeyListener {

    private Timer timer;
    private int arrowX, arrowY;
    private boolean arrowShot;
    private int targetX, targetY;
    private int targetSpeed;
    private int score;

    public ArcheryGame() {
        setPreferredSize(new Dimension(600, 400));
        setBackground(Color.WHITE);

        arrowX = 275; // starting arrow position (middle bottom)
        arrowY = 350;
        arrowShot = false;

        targetX = 0;
        targetY = 50;
        targetSpeed = 4;

        score = 0;

        timer = new Timer(20, this);
        timer.start();

        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw target (red circle)
        g.setColor(Color.RED);
        g.fillOval(targetX, targetY, 50, 50);

        // Draw arrow (black rectangle)
        g.setColor(Color.BLACK);
        g.fillRect(arrowX, arrowY, 10, 30);

        // Draw bow (just a simple line)
        g.setColor(Color.DARK_GRAY);
        g.drawLine(arrowX + 5, 380, arrowX + 5, 350);

        // Draw score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + score, 10, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Move target horizontally, bounce on edges
        targetX += targetSpeed;
        if (targetX <= 0 || targetX >= getWidth() - 50) {
            targetSpeed = -targetSpeed;
        }

        // Move arrow if shot
        if (arrowShot) {
            arrowY -= 10;
            if (arrowY < 0) {
                resetArrow();
            }

            // Check for collision with target
            Rectangle arrowRect = new Rectangle(arrowX, arrowY, 10, 30);
            Rectangle targetRect = new Rectangle(targetX, targetY, 50, 50);

            if (arrowRect.intersects(targetRect)) {
                score++;
                resetArrow();
            }
        }

        repaint();
    }

    private void resetArrow() {
        arrowShot = false;
        arrowX = 275;
        arrowY = 350;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!arrowShot && e.getKeyCode() == KeyEvent.VK_SPACE) {
            arrowShot = true;
        }

        // Move arrow left/right with arrow keys only if not shot
        if (!arrowShot) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT && arrowX > 0) {
                arrowX -= 15;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT && arrowX < getWidth() - 10) {
                arrowX += 15;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { /* Not used */ }
    @Override
    public void keyTyped(KeyEvent e) { /* Not used */ }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple Archery Game");
        ArcheryGame gamePanel = new ArcheryGame();

        frame.add(gamePanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
