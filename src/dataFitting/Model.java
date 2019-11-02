package dataFitting;

import java.util.List;

/** Model interface defines fit and predict two functions. */
public interface Model<T> {

  /**
   * Fit data into a model.
   *
   * @param data input x, y
   * @return new model object
   */
  Model<T> fit(double[][] data);

  /**
   * Predict y given x.
   *
   * @param instance x instances
   * @return predicted y
   */
  double[] predict(double[] instance);
  
  /**
   * Return fitted parameters.
   * 
   * @return list of fitted paremeters.
   */
  List<T> getFittedParameters();
}
