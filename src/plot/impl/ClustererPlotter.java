package plot.impl;

import java.awt.Color;
import java.io.IOException;

import model.Clusterer;
import plot.Plotter;

public class ClustererPlotter implements Plotter<Clusterer> {

  private static Color[] COLORS = new Color[] {Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE,
      Color.PINK, Color.CYAN, Color.MAGENTA, Color.YELLOW};

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
  
  @Override
  public void plot(Clusterer model, double[][] data, String outputPath) throws IOException {
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
      int clusterIndex = model.cluster(instance);
      Color color = COLORS[clusterIndex % COLORS.length];
      int x = (int) instance[0];
      int y = (int) instance[1];
      plotter.addPoint(x, y, color);
    }

    plotter.write(outputPath);
  }
}
