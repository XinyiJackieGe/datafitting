package model;

/** Regression interface for regressors. */
public interface Regression extends Model {
  /**
   * Predict y value given an x instance.
   * @param instance an x instance
   * @return predicted y
   */
  double regress(double instance);

  /**
   * Get fitted parameters.
   * @return fitted parameters a, b, c
   */
  double[] getParameters();
}
