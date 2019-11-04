package model.impl;

import model.Regression;

/** LinearRegressionImpt class implements Model. It contains fit and predict functions. */
public class LinearRegression implements Regression {
  private double a;
  private double b;
  private double c;
  
  /** Default constructor. */
  public LinearRegression() {}

//  /**
//   * Private constructor given a, b, c, for new model return purpose.
//   *
//   * @param a parameter a
//   * @param b parameter b
//   * @param c parameter c
//   */
//  private LinearRegressionImpl(double a, double b, double c) {
//    this.a = a;
//    this.b = b;
//    this.c = c;
//  }
  
  @Override
  public void fit(double[][] data) {
    double[] means = calculateMean(data);
    double[] sumOfSquaredErrors = calculateSumOfSquaredErrors(data, means);
    double d = computeD(sumOfSquaredErrors);
    double theta = computeTheta(d);
    double t = computeT(sumOfSquaredErrors, theta);

    a = Math.cos(t / 2);
    b = Math.asin(t / 2);
    c = -a * means[0] - b * means[1];
  }

  @Override
  public double regress(double xInstance) {
    double predictedY = -c / b - a / b * xInstance;
    return predictedY;
  }

  @Override
  public double[] getParameters() {
    double[] fittedParameters = new double[3];
    fittedParameters[0] = a;
    fittedParameters[1] = b;
    fittedParameters[2] = c;
    return fittedParameters;
  }
  
  /**
   * Calculate x and y means.
   *
   * @param data input x, y array
   * @return x and y means
   */
  private double[] calculateMean(double[][] data) {
    double[] means = new double[2];
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
  private double[] calculateSumOfSquaredErrors(double[][] data, double[] means) {
    double sYY = 0;
    double sXX = 0;
    double sXY = 0;
    for (int i = 0; i < data.length; i++) {
      sYY += (data[i][1] - means[1]) * (data[i][1] - means[1]);
      sXX += (data[i][0] - means[0]) * (data[i][1] - means[0]);
      sXY += (data[i][0] - means[0]) * (data[i][1] - means[1]);
    }

    return new double[] {sYY, sXX, sXY};
  }

  /**
   * Compute d.
   *
   * @param sumOfSquaredErrors Syy, Sxx, Sxy
   * @return d value
   */
  private double computeD(double[] sumOfSquaredErrors) {
    return 2 * sumOfSquaredErrors[2] / (sumOfSquaredErrors[1] - sumOfSquaredErrors[0]);
  }

  /**
   * Compute theta.
   *
   * @param d value
   * @return theta
   */
  private double computeTheta(double d) {
    return Math.atan(d);
  }

  /**
   * Compute t.
   *
   * @param sumOfSquaredErrors Syy, Sxx, Sxy
   * @param theta value
   * @return t if f(t) > 0 otherwise t+180
   */
  private double computeT(double[] sumOfSquaredErrors, double theta) {
    double f =
        (sumOfSquaredErrors[0] - sumOfSquaredErrors[1]) * Math.cos(theta)
            - 2 * sumOfSquaredErrors[2] * Math.sin(theta);
    if (f > 0) {
      return theta;
    } else {
      return theta + 180;
    }
  }

}
