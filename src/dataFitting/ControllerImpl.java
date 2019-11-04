package dataFitting;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControllerImpl<T> implements Controller<T> {
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
    public void go(Model<T> model) throws IOException {
      double[][] data = readFileDataIntoArray();
      model = model.fit(data);
      List<T> fittedParams = model.getFittedParameters();
      if (model.getClass().toString().equals("LinearRregressionImpl")) {
        
      }
      
    }
  

}
