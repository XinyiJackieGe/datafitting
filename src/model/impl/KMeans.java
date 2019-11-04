package model.impl;

import java.util.ArrayList;
import java.util.Random;
import model.Clusterer;

/**
 * KMeansClusteringImpt class implements Model interface with fit and predit methods. However, for
 * now we do not have predict funtionality for KMeans. predict method returns null indicating that.
 */
public class KMeans implements Clusterer {
  private final int k;
  private final int maxIterNum;
  private final double epsilon;
  private final int ransacIterNum;
  private ArrayList<Cluster> clusters;
  private ArrayList<Point> points;
  private ArrayList<Point> centroids;

  

  /**
   * Construct a point.
   *
   * @param x coordinate
   * @param y coordinate
   */
  public static class Point {
    private double x;
    private double y;
    private int centroidId;
    
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

  /**
   * Construct a KMeansClustering.
   *
   * @param k number of centroids
   * @param maxIterNum maximum number of interation
   * @param ransacIterNum iteration number for Ransac
   * @param epsilon error tolerance
   */
  public KMeans(int k, int maxIterNum, int ransacIterNum, double epsilon) {
    this.k = k;
    this.maxIterNum = maxIterNum;
    this.ransacIterNum = ransacIterNum;
    this.epsilon = epsilon;
    this.clusters = new ArrayList<Cluster>(k);
    this.points = new ArrayList<Point>();
    this.setCentroids(new ArrayList<Point>(k));
  }
  
  /**
   * Construct a KMeansClusteringImpt with default values for some fields.
   *
   * @param k number of centroids
   */
  public KMeans(int k) {
    this.k = k;
    this.maxIterNum = 10; // FIXME
    this.ransacIterNum = 3;
    this.epsilon = 0.0001;
    this.clusters = new ArrayList<Cluster>(k);
    this.points = new ArrayList<Point>();
    this.setCentroids(new ArrayList<Point>(k));
  }

  @Override
  public void fit(double[][] data) {
    addPoints(data);
    if (points.size() < k) {
      throw new IllegalArgumentException("Not sufficient data!");
    }
    double[] minMaxValues = minMaxValues(data);
    double error = Double.MAX_VALUE;

    for (int i = 0; i < ransacIterNum; i++) {
      clearClusters();
      double newError = calculate(minMaxValues);
      if (newError < error) {
        centroids = getCentroidsInRansac();
      }
    }

  }

  @Override
  public int cluster(double[] instance) {
    Point instancePoint = new Point(instance[0], instance[1]);
    double max = Double.MAX_VALUE;
    double min = max;
    int cluster = 0;
    double distance = 0.0;

    for (int i = 0; i < k; i++) {
        Cluster c = clusters.get(i);
        distance = Point.distance(instancePoint, c.getCentroid());
        if (distance < min) {
          min = distance;
          cluster = i;
        }
      }
    instancePoint.setCluster(cluster);
    
    return instancePoint.getCluster();
  }

  @Override
  public double[][] getParameters() {
    double[][] fittedParameters = new double[k][2];
    for (int i = 0; i < k; i++) {
      fittedParameters[i][0] = centroids.get(i).getX();
      fittedParameters[i][1] = centroids.get(i).getY();
    }
    return fittedParameters;
  }
  
