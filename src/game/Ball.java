package game;

import java.awt.*;

public class Ball extends Polygon {
	int radius;
	Polygon triangleHolder;
	StarPoint[] triangles = new StarPoint[6];
	static Color[] colors = new Color[] {Color.red, Color.orange, Color.yellow, 
			Color.green, Color.blue, Color.pink};

	public class StarPoint extends Polygon {
		Color color;

		public StarPoint(int radius, Point position, double rotation, 
				Color color) {
			super(3, radius, position, rotation);
			this.color = color;
		}

	}

	public Ball(int radius, int startX, int startY) {
		super(6, radius, new Point(startX, startY), 0);
		this.radius = radius;
		triangleHolder = new Polygon(6, radius * 2 / 3, new Point(startX, startY), 0);

		for (int i = 0; i < 6; i++) {
			Point[] points = triangleHolder.getPoints();
			int rotation = 60 * i;

			triangles[i] = new StarPoint(radius * 2 / 3, points[i], rotation, colors[i]);
		}
	}

	public void moveBall(int xDist, int yDist) {
		position.setX(position.getX() + xDist);
		position.setY(position.getY() + yDist);
		triangleHolder.position = this.position;
		rotateBall(0);
	}

	public void paint(Graphics brush) {

		for (int i = 0; i < 6; i++) {
			Point[] points = triangles[i].getPoints();

			int[] xVals = new int[] {(int)Math.round(points[0].getX()),
					(int)Math.round(points[1].getX()),(int)Math.round(points[2].getX())};
			int[] yVals = new int[] {(int)Math.round(points[0].getY()),
					(int)Math.round(points[1].getY()),(int)Math.round(points[2].getY())};

			brush.setColor(triangles[i].color);

			brush.fillPolygon(xVals, yVals, 3);
		}
	}

	public void rotateBall(int degrees) {
		this.rotate(degrees);
		triangleHolder.rotate(degrees);
		Point[] points = triangleHolder.getPoints();

		for (int i = 0; i < 6; i++) {
			triangles[i].position = points[i];
			triangles[i].rotate(degrees);
		}
	}

	public int getRadius() {
		return radius;
	}
}
