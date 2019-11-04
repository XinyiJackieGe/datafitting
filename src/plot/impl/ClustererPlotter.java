package plot.impl;

import java.awt.Color;
import java.io.IOException;

import model.Clusterer;
import plot.Plotter;

public class ClustererPlotter implements Plotter<Clusterer> {

  private static Color[] COLORS = new Color[] {Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE,
      Color.PINK, Color.CYAN, Color.MAGENTA, Color.YELLOW};

  @Override
  public void plot(Clusterer model, double[][] data, String outputPath) throws IOException {
    ImagePlotter plotter = new ImagePlotter();
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
