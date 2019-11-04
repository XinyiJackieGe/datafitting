package plot.impl;

import java.awt.Color;
import java.io.IOException;

import model.Regression;
import plot.Plotter;

public class RegressionPlotter implements Plotter<Regression> {

  private int[] computeStartEndPoints(double a, double b, double c) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void plot(Regression model, double[][] data, String outputPath) throws IOException {
    ImagePlotter plotter = new ImagePlotter();
    for (double[] instance : data) {
      int x = (int) instance[0];
      int y = (int) instance[1];
      plotter.addPoint(x, y);
    }
    double[] parameters = model.getParameters();
    double a = parameters[0];
    double b = parameters[1];
    double c = parameters[2];
    int[] startEndPoints = computeStartEndPoints(a, b, c);
    plotter.addLine(startEndPoints[0], startEndPoints[1], startEndPoints[2], startEndPoints[3],
        Color.RED);

    plotter.write(outputPath);
  }

}
