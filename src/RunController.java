import java.io.IOException;
import model.Model;
import plot.Plotter;

/**
 * Run controller interface.
 *
 * @param <T> Model type.
 */
public interface RunController<T extends Model> {
  /**
   * Run controller.
   *
   * @param model type T model
   * @param inputPath String of input path
   * @param outputPath String of output path
   * @param plotter type T plotter
   * @throws IOException
   */
  void go(T model, String inputPath, String outputPath, Plotter<T> plotter) throws IOException;
}
