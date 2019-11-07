import java.io.IOException;
import model.Model;
import plot.Plotter;

/**
 * Run controller implementation.
 *
 * @param <T> Model type.
 */
public class RunController<T extends Model> {

  /**
   * Run controller.
   *
   * @param model type T model
   * @param inputPath String of input path
   * @param outputPath String of output path
   * @param plotter type T plotter
   * @throws IOException throw IOException if file path cannot be found.
   */
  public void run(T model, String inputPath, String outputPath, Plotter<T> plotter)
      throws IOException {
    double[][] data = dataset.DataSet.load(inputPath);
    model.fit(data);
    plotter.plot(model, data, outputPath);
  }
}
