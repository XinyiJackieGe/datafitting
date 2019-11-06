import java.io.IOException;
import java.util.Arrays;
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
//    String filePathLinear = "input/linedata-1.txt";
//    Regression linearRegression = new LinearRegression();
//    String linearRegressionOutputPath = "output/linear.png";
//    Plotter<Regression> regressionPlotter = new RegressionPlotter();
//    RunController<Regression> rclr = new RunControllerImpl<Regression>();
//    rclr.go(linearRegression, filePathLinear, linearRegressionOutputPath, regressionPlotter);
//
//    // KMeans.
//    String filePathKMeans = "input/clusterdata-2.txt";
//    int k = 3;
//    KMeans kMeans = new KMeans(k);
//    String kMeansOutputPath = "output/cluster_test.png";
//    Plotter<Clusterer> clustererPlotter = new ClustererPlotter();
//    
//    RunController<Clusterer> rckm = new RunControllerImpl<Clusterer>();
//    rckm.go(kMeans, filePathKMeans, kMeansOutputPath, clustererPlotter);
    int k = 2;
    KMeans kMeans = new KMeans(k);
    double[][] testData = {
        {1.5, 2.0}, {1.5, 1.5}, {2.0, 1.5}, {2.0, 2.5}, {1.8, 5.0},
        {2.2, 2.2}, {3.0, 2.0}, {2.5, 4.0}, {4.0, 5.0}, {5.0, 3.0},
        {150.5, 250.6}, {140.0, 252.0}, {145.0, 245.0}, {155.0, 255.0}, {150.0, 240.0},
        {164.0, 253.0}, {160.0, 260.0}, {170.0, 270.0}, {175.0, 245.0}, {160.0, 270.0}
      };
    kMeans.fit(testData);
    System.out.println( kMeans.getParameters()[0][0]); 
    System.out.println( kMeans.getParameters()[0][1]);
    System.out.println( kMeans.getParameters()[1][0]); 
    System.out.println( kMeans.getParameters()[1][1]);
    System.out.println( kMeans.cluster(new double[] {1.5, 2.0})); 
    System.out.println( kMeans.cluster(new double[] {150.5, 250.6}));
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
