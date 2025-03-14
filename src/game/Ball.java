package game;

import java.awt.*;

/**
 * Represents the ball in the Breakout game.
 * The ball moves around the screen and interacts with other game elements.
 * It is represented as a star-like shape made up of triangles
 * to allow for visual rotation.
 */
public class Ball extends Polygon {
	private int radius;
	private Polygon triangleHolder;
	private StarPoint[] triangles = new StarPoint[6];

	/**
	 * Each point is a triangle with a specific color.
	 */
	public class StarPoint extends Polygon {
		Color color;

		/**
		 * Creates a new star point with the specified properties.
		 *
		 * @param radius The radius of the star point
		 * @param position The position of the star point
		 * @param rotation The rotation of the star point in degrees
		 * @param color The color of the star point
		 */
		public StarPoint(int radius, Point position, double rotation, 
				Color color) {
			super(3, radius, position, rotation);
			this.color = color;
		}

	}

	/**
	 * Creates a new ball with the specified radius and position.
	 *
	 * @param radius The radius of the ball
	 * @param startX The initial x-coordinate of the ball's position
	 * @param startY The initial y-coordinate of the ball's position
	 */
	public Ball(int radius, int startX, int startY) {
		super(6, radius, new Point(startX, startY), 0);
		this.radius = radius;
		triangleHolder = new Polygon(6, radius * 2 / 3, new Point(startX, startY), 0);
		Color[] colors = new Color[] {Color.red, Color.orange, Color.yellow, 
				Color.green, Color.blue, Color.pink};

		for (int i = 0; i < 6; i++) {
			Point[] points = triangleHolder.getPoints();
			int rotation = 60 * i;

			triangles[i] = new StarPoint(radius * 2 / 3, points[i], rotation, colors[i]);
		}
	}

	/**
	 * Moves the ball by the specified distances in the x and y directions.
	 *
	 * @param xDist The distance to move in the x direction
	 * @param yDist The distance to move in the y direction
	 */
	public void moveBall(int xDist, int yDist) {
		position.setX(position.getX() + xDist);
		position.setY(position.getY() + yDist);
		triangleHolder.position = this.position;
		rotateBall(0);
	}

	/**
	 * Renders the ball on the screen.
	 *
	 * @param brush The graphics context to paint on
	 */
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

	/**
	 * Rotates the ball by the specified number of degrees.
	 *
	 * @param degrees The number of degrees to rotate the ball
	 */
	public void rotateBall(int degrees) {
		this.rotate(degrees);
		triangleHolder.rotate(degrees);
		Point[] points = triangleHolder.getPoints();

		for (int i = 0; i < 6; i++) {
			triangles[i].position = points[i];
			triangles[i].rotate(degrees);
		}
	}

	/**
	 * Gets the radius of the ball.
	 *
	 * @return The radius of the ball
	 */
	public int getRadius() {
		return radius;
	}
}
