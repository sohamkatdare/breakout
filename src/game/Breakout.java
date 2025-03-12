package game;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
class BreakoutGame extends Game implements KeyListener {

    private Ball ball;
    private BlockManager blockManager;
    private Paddle paddle;
    
    private boolean gameStarted = false;
    private int score = 0;
    private int lives = 3;
    
    private int ballSpeedX = 3;
    private int ballSpeedY = -3;
    
    public BreakoutGame() {
        super("Breakout Game", 800, 600);
        this.setFocusable(true);
     	this.requestFocus();
        
        ball = new Ball(10, 400, 500);
        paddle = new Paddle(100, 10, 350, 550, 5);
        
        blockManager = new BlockManager();
        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, 
        		Color.BLUE};
        blockManager.createBlockGrid(5, 11, 60, 20, 35, 50, 10, colors);
        this.addKeyListener(this);
    }
    
    public void paint(Graphics brush) {
        brush.setColor(Color.BLACK);
        brush.fillRect(0, 0, width, height);
        
        blockManager.paint(brush);
        paddle.paint(brush);
        
        ball.paint(brush);
        
        brush.setColor(Color.WHITE);
        brush.drawString("Score: " + score, 20, 20);
        brush.drawString("Lives: " + lives, width - 80, 20);
        
        if (!gameStarted) {
        	if (lives > 0) {
        		brush.drawString("Press SPACE to start", 350, 300);
        	}
        } else {
            updateGame();
        }
        
        if (lives <= 0) {
            brush.drawString("GAME OVER", 350, 300);
            brush.drawString("Press R to restart", 350, 320);
        } else if (blockManager.allBlocksDestroyed()) {
            brush.drawString("YOU WIN!", 350, 300);
            brush.drawString("Press R to restart", 350, 320);
        }
    }
    
    private void updateGame() {
        if (lives <= 0 || blockManager.allBlocksDestroyed()) {
            return;
        }
        
        // Update paddle position
        paddle.update(width);
        
        ball.moveBall(ballSpeedX, ballSpeedY);
        ball.rotateBall(1);
        
        // Handle wall collisions
        if (ball.position.getX() - ball.getRadius() <= 0 || 
        		ball.position.getX() + ball.getRadius() >= width) {
            ballSpeedX = -ballSpeedX;
        }
        if (ball.position.getY() - ball.getRadius() <= 0) {
            ballSpeedY = -ballSpeedY;
        }
        
        // Handle paddle collision
        if (paddle.checkCollision(ball)) {
            ballSpeedY = -Math.abs(ballSpeedY); // Always go up after hitting paddle
            ballSpeedX = paddle.calculateBallAngle(ball);
        }
        
        // Handle block collisions
        int blockCollisions = blockManager.checkCollisions(ball);
        if (blockCollisions == 1) {
            ballSpeedX = -ballSpeedX;
            score += 10;
        } else if (blockCollisions == 2) {
            ballSpeedY = Math.abs(ballSpeedY);
            score += 10;
        } else if (blockCollisions == 3){
            ballSpeedY = -1 * Math.abs(ballSpeedY);
            score += 10;
        }
        
        // Handle ball falling off the bottom
        if (ball.position.getY() > height) {
            lives--;
            resetBall();
        }
    }
    
    private void resetBall() {
        ball = new Ball(10, 400, 500);
        ballSpeedX = 3;
        ballSpeedY = -3;
        paddle.position = new Point(350, 550);
        gameStarted = false;
    }
    
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_LEFT) {
            paddle.setMovingLeft(true);
        } else if (key == KeyEvent.VK_RIGHT) {
            paddle.setMovingRight(true);
        } else if (key == KeyEvent.VK_SPACE) {
            gameStarted = true;
        } else if (key == KeyEvent.VK_R) {
            score = 0;
            lives = 3;
            resetBall();
            paddle = new Paddle(100, 10, 350, 550, 5);
            blockManager = new BlockManager();
            Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE};
            blockManager.createBlockGrid(5, 10, 60, 20, 100, 50, 10, colors);
        }
    }
    
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_LEFT) {
            paddle.setMovingLeft(false);
        } else if (key == KeyEvent.VK_RIGHT) {
            paddle.setMovingRight(false);
        }
    }
    
    public static void main(String[] args) {
        BreakoutGame game = new BreakoutGame();
        game.repaint();
    }

	public void keyTyped(KeyEvent e) {		
	}
}
