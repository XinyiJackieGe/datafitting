package dataFitting;

import java.util.ArrayList;

/**
 * Cluster class contains points within the cluster. 
 */
public class Cluster {
  private int id;
  private Point centroid;
  private ArrayList<Point> points;

  /**
   * Construct a Cluster object.
   * @param id cluster id
   */
  public Cluster(int id) {
    this.id = id;
    this.centroid = null;
    this.points = new ArrayList<Point>();
  }

  /** Get centroid point of the cluster.
   * 
   * @return centroid point
   */
  public Point getCentroid() {
    return centroid;
  }

  /**
   * Set centroid point to the cluster.
   * @param centroid point
   */
  public void setCentroid(Point centroid) {
    this.centroid = centroid;
  }

  /**
   * Get cluster id of this cluster.
   * @return cluster id
   */
  public int getId() {
    return id;
  }

  /**
   * Get points within with this cluster.
   * @return points within with this cluster
   */
  public ArrayList<Point> getPoints() {
    return points;
  }

  /**
   * Add a point to the cluster.
   * @param point new point
   */
  public void addPoint(Point point) {
    points.add(point);
  }

  /** 
   * Set points.
   * @param points
   */
  public void setPoints(ArrayList<Point> points) {
    this.points = points;
  }

  /**
   * Clear the cluster.
   */
  public void clear() {
    points.clear();
  }
}
