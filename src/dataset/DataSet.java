package dataset;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class loads data from text files.
 */
public class DataSet {
  /**
   * Load data given a string path.
   * @param path file path
   * @return data 2-d double array
   * @throws IOException
   */
  public static double[][] load(String path) throws IOException{
    BufferedReader in = new BufferedReader(new FileReader(path));
    ArrayList<double[]> inputData = new ArrayList<>();
    String line;
    while ((line = in.readLine()) != null) {
      String[] vals = line.trim().split(" ");
      double[] doubleValues = Arrays.stream(vals).mapToDouble(Double::parseDouble).toArray();
      inputData.add(doubleValues);
    }
    double[][] data = new double[inputData.size()][2];
    inputData.toArray(data);
    in.close();
    return data; 
  }
}