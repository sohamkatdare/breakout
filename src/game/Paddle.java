package game;

import java.awt.*;

/**
 * Represents the player-controlled paddle in the Breakout game.
 * The paddle can move horizontally and interact with the ball.
 */
public class Paddle extends Polygon {
    private int speed;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private int width;

    /**
     * Functional interface for collision checking.
     * Used to implement custom collision detection strategies.
     */
    interface CollisionChecker {
        /**
         * Checks if a point collides with an object.
         *
         * @param point The point to check for collision
         * @return true if the point collides, false otherwise
         */
        boolean checkCollision(Point point); 
    }

    /**
     * Creates a new paddle with the specified dimensions and position.
     *
     * @param width The width of the paddle
     * @param height The height of the paddle
     * @param posX The x-coordinate of the paddle's position
     * @param posY The y-coordinate of the paddle's position
     * @param speed The movement speed of the paddle
     */
    public Paddle(int width, int height, int posX, int posY, int speed) {
        super(new Point[] {
            new Point(0, 0),
            new Point(width, 0),
            new Point(width, height),
            new Point(0, height)
        }, new Point(posX, posY), 0);
        
        this.speed = speed;
        this.width = width;
    }
    
    /**
     * Renders the paddle on the screen.
     *
     * @param brush The graphics context to paint on
     */
    public void paint(Graphics brush) {
        Point[] points = super.getPoints();
        int[] xVals = new int[points.length];
        int[] yVals = new int[points.length];
        
        for (int i = 0; i < points.length; i++) {
            xVals[i] = (int)Math.round(points[i].getX());
            yVals[i] = (int)Math.round(points[i].getY());
        }
        
        brush.setColor(Color.GRAY);
        brush.fillPolygon(xVals, yVals, points.length);
    }
    
    /**
     * Sets the left movement state of the paddle.
     *
     * @param moving true to move left, false to stop moving left
     */
    public void setMovingLeft(boolean moving) {
        this.movingLeft = moving;
    }
    
    /**
     * Sets the right movement state of the paddle.
     *
     * @param moving true to move right, false to stop moving right
     */
    public void setMovingRight(boolean moving) {
        this.movingRight = moving;
    }
    
    /**
     * Updates the paddle's position based on its movement state.
     * Ensures the paddle stays within the screen boundaries.
     *
     * @param screenWidth The width of the screen
     */
    public void update(int screenWidth) {
        if (movingLeft) {
            position.setX(Math.max(0, position.getX() - speed));
        }
        if (movingRight) {
            position.setX(Math.min(screenWidth - width, position.getX() + speed));
        }
    }

    /**
     * Helper method for collision detection using a custom collision checker.
     *
     * @param ball The ball to check collision with
     * @param checker The collision checker to use
     * @return true if the ball collides with the paddle, false otherwise
     */
    public boolean checkCollisionHelper(Ball ball, CollisionChecker checker) {
        Point[] ballPoints = ball.getPoints();
        for (Point p : ballPoints) {
			if (checker.checkCollision(p)) return true;
        }
        return false;
    }

    /**
     * Checks if the ball collides with the paddle.
     * Uses a lambda expression and anonymous class to implement the collision checker.
     *
     * @param ball The ball to check collision with
     * @return true if the ball collides with the paddle, false otherwise
     */
    public boolean checkCollision(Ball ball) {
        CollisionChecker checker = (Point p) -> this.contains(p);
        return checkCollisionHelper(ball, checker);
    }
    
    /**
     * Calculates the angle at which the ball should bounce off the paddle.
     * The angle depends on where the ball hits the paddle.
     *
     * @param ball The ball interacting with the paddle
     * @return The x-component of the ball's new velocity
     */
    public int calculateBallAngle(Ball ball) {
        double paddleCenter = position.getX() + (width / 2);
        double ballCenter = ball.position.getX();
        double difference = ballCenter - paddleCenter;
        
        return (int)(difference / 10);
    }
}
