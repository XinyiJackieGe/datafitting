package model;

/** Model interface for both Regressor and Clusterer. */
public interface Model {
  /**
   * Fit model given data
   *
   * @param data double array data
   */
  void fit(double[][] data);
}
