package model.impl;

import model.Regression;

/** LinearRegressionImpt class implements Model. It contains fit and predict functions. */
public class LinearRegression implements Regression {
//  private double a;
//  private double b;
//  private double c;
  private double alpha;
  private double beta;

  /** Default constructor. */
  public LinearRegression() {}

  @Override
  public void fit(double[][] data) {
    if (data.length < 30) {
      throw new IllegalArgumentException("Insufficient data for Linear Regression fitting!");
    }

    double[] means = calculateMean(data);
    double x_mean = means[0];
    double y_mean = means[1];
    double[] sumOfSquaredErrors = calculateSumOfSquaredErrors(data, x_mean, y_mean);
    double sYY = sumOfSquaredErrors[0];
    double sXX = sumOfSquaredErrors[1];
    double sXY = sumOfSquaredErrors[2];
//    double d = 2 * sXY / (sXX - sYY);
//    double theta = Math.atan(d);
//    double t = computeT(sYY, sXX, sXY, theta);
//
//    a = Math.cos(t / 2.0);
//    b = Math.sin(t / 2.0);
//    c = -a * x_mean - b * y_mean;
    
    beta = sXY / sXX;
    alpha = y_mean - beta * x_mean;
    
    
  }

  @Override
  public double regress(double x) {
    return alpha + beta * x;
    //return -(a * x + c) / b;
    //    double predictedY = -c / b - a / b * xInstance;
    //    return predictedY;
  }

  @Override
  public double[] getParameters() {
//    double[] fittedParameters = new double[3];
//    fittedParameters[0] = a;
//    fittedParameters[1] = b;
//    fittedParameters[2] = c;
//    
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
      means[0] += data[i][0];
      means[1] += data[i][1];
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
  private double[] calculateSumOfSquaredErrors(double[][] data, double x_mean, double y_mean) {
    double sYY = 0;
    double sXX = 0;
    double sXY = 0;
    for (int i = 0; i < data.length; i++) {
      double x = data[i][0];
      double y = data[i][1];
      sYY += ((y - y_mean) * (y - y_mean));
      sXX += ((x - x_mean) * (x - x_mean));
      sXY += ((x - x_mean) * (y - y_mean));
    }

    return new double[] {sYY, sXX, sXY};
  }

  /**
   * Compute t.
   *
   * @param FIXME Syy, Sxx, Sxy
   * @param theta value
   * @return t if f(t) > 0 otherwise t+180
   */
  private double computeT(double sYY, double sXX, double sXY, double theta) {
    double t = theta;
    double f = (sYY - sXX) * Math.cos(t) - 2 * sXY * Math.sin(t);
    if (f > 0) {
      return t;
    } else {
      return t + 180;
    }
  }
}
