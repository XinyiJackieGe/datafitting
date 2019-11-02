package dataFitting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ModelFitting {
  public static void main(String[] args) throws Exception {
    // new ControllerImplt(new InputStreamReader(System.in), System.out).go(new Calculator());
    Model<Double> lr = new LinearRegressionImpl();
    View view = new View();
    String filename1 = "input/linedata-3.txt";
    BufferedReader buffer = new BufferedReader(new FileReader(filename1));
    new ControllerImpl(buffer, System.out).go(lr, view);
  }
}

class ControllerImpl implements Controller {
  final BufferedReader in;
  final Appendable out;

  ControllerImpl(BufferedReader in, Appendable out) {
    this.in = in;
    this.out = out;
  }

  private double[][] readFileDataIntoArray() throws IOException {
    // BufferedReader buffer = new BufferedReader(new FileReader(filename));

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

  private static int[][] convertDoubleToIntData(double[][] data) {

    return null;
  }

  @Override
  public void go(Model model, View view) throws IOException {
    double[][] data = readFileDataIntoArray();
    model.fit(data);
  }
}
