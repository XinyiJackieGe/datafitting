import java.io.IOException;
import model.Clusterer;
import model.Regression;
import model.impl.KMeans;
import model.impl.LinearRegression;
import plot.Plotter;
import plot.impl.ClustererPlotter;
import plot.impl.RegressionPlotter;

public class Runner {
  public static void main(String[] args) throws IOException {
    String filePath = ""; // FIXME
    double[][] data = dataset.DataSet.load(filePath);

    Regression linearRegression = new LinearRegression();
    linearRegression.fit(data);
    String linearRegressionOutputPath = ""; // FIXME
    Plotter<Regression> regressionPlotter = new RegressionPlotter();
    regressionPlotter.plot(linearRegression, data, linearRegressionOutputPath);

    int k = 3;
    KMeans kMeans = new KMeans(k);
    kMeans.fit(data);
    String kMeansOutputPath = ""; // FIXME
    Plotter<Clusterer> clustererPlotter = new ClustererPlotter();
    clustererPlotter.plot(kMeans, data, kMeansOutputPath);
  }
}