  /** Return centroids list in string */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < centroids.size(); i++) {
      sb.append(centroids.get(i).toString());
      sb.append(" , ");
    }
    return sb.toString();
  }

  /**
   * Set centroids for the clusters.
   *
   * @param centroids new centroids
   */
  private void setCentroids(ArrayList<Point> centroids) {
    this.centroids = centroids;
  }

  /** Add data points to points array list. */
  private void addPoints(double[][] data) {
    for (int i = 0; i < data.length; i++) {
      points.add(new Point(data[i][0], data[i][1]));
    }
  }
  
  /**
   * Get centroids in a loop of Ransac given the current cluster.
   *
   * @return centroids.
   */
  private ArrayList<Point> getCentroidsInRansac() {
    ArrayList<Point> centroids = new ArrayList<Point>(k);
    for (Cluster cluster : clusters) {
      centroids.add(cluster.getCentroid());
    }
    return centroids;
  }

  /** Clear cluster information. */
  private void clearClusters() {
    clusters = new ArrayList<Cluster>(k);
  }

  /**
   * Find mininum and maximum values of the input data for random centroid generation.
   *
   * @param data input data array
   * @return x mininum, maximum, y minimum, maximum
   */
  private double[] minMaxValues(double[][] data) {
    double minX = Double.MAX_VALUE;
    double minY = Double.MAX_VALUE;
    double maxX = Double.MIN_VALUE;
    double maxY = Double.MIN_VALUE;
    for (int i = 0; i < points.size(); i++) {
      if (data[i][0] < minX) {
        minX = data[i][0];
      } else if (data[i][0] > maxX) {
        maxX = data[i][0];
      }
      if (data[i][1] < minY) {
        minY = data[i][1];
      } else if (data[i][1] > maxY) {
        maxY = data[i][1];
      }
    }
    return new double[] {minX, maxX, minY, maxY};
  }

  /**
   * Creat a random point as a centroid.
   *
   * @param minMaxValues minnum and maximum values of the input data.
   * @return generated random centroid
   */
  private Point createRandomPoint(double[] minMaxValues) {
    Random rand = new Random();
    double x = minMaxValues[0] + (minMaxValues[1] - minMaxValues[0]) * rand.nextDouble();
    double y = minMaxValues[2] + (minMaxValues[3] - minMaxValues[2]) * rand.nextDouble();
    return new Point(x, y);
  }

  /** Assign points to clusters. */
  private void assignCluster() {
    double max = Double.MAX_VALUE;
    double min = max;
    int cluster = 0;
    double distance = 0.0;

    for (Point point : points) {
      min = max;
      for (int i = 0; i < k; i++) {
        Cluster c = clusters.get(i);
        distance = Point.distance(point, c.getCentroid());
        if (distance < min) {
          min = distance;
          cluster = i;
        }
      }
      point.setCluster(cluster);
      clusters.get(cluster).addPoint(point);
    }
  }

  /** Update centroids. */
  private void updateCentroids() {
    for (int i = 0; i < k; i++) {
      double sumX = 0;
      double sumY = 0;
      ArrayList<Point> list = clusters.get(i).getPoints();
      int instanceLength = list.size();

      for (Point point : list) {
        sumX += point.getX();
        sumY += point.getY();
      }

      if (instanceLength != 0) {
        clusters.get(i).getCentroid().setX(sumX / instanceLength);
        clusters.get(i).getCentroid().setY(sumY / instanceLength);
      }
    }
  }

  /**
   * Calculate percentage error.
   *
   * @param lastError error in the last iteration
   * @return percentage error
   */
  private double calculatePerctageError(double lastError) {
    double distance = 0;
    for (Point point : points) {
      distance += Point.distance(point, clusters.get(point.getCluster()).getCentroid());
    }
    double newError = distance / points.size();
    return Math.abs(newError - lastError) / lastError;
  }

  /**
   * Run K-means algorithm and returns percentage error.
   *
   * @param minMaxValues mininum and maximum values of the input data
   * @return percentage error
   */
  private double calculate(double[] minMaxValues) {
    // Initialize random centroids.
    for (int i = 0; i < k; i++) {
      Point centroid = createRandomPoint(minMaxValues);
      Cluster cluster = new Cluster(i);
      cluster.setCentroid(centroid);
      clusters.add(cluster);
    }
    // Initialize error.
    double lastError = Double.MAX_VALUE;
    for (int i = 0; i < maxIterNum; i++) {
      assignCluster();
      updateCentroids();
      double newError = calculatePerctageError(lastError);
      if (newError < epsilon) {
        break;
      }
      lastError = newError;
    }

    return lastError;
  }
}
