package model.impl;

import model.Regression;

/** LinearRegressionImpt class implements Model. It contains fit and predict functions. */
public class LinearRegression implements Regression {
  private double alpha;
  private double beta;

  @Override
  public void fit(double[][] data) {
    if (data.length < 30) {
      throw new IllegalArgumentException("Insufficient data for Linear Regression fitting!");
    }

    double[] means = calculateMean(data);
    double x_mean = means[0];
    double y_mean = means[1];
    double[] sumOfSquaredErrors = calculateSumOfSquaredErrors(data, x_mean, y_mean);
    double sXX = sumOfSquaredErrors[1];
    double sXY = sumOfSquaredErrors[2];

    beta = sXY / sXX;
    alpha = y_mean - beta * x_mean;
  }

  @Override
  public double regress(double x) {
    return alpha + beta * x;
  }

  @Override
  public double[] getParameters() {
    double[] fittedParameters = new double[2];
    fittedParameters[0] = alpha;
    fittedParameters[1] = beta;
    return fittedParameters;
  }

  /**
   * Calculate x and y means.
   *
   * @param data input x, y array
   * @return x and y means
   */
  private double[] calculateMean(double[][] data) {
    double[] means = new double[] {0.0, 0.0};
    int instanceLength = data.length;

    for (int i = 0; i < instanceLength; i++) {
      if (data[i].length != 2) {
        instanceLength--;
        continue;
      } else {
        means[0] += data[i][0];
        means[1] += data[i][1];
      }
    }
    if (instanceLength < 30) {
      throw new IllegalArgumentException("Insufficient data for Linear Regression fitting!");
    }

    means[0] = means[0] / instanceLength;
    means[1] = means[1] / instanceLength;
    return means;
  }

  /**
   * Calculate sum of squared errors.
   *
   * @param data input x, y array
   * @param means x, y means
   * @return Syy, Sxx, Sxy
   */
  private double[] calculateSumOfSquaredErrors(double[][] data, double xMean, double yMean) {
    double sYY = 0;
    double sXX = 0;
    double sXY = 0;
    for (int i = 0; i < data.length; i++) {
      double x = data[i][0];
      double y = data[i][1];
      sYY += ((y - yMean) * (y - yMean));
      sXX += ((x - xMean) * (x - xMean));
      sXY += ((x - xMean) * (y - yMean));
    }

    return new double[] {sYY, sXX, sXY};
  }
}
