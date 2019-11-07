import java.io.IOException;
import model.Clusterer;
import model.Regression;
import model.impl.KMeans;
import model.impl.LinearRegression;
import plot.Plotter;
import plot.impl.ClustererPlotter;
import plot.impl.RegressionPlotter;

/** Runner class runs models and plot figures given input and output file path. */
public class Runner {
  /**
   * Main function to run model fitting and plotting.
   *
   * @param args default
   * @throws IOException throws exception if file path is not found.
   */
  public static void main(String[] args) throws IOException {
    // Linear regression.
    String filePathLinear = "input/linedata-1.txt";
    Regression linearRegression = new LinearRegression();
    String linearRegressionOutputPath = "output/linear.png";
    Plotter<Regression> regressionPlotter = new RegressionPlotter();
    RunController<Regression> rclr = new RunController<>();
    rclr.run(linearRegression, filePathLinear, linearRegressionOutputPath, regressionPlotter);

    // KMeans.
    String filePathKMeans = "input/clusterdata-2.txt";
    int k = 3;
    KMeans kMeans = new KMeans(k);
    String kMeansOutputPath = "output/cluster.png";
    Plotter<Clusterer> clustererPlotter = new ClustererPlotter();
    RunController<Clusterer> rckm = new RunController<>();
    rckm.run(kMeans, filePathKMeans, kMeansOutputPath, clustererPlotter);

    System.out.println("Done");
  }
}
