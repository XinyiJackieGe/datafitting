package plot;

import java.io.IOException;

import model.Model;

public interface Plotter<T extends Model> {
  void plot(T model, double[][] data, String outputPath) throws IOException;
}
