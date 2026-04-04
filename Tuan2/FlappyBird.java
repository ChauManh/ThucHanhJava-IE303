import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    private static final int WIDTH = 360;
    private static final int HEIGHT = 640;
    private static final int BIRD_SIZE = 30;
    private static final int PIPE_WIDTH = 64;
    private static final int PIPE_GAP = 150;
    private static final int GRAVITY = 1;
    private static final int JUMP_STRENGTH = -15;

    private Image backgroundImg;
    private Image birdImg;
    private Image topPipeImg;
    private Image bottomPipeImg;

    private int birdY;
    private int birdVelocity;
    private ArrayList<Rectangle> pipes;
    private int score;
    private boolean gameOver;
    private boolean started;
    private Timer timer;
    private Random rand;

    public FlappyBird() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        // Load images
        backgroundImg = new ImageIcon("flappybirdbg.png").getImage();
        birdImg = new ImageIcon("flappybird.png").getImage();
        topPipeImg = new ImageIcon("toppipe.png").getImage();
        bottomPipeImg = new ImageIcon("bottompipe.png").getImage();

        birdY = HEIGHT / 2;
        birdVelocity = 0;
        pipes = new ArrayList<>();
        score = 0;
        gameOver = false;
        started = false;
        rand = new Random();

        timer = new Timer(20, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImg, 0, 0, WIDTH, HEIGHT, null);

        // Draw bird
        g.drawImage(birdImg, 50, birdY, BIRD_SIZE, BIRD_SIZE, null);

        // Draw pipes
        for (Rectangle pipe : pipes) {
            if (pipe.y == 0) { // top pipe
                g.drawImage(topPipeImg, pipe.x, pipe.y, PIPE_WIDTH, pipe.height, null);
            } else { // bottom pipe
                g.drawImage(bottomPipeImg, pipe.x, pipe.y, PIPE_WIDTH, pipe.height, null);
            }
        }

        // Draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics fm = g.getFontMetrics();
        String scoreText = "Score: " + score;
        g.drawString(scoreText, (WIDTH - fm.stringWidth(scoreText)) / 2, 50);

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            String gameOverText = "Game Over";
            fm = g.getFontMetrics();
            g.drawString(gameOverText, (WIDTH - fm.stringWidth(gameOverText)) / 2, HEIGHT / 2);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            String restartText = "Press R to Restart";
            fm = g.getFontMetrics();
            g.drawString(restartText, (WIDTH - fm.stringWidth(restartText)) / 2, HEIGHT / 2 + 50);
        }

        if (!started) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            String startText = "Press Space to Start";
            fm = g.getFontMetrics();
            g.drawString(startText, (WIDTH - fm.stringWidth(startText)) / 2, HEIGHT / 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (started && !gameOver) {
            // Bird physics
            birdVelocity += GRAVITY;
            birdY += birdVelocity;

            // Move pipes
            for (int i = 0; i < pipes.size(); i++) {
                Rectangle pipe = pipes.get(i);
                pipe.x -= 5;

                // Remove off-screen pipes
                if (pipe.x + PIPE_WIDTH < 0) {
                    pipes.remove(i);
                    i--;
                }
            }

            // Add new pipes
            if (pipes.isEmpty() || pipes.get(pipes.size() - 1).x < WIDTH - 200) {
                int pipeHeight = rand.nextInt(HEIGHT - PIPE_GAP - 100) + 50;
                pipes.add(new Rectangle(WIDTH, 0, PIPE_WIDTH, pipeHeight)); // top
                pipes.add(new Rectangle(WIDTH, pipeHeight + PIPE_GAP, PIPE_WIDTH, HEIGHT - pipeHeight - PIPE_GAP)); // bottom
            }

            // Check collisions
            Rectangle birdRect = new Rectangle(50, birdY, BIRD_SIZE, BIRD_SIZE);
            for (Rectangle pipe : pipes) {
                if (birdRect.intersects(pipe)) {
                    gameOver = true;
                }
            }
            if (birdY < 0 || birdY > HEIGHT) {
                gameOver = true;
            }

            // Score
            for (Rectangle pipe : pipes) {
                if (pipe.x + PIPE_WIDTH == 50 && pipe.y != 0) { // passed a pipe
                    score++;
                }
            }
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!started) {
                started = true;
            } else if (!gameOver) {
                birdVelocity = JUMP_STRENGTH;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            restart();
        }
    }

    private void restart() {
        birdY = HEIGHT / 2;
        birdVelocity = 0;
        pipes.clear();
        score = 0;
        gameOver = false;
        started = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new FlappyBird());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}