import java.io.IOException;
import model.Clusterer;
import model.Model;
import model.Regression;
import model.impl.KMeans;
import model.impl.LinearRegression;
import plot.Plotter;
import plot.impl.ClustererPlotter;
import plot.impl.RegressionPlotter;

/** Runner class runs models and plot figures given input and output file path. */
public class Runner {
  public static void main(String[] args) throws IOException {
    //Linear regression.
    String filePathLinear = "input/linedata-1.txt";
    Regression linearRegression = new LinearRegression();
    String linearRegressionOutputPath = "output/linear.png";
    Plotter<Regression> regressionPlotter = new RegressionPlotter();
    RunController<Regression> rclr = new RunControllerImpl<Regression>();
    rclr.go(linearRegression, filePathLinear, linearRegressionOutputPath, regressionPlotter);

    // KMeans.
    String filePathKMeans = "input/clusterdata-2.txt";
    int k = 2;
    KMeans kMeans = new KMeans(k);
    String kMeansOutputPath = "output/cluster.png";
    Plotter<Clusterer> clustererPlotter = new ClustererPlotter();
    
    RunController<Clusterer> rckm = new RunControllerImpl<Clusterer>();
    rckm.go(kMeans, filePathKMeans, kMeansOutputPath, clustererPlotter);
   
    System.out.println("Done");
  }
}

class RunControllerImpl<T extends Model> implements RunController<T> {
  @Override
  public void go(T model, String inputPath, String outputPath, Plotter<T> plotter)
      throws IOException {
    double[][] data = dataset.DataSet.load(inputPath);
    model.fit(data);
    plotter.plot(model, data, outputPath);
  }
}
