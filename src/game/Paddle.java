package game;

import java.awt.*;

public class Paddle extends Polygon {
    private int speed;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private int width;

    interface CollisionChecker { // functional interface
        boolean checkCollision(Point point); 
    }

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
    
    public void setMovingLeft(boolean moving) {
        this.movingLeft = moving;
    }
    
    public void setMovingRight(boolean moving) {
        this.movingRight = moving;
    }
    
    public void update(int screenWidth) {
        if (movingLeft) {
            position.setX(Math.max(0, position.getX() - speed));
        }
        if (movingRight) {
            position.setX(Math.min(screenWidth - width, position.getX() + speed));
        }
    }

    public boolean checkCollisionHelper(Ball ball, CollisionChecker checker) {
        Point[] ballPoints = ball.getPoints();
        for (Point p : ballPoints) {
			boolean collides = checker.checkCollision(p);
			if (collides) {
				return true;
			}
        }
        return false;
    }

    public boolean checkCollision(Ball ball) {
        CollisionChecker checker = (Point p) -> this.contains(p); // lambda expression + anonymous class
        return checkCollisionHelper(ball, checker);
    }
    
    public int calculateBallAngle(Ball ball) {
        double paddleCenter = position.getX() + (width / 2);
        double ballCenter = ball.position.getX();
        double difference = ballCenter - paddleCenter;
        
        return (int)(difference / 10);
    }
}
