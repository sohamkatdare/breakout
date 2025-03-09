package game;

import java.awt.*;

public class Ball extends Polygon {

	public Ball(int radius, int startX, int startY) {
		super(8, radius, new Point(startX, startY), 0);
		new BallCollision(radius, startX, startY);
	}

	public void moveBall(int xDist, int yDist) {
		position.setX(position.getX() + xDist);
		position.setY(position.getY() + yDist);
	}

	public void paint(Graphics brush) {
		Point[] points = super.getPoints();
		
		int[] xVals = new int[8];
		int[] yVals = new int[8];
		
		for (int i = 0; i < 8; i++) {
			xVals[i] = (int)Math.round(points[i].getX());
			yVals[i] = (int)Math.round(points[i].getY());
		}

		brush.fillPolygon(xVals, yVals, 8);
	}
