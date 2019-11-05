package plot.impl;

import java.awt.Color;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import model.Regression;
import plot.Plotter;

public class RegressionPlotter implements Plotter<Regression> {

  private double[] calculateMinMax(double[][] data) {
    double minX = Double.MAX_VALUE;
    double minY = Double.MAX_VALUE;
    double maxX = Double.MIN_VALUE;
    double maxY = Double.MIN_VALUE;
    for (int i = 0; i < data.length; i++) {
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

  private int[] computeStartEndPoints(double alpha, double beta, double[] minMax) {
    int[] startEndPoints = new int[4];

    startEndPoints[0] = (int) minMax[0];
    startEndPoints[1] = (int) (alpha + beta * minMax[0]);
    startEndPoints[2] = (int) minMax[1];
    startEndPoints[3] = (int) (alpha + beta * minMax[1]);

    return startEndPoints;
  }

  @Override
  public void plot(Regression model, double[][] data, String outputPath) throws IOException {
    ImagePlotter plotter = new ImagePlotter();
    plotter.setWidth(500);
    plotter.setHeight(500);
    double[] minMax = calculateMinMax(data);
    
    plotter.setDimensions(
        (int) (minMax[0] - 50),
        (int) (minMax[1] + 50),
        (int) (minMax[2] - 50),
        (int) (minMax[3] + 50));

    for (double[] instance : data) {
      int x = (int) instance[0];
      int y = (int) instance[1];
      plotter.addPoint(x, y);
    }

    double[] parameters = model.getParameters();
    double alpha = parameters[0];
    double beta = parameters[1];
//    double a = parameters[0];
//    double b = parameters[1];
//    double c = parameters[2];


    int[] startEndPoints = computeStartEndPoints(alpha, beta, minMax);
     plotter.addLine(
            startEndPoints[0], startEndPoints[1], startEndPoints[2], startEndPoints[3],
     Color.RED);

    plotter.write(outputPath);
  }
}
