package dataFitting;

/**
 * Point class contains coordinates of a point and its cluster information. It also calculate the
 * Euclidean distance between this point and other point.sß
 */
public class Point {
  private double x;
  private double y;
  private int centroidId;

  /**
   * Construct a point.
   *
   * @param x coordinate
   * @param y coordinate
   */
  public Point(double x, double y) {
    this.setX(x);
    this.setY(y);
  }

  /**
   * Get x coordinate.
   *
   * @return x
   */
  public double getX() {
    return x;
  }

  /**
   * Set x coodinate.
   *
   * @param x coordinate
   */
  public void setX(double x) {
    this.x = x;
  }

  /**
   * Get y coodinate.
   *
   * @param y
   */
  public double getY() {
    return y;
  }

  /**
   * Set y coordinate.
   *
   * @param y coordinate
   */
  public void setY(double y) {
    this.y = y;
  }

  /**
   * Set a cluster id to the point.
   *
   * @param n cluster id
   */
  public void setCluster(int n) {
    this.centroidId = n;
  }

  /**
   * Get the cluster id of the point.
   *
   * @return cluster id
   */
  public int getCluster() {
    return this.centroidId;
  }

  /**
   * Calculate Euclidean distance between two points.
   *
   * @param p this point
   * @param centroid centroid point
   * @return distance
   */
  public static double distance(Point p, Point centroid) {
    return Math.sqrt(
        Math.pow((centroid.getY() - p.getY()), 2) + Math.pow((centroid.getX() - p.getX()), 2));
  }
  
  /**
   * Return point in a string type.
   * 
   * @return point (x, y) in string
   */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("(");
    sb.append(String.format("%.4f", x));
    sb.append(", ");
    sb.append(String.format("%.4f", y));
    sb.append(")");
      
    return sb.toString();
  }
}
