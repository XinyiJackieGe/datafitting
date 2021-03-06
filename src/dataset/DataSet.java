package dataset;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/** This class loads data from text files. */
public class DataSet {
  /**
   * Load data given a string path. If an line has more than 2 numbers, throws exception.
   *
   * @param path file path
   * @return data 2-d double array
   * @throws IOException throws exception if file path is not found.
   */
  public static double[][] load(String path) throws IOException {
    BufferedReader in = new BufferedReader(new FileReader(path));
    ArrayList<double[]> inputData = new ArrayList<>();
    String line;
    while ((line = in.readLine()) != null) {
      String[] vals = line.trim().split(" ");
      if (vals.length > 2) {
        in.close();
        throw new IllegalArgumentException("More than two values in a line!");
      } else if (vals.length == 2) {
        double[] doubleValues = Arrays.stream(vals).mapToDouble(Double::parseDouble).toArray();
        inputData.add(doubleValues);
      }
    }

    double[][] data = new double[inputData.size()][2];
    inputData.toArray(data);
    in.close();
    return data;
  }
}
