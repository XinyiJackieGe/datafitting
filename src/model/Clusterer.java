package model;

/** Clusterer interface extends from Model. */
public interface Clusterer extends Model {
  int cluster(double[] instance);

  /**
   * Get fitted parameters.
   *
   * @return centroids for clusterer.
   */
  double[][] getParameters();
}
