package plot;

import java.io.IOException;
import model.Model;

/**
 * Plotter interface for plotting Regression and Clusterer figures. The input T extends from Model.
 *
 * @param <T> extends from Model
 */
public interface Plotter<T extends Model> {
  /**
   * Plot figure.
   *
   * @param model either regression or clusterer
   * @param data x, y matrix
   * @param outputPath to display figure
   * @throws IOException
   */
  void plot(T model, double[][] data, String outputPath) throws IOException;
}
