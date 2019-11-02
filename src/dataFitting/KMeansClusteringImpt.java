package dataFitting;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * KMeansClusteringImpt class implements Model interface with fit and predit methods.
 * However, for now we do not have predict funtionality for KMeans.
 * predict method returns null indicating that.
 */
public class KMeansClusteringImpt implements Model<Point> {
  private final int k;
  private final int maxIterNum;
  private final double epsilon;
  private final int ransacIterNum;
  private ArrayList<Cluster> clusters;
  private ArrayList<Point> points;
  private ArrayList<Point> centroids;

  /**
   * Construct a KMeansClusteringImpt.
   * @param k number of centroids
   * @param maxIterNum maximum number of interation
   * @param ransacIterNum iteration number for Ransac
   * @param epsilon error tolerance
   */
  public KMeansClusteringImpt(int k, int maxIterNum, int ransacIterNum, double epsilon) {
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
   * @param k number of centroids
   */
  public KMeansClusteringImpt(int k) {
    this.k = k;
    this.maxIterNum = 10; //FIXME
    this.ransacIterNum = 3;
    this.epsilon = 0.0001;
    this.clusters = new ArrayList<Cluster>(k);
    this.points = new ArrayList<Point>();
    this.setCentroids(new ArrayList<Point>(k));
  }

  @Override
  public Model<Point> fit(double[][] data) {
    addPoints(data);
    if (points.size() < k) {
      throw new IllegalArgumentException("Not sufficient data!");
    }
    double[] minMaxValues = minMaxValues(data);
    double error = Double.MAX_VALUE;
    
    for (int i = 0; i < ransacIterNum; i++) {
       double newError = calculate(minMaxValues);
       if (newError < error) {
         centroids = getCentroidsInRansac();
       }
       clearClusters();
    }
    return this;
  }

  @Override
  public double[] predict(double[] instance) {
    return null;  // We don't have predict funtionality for unsupervised learning.
  }
  
  @Override
  public List<Point> getFittedParameters() {
    List<Point> fittedParameters = centroids;
    return fittedParameters;
  }
  
  /**
   * Return centroids list in string
   */
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
   * @param centroids new centroids
   */
  private void setCentroids(ArrayList<Point> centroids) {
    this.centroids = centroids;
  }
  
  /**
   * Add data points to points array list.
   */
  private void addPoints(double[][] data) {
    for (int i = 0; i < data.length; i++) {
      points.add(new Point(data[i][0], data[i][1]));
    }
  }

  /**
   * Get centroids in a loop of Ransac given the current cluster.
   * @return centroids.
   */
  private ArrayList<Point> getCentroidsInRansac() {
    ArrayList<Point> centroids = new ArrayList<Point>(k);
    for (Cluster cluster : clusters) {
      // Point aux = cluster.getCentroid();
      // Point point = new Point(aux.getX(),aux.getY());
      centroids.add(cluster.getCentroid());
    }
    return centroids;
  }

  /**
   * Clear cluster information.
   */
  private void clearClusters() { 
    clusters = new ArrayList<Cluster>(k);
  }

  /**
   * Find mininum and maximum values of the input data for random centroid generation.
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
   * @param minMaxValues minnum and maximum values of the input data.
   * @return generated random centroid
   */
  private Point createRandomPoint(double[] minMaxValues) {
    Random rand = new Random();
    double x = minMaxValues[0] + (minMaxValues[1] - minMaxValues[0]) * rand.nextDouble();
    double y = minMaxValues[2] + (minMaxValues[3] - minMaxValues[2]) * rand.nextDouble();
    return new Point(x, y);
  }

  /**
   * Assign points to clusters.
   */
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

  /**
   * Update centroids.
   */
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
