package game;

public class Ball {
  Polygon[8] ballTriangles;
  Point origin;

  public Ball(radius, startX, startY) {
    Point[] triangle = new Point[] {startX, startY, startX+radius, startY, startX+radius*Math.cos(45), startY+radius*Math.sin(45)};
    origin = new Point(startX, startY);
    for (int i = 0; i < ballTriangles.length; i++) {
      ballTriangles[i] = new Polygon(triangle, origin, i*45);
    }
  }

  public void moveBall(xDist, yDist) {
    origin.setX = origin.getX + xDist;
    origin.setY = origin.getY + yDist;

    // commented out as it may not be necessary, we will determine if that's true while testing
    //for (Polygon triangle : ballTriangles) {
    //  triangle.inPosition
    //}
}
